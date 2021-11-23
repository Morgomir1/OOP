package game;

import commandProviders.CommandProvider;
import commandProviders.ConsoleCommandProvider;
import commandProviders.ScriptedCommandProvider;
import field.ShipsField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serialize.GameContext;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {
    private static final Logger logGame = LoggerFactory.getLogger(Game.class);
    private static Scanner scanner = new Scanner(System.in);
    private ShipsField hitFieldPlayer1 = new ShipsField();
    private ShipsField hitFieldPlayer2 = new ShipsField();
    private ShipsField battleFieldPlayer1 = new ShipsField();
    private ShipsField battleFieldPlayer2 = new ShipsField();
    private int currPlayer = 1;
    private CommandProvider cp;

    public void createGameMenu() {
        logGame.info("Menu created");
        System.out.println("Select an action:");
        System.out.println("|---------------------------|");
        System.out.println("game - start a new game");
        System.out.println("|---------------------------|");
        System.out.println("demo - show a demo version");
        System.out.println("|---------------------------|");
        System.out.println("load - load a game");
        System.out.println("|---------------------------|");
        System.out.println("exit - exit from proggram");
        System.out.println("|---------------------------|");
        String choice = scanner.next();
        switch (choice) {
            case "game":
                cp = new ConsoleCommandProvider();
                initializeGame();
                break;
            case "demo":
                cp = new ScriptedCommandProvider();
                initializeGame();
                break;
            case "load":
                try {
                    File savesDirectory = FileUtils.getAbsolutePathOfSavesDirectory().toFile();
                    if (!savesDirectory.exists()) {
                        System.out.println("Can not find saves folder");
                        createGameMenu();
                    } else {
                        File[] savesList = savesDirectory.listFiles();
                        List<String> names = Arrays.stream(savesList)
                                .map(x -> x.toPath().getFileName().toString())
                                .filter(x -> x.endsWith(".json"))
                                .map(x -> {
                                    String filename = x;
                                    filename = filename.substring(0, filename.lastIndexOf('.'));
                                    return filename;
                                })
                                .collect(Collectors.toList());
                        if (names.size() > 0) {
                            System.out.println("Available saves files: ");
                            names.forEach(x -> System.out.println("    " + x));
                            System.out.println("Type name of save");
                            String filename = scanner.next();
                            int index = names.indexOf(filename);
                            if (index != -1) {
                                GameContext gc = GameContext.read(savesList[index]);
                                this.setContext(gc);
                                cp = new ConsoleCommandProvider();
                            /*if (gc.getGameState() == GameState.GAME) {
                                game();
                            } else {
                                initializeGame();
                            }*/
                                game();
                            } else {
                                System.out.println("Can not find this file");
                                createGameMenu();
                            }
                        } else {
                            System.out.println("Saves folder is empty.");
                            createGameMenu();
                        }
                    }
                } catch (IOException e) {
                    logGame.info("Can not read save file. Error: " + e.getMessage());
                    createGameMenu();
                }
                break;
            case "exit":

                break;
            default:
                System.out.println("Unknown command!");
                createGameMenu();
                break;
        }

    }

    private void initializeGame() {

        if (this.cp instanceof ScriptedCommandProvider) {
            logGame.info("Demo started");
            battleFieldPlayer1.demoFieldInitialize();
            battleFieldPlayer2.demoFieldInitialize();
        } else {
            logGame.info("Game started");
            System.out.println("First player planting ships");
            battleFieldPlayer1.fillBatteField();
            //battleFieldPlayer1.demoFieldInitialize();
            hitFieldPlayer1.setBatteField(new char[10][10]);
            switchPlayer();
            System.out.println("Second player planting ships");
            battleFieldPlayer2.fillBatteField();
            //battleFieldPlayer2.demoFieldInitialize();
            hitFieldPlayer2.setBatteField(new char[10][10]);
            switchPlayer();
        }
        game();
    }

    private void game() {
        while (isFleetAlive(battleFieldPlayer1.getBatteField()) && isFleetAlive(battleFieldPlayer2.getBatteField())) {
            playerTurn();
        }
        logGame.info("Player number " + currPlayer + " wins!");
        System.out.println("Player number " + currPlayer + " wins!");
        createGameMenu();
    }

    protected void playerTurn() {
        logGame.info("Player number " + currPlayer + " turn");
        System.out.println("Player number " + currPlayer + " turn. Your battle fields:");
        printPlayerFields();
        System.out.println("Enter <commands> to see commands");
        System.out.println("Enter command:");
        String command = cp.getNextLine();
        if (cp instanceof ScriptedCommandProvider) {
            System.out.println(command);
        }
        String[] arr = command.split(" ");
        String firstWord = arr[0];
        switch (firstWord) {
            case "strike":
                if (arr.length < 3) {
                    System.out.println("Strike parameters is not full!");
                } else {
                    try {
                        int x = Integer.parseInt(arr[1]);
                        int y = Integer.parseInt(arr[2]);
                        boolean isStrikeSucces = tryToStrikeSquare(x, y);
                        if (!isStrikeSucces) {
                            switchPlayer();
                            logGame.info("Players are switched");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong parameters! Try again:");
                        logGame.error("Wrong parameters!");
                    }
                }
                break;
            case "save":
                try {
                    if (arr.length < 2) {
                        System.out.println("SaveName is not founded");
                        logGame.info("SaveName is not founded");
                        return;
                    }
                    String saveName = arr[1];
                    GameContext gc = this.context();
                    GameContext.save(saveName, gc);
                } catch (IOException e) {
                    System.out.println("Failed to save that game.");
                    logGame.info("Failed to save that game.\n Error: " + e.getMessage());
                }
                break;
            case "stop":
                createGameMenu();
                return;
            case "commands":
                System.out.println("strike x y - strike to enemy fleet");
                System.out.println("save - save game");
                System.out.println("stop - quit to main menu");

                break;
        }
    }

    private boolean tryToStrikeSquare(int x, int y) {
        x -= 1;
        y -= 1;
        if (currPlayer == 1) {
            if (battleFieldPlayer2.getBatteField()[y][x] == '*') {
                battleFieldPlayer2.getBatteField()[y][x] = 'x';
                hitFieldPlayer1.getBatteField()[y][x] = 'x';
                System.out.println("Strike is successful!");
                return true;
            } else if (battleFieldPlayer2.getBatteField()[y][x] == '!') {
                hitFieldPlayer2.getBatteField()[y][x] = battleFieldPlayer1.getBatteField()[y][x];
                battleFieldPlayer2.getBatteField()[y][x] = 'x';
                System.out.println("You shoot to mine!");
            }
        } else  {
            if (battleFieldPlayer1.getBatteField()[y][x] == '*') {
                battleFieldPlayer1.getBatteField()[y][x] = 'x';
                hitFieldPlayer2.getBatteField()[y][x] = 'x';
                System.out.println("Strike is successful!");
                return true;
            } else if (battleFieldPlayer1.getBatteField()[y][x] == '!') {
                hitFieldPlayer1.getBatteField()[y][x] = battleFieldPlayer2.getBatteField()[y][x];
                battleFieldPlayer1.getBatteField()[y][x] = 'x';
                System.out.println("You shoot to mine!");
            }
        }
        if (currPlayer == 1) {
            hitFieldPlayer1.getBatteField()[y][x] = 'o';
        } else  {
            hitFieldPlayer2.getBatteField()[y][x] = 'o';
        }
        System.out.println("Strike is not successful!");
        return false;
    }

    private boolean isFleetAlive (char[][] fleet) {
        for (char[] chars : fleet) {
            for (int j = 0; j < fleet.length; j++) {
                if (chars[j] == '*') {
                    return true;
                }
            }
        }
        return  false;
    }

    private void switchPlayer () {
        currPlayer = 3 - currPlayer;
    }

    private void printPlayerFields () {
        char[][] battleField;
        char[][] hitField;
        if (currPlayer == 1) {
            battleField = battleFieldPlayer1.getBatteField();
            hitField = hitFieldPlayer1.getBatteField();
        } else {
            battleField = battleFieldPlayer2.getBatteField();
            hitField = hitFieldPlayer2.getBatteField();
        }
        int length = hitField.length;
        System.out.print("    ");
        for (int j = 1; j <= length * 2; j++) {
            if (j <= length) {
                System.out.print("[" + j + "]");
            } else {
                if (j == 11) {
                    System.out.print("   ");
                }
                System.out.print("[" + (j - length) + "]");
            }
        }
        System.out.println();
        for (int i = 0; i < length; i++) {
            System.out.print("[" + (i + 1) + "]");
            if (i != 9) {
                System.out.print(" ");
            }
            for (int j = 0; j < length; j++) {
                System.out.print(" " + battleField[i][j] + " ");
            }
            System.out.print("[" + (i + 1) + "]");
            if (i != 9) {
                System.out.print(" ");
            }
            for (int j = 0; j < length; j++) {
                System.out.print(" " + hitField[i][j] + " ");
            }
            System.out.println();
        }
    }

    public GameContext context() {
        return new GameContext(battleFieldPlayer1.getBatteField(), battleFieldPlayer2.getBatteField(), hitFieldPlayer1.getBatteField(), hitFieldPlayer2.getBatteField(), currPlayer);
    }

    public void setContext (GameContext context) {
        this.battleFieldPlayer1.setBatteField(context.getBattleFieldPlayer1());
        this.battleFieldPlayer2.setBatteField(context.getBattleFieldPlayer2());
        this.hitFieldPlayer1.setBatteField(context.getHitFieldPlayer1());
        this.hitFieldPlayer2.setBatteField(context.getHitFieldPlayer2());
        this.currPlayer = context.getCurrPlayer();
    }
}
