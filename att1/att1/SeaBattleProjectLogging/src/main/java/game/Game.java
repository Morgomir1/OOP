package game;


import main.java.field.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Game {
    private static final Logger logGame = LoggerFactory.getLogger(Game.class);
    private static Scanner scanner = new Scanner(System.in);
    private static final Field hitFieldPlayer1 = new Field();
    private static final Field hitFieldPlayer2 = new Field();
    private static final Field battleFieldPlayer1 = new Field();
    private static final Field battleFieldPlayer2 = new Field();
    private static int currPlayer = 1;

    public static void initializeGame() {

        logGame.info("game.Game start");
        battleFieldPlayer1.fillBatteField();
        battleFieldPlayer2.fillBatteField();
        startGame();
    }

    private static void startGame() {
        while (isFleetAlive(battleFieldPlayer1.getBatteField()) && isFleetAlive(battleFieldPlayer2.getBatteField())) {
            playerTurn();
        }
        endGame();
    }

    private static void endGame() {
        logGame.info("Player number " + currPlayer + " wins!");
        System.out.println("Player number " + currPlayer + " wins!");
        System.out.println("Do you want to play again? y/n");
        String name = scanner.next();
        if (name.equals("y")) {
            initializeGame();
        } else if(!name.equals("n")) {
            endGame();
        }
        logGame.info("game.Game end");
    }

    private static void playerTurn() {
        logGame.info("Player number " + currPlayer + " turn. Your battle fields:");
        System.out.println("Player number " + currPlayer + " turn. Your battle fields:");
        printFields();
        int x = 0;
        System.out.println("Enter x attack cord:");
        if (scanner.hasNextInt()) {
            x = scanner.nextInt();
        } else  {
            logGame.info("Wrong coordinates!");
            System.out.println("Wrong coordinates!");
            playerTurn();
            return;
        }

        int y = 0;
        System.out.println("Enter y attack cord:");
        if (scanner.hasNextInt()) {
            y = scanner.nextInt();
        } else  {
            logGame.info("Wrong coordinates!");
            System.out.println("Wrong coordinates!");
            playerTurn();
            return;
        }
        boolean isStrikeSucces =  tryToStrikeSquare(x, y);
        if (!isStrikeSucces) {
            switchPlayer();
            logGame.info("Players are switched");
        }
    }

    private static boolean tryToStrikeSquare(int x, int y) {
        x -= 1;
        y -= 1;
        if (currPlayer == 1) {
            if (battleFieldPlayer2.getBatteField()[y][x] == '*') {
                battleFieldPlayer2.getBatteField()[y][x] = 'x';
                hitFieldPlayer1.getBatteField()[y][x] = 'x';
                System.out.println("Strike is successful!");
                return true;
            }
        } else  {
            if (battleFieldPlayer1.getBatteField()[y][x] == '*') {
                battleFieldPlayer1.getBatteField()[y][x] = 'x';
                hitFieldPlayer2.getBatteField()[y][x] = 'x';
                System.out.println("Strike is successful!");
                return true;
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

    private static boolean isFleetAlive (char[][] fleet) {
        for (char[] chars : fleet) {
            for (int j = 0; j < fleet.length; j++) {
                if (chars[j] == '*') {
                    return true;
                }
            }
        }
        return  false;
    }

    private static void printFields () {
        if (currPlayer == 1) {
            System.out.println("Your fleet:");
            battleFieldPlayer1.printBattleField();
            System.out.println("Your hit marks field:");
            hitFieldPlayer1.printBattleField();
        } else {
            System.out.println("Your fleet:");
            battleFieldPlayer2.printBattleField();
            System.out.println("Your hit marks field:");
            hitFieldPlayer2.printBattleField();
        }
    }

    private static void switchPlayer () {
        currPlayer = 3 - currPlayer;
    }
}
