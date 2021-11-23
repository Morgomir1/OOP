package field;

import commandProviders.CommandProvider;
import commandProviders.ConsoleCommandProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ship.ShipType;

public class ShipsField {

    private static final Logger logField = LoggerFactory.getLogger(ShipsField.class);
    private final static CommandProvider cp = new ConsoleCommandProvider();
    private int numberOfShips = 0;
    private final static int fieldSize = 10;
    private final static String verticalDirection = "v";
    private final static String gorisontalDirection = "g";
    private final static int maxShipCount = 15;
    private char[][] batteField = new char[fieldSize][fieldSize];

    public void setBatteField(char[][] batteField) {
        this.batteField = batteField;
    }


    public void fillBatteField() {
        while (numberOfShips != maxShipCount) {
            printBattleField();
            System.out.println("Enter <commands> to see commands");
            System.out.println("Enter command:");
            String command =  cp.getNextLine();
            String[] arr = command.split(" ");
            String firstWord = arr[0];
            if (firstWord.equals("place")) {
                if (arr.length < 5) {
                    System.out.println("Ship parameters is not full!");
                } else {
                    ShipType newShip;
                    try {
                        newShip = ShipType.valueOf(arr[1]);
                        if (newShip.getMaxShipCount() < newShip.getCurrShipCount() + 1) {
                            System.out.println("This type of ship is full!");
                            continue;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Wrong ship name!");
                        continue;
                    }
                    try {
                        int x = Integer.parseInt(arr[2]);
                        int y = Integer.parseInt(arr[3]);
                        String direction = arr[4];
                        if (!direction.equals(verticalDirection) && !direction.equals(gorisontalDirection)) {
                            logField.warn("Wrong direction! Must be g/v");
                            System.out.println("Wrong direction! Must be g/v");
                            continue;
                        }
                        newShip.setShipLocation(direction, x, y);
                        if (tryToPlaceShip(newShip)) {
                            logField.info("Ship placed!");
                            System.out.println("Ship placed!");
                            newShip.setCurrShipCount(newShip.getCurrShipCount() + 1);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong ship coordinates! Try again:");
                        logField.error("Wrong coordinates!");
                    }
                }
            } else if (firstWord.equals("commands")) {
                System.out.println("---Use this command to place ships---");
                System.out.println("place SUBMARINE/FRIGATE/CRUISER/MINE <x cord> <y cord> <direction v/g>");
            } else {
                System.out.println("Wrong command name!");
            }
        }
        System.out.println("Your battle field:");
        printBattleField();
        logField.info("Battle field completed");
        System.out.println("Battle field completed");
    }

    private boolean tryToPlaceShip(ShipType ship) {
        char shipChar = ship.getShipName();
        int x = ship.getX() - 1;
        int y = ship.getY() - 1;
        if (x < 0 || y < 0 || x > batteField.length || y > batteField.length) {
            logField.error("Ship cords is out of battle field! Try again:");
            System.out.println("Ship cords is out of battle field! Try again:");
            return false;
        }
        String direction = ship.getDirection();
        int numberOfDescks = ship.getNumberOfDescks();
        boolean isBattleFieldFree = checkShipPositions(numberOfDescks, x, y, direction);
        if (isBattleFieldFree) {
            for (int i = 0; i < numberOfDescks; i++) {
                if (direction.equals(gorisontalDirection)) {
                    batteField[y][x + i] = shipChar;
                }
                if (direction.equals(verticalDirection)) {
                    batteField[y + i][x] = shipChar;
                }
            }
            numberOfShips++;
        } else {
            logField.warn("Cannot place ship here!");
            System.out.println("Cannot place ship here!");
            ship.setCurrShipCount(ship.getCurrShipCount() - 1);
            return false;
        }
        return true;
    }

    private boolean checkShipPositions (int numberOfDescks,int x, int y, String direction) {
        for (int i = 0; i < numberOfDescks; i++) {
            boolean isSquareNotFree;
            if (direction.equals(gorisontalDirection)) {
                if (x + i >= batteField.length) {
                    return false;
                }
                isSquareNotFree = checkSquare(x + i, y);
                if(isSquareNotFree) {
                    return false;
                }
            }
            if (direction.equals(verticalDirection)) {
                if (y + i >= batteField.length) {
                    return false;
                }
                isSquareNotFree = checkSquare(x, y + i);
                if(isSquareNotFree) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkSquare(int x, int y) {

        if (batteField[y][x] == '*')
            return true;
        if (x + 1 < batteField.length) {
            if (batteField[y][x + 1] == '*')
                return true;
        }
        if (x - 1 > -1) {
            if (batteField[y][x - 1] == '*')
                return true;
        }
        if (y + 1 < batteField.length) {
            if (batteField[y + 1][x] == '*')
                return true;
        }
        if (y - 1 > -1) {
            if(batteField[y - 1][x] == '*')
                return true;
        }
        return false;
    }

    public void printBattleField () {
        System.out.print("    ");
        for (int j = 1; j <= 10; j++) {
            System.out.print("[" + j + "]");
        }
        for (int i = 0; i < batteField.length; i++) {
            System.out.println();
            System.out.print("[" + (i + 1) + "]");
            if (i != 9) {
                System.out.print(" ");
            }
            for (int j = 0; j < batteField.length; j++) {
                System.out.print(" " + batteField[i][j] + " ");
            }
        }
        System.out.println();
    }


    public char[][] getBatteField() {
        return batteField;
    }

    public void demoFieldInitialize() {
        batteField[0][1] = '*';
        batteField[0][2] = '*';
        batteField[0][3] = '*';
        batteField[0][4] = '*';

        batteField[2][1] = '*';
        batteField[2][2] = '*';
        batteField[2][3] = '*';

        batteField[4][1] = '*';
        batteField[4][2] = '*';
        batteField[4][3] = '*';

        batteField[6][1] = '*';
        batteField[6][2] = '*';

        batteField[8][1] = '*';
        batteField[8][2] = '*';

        batteField[0][5] = '*';
        batteField[0][6] = '*';

        batteField[2][5] = '*';

        batteField[4][5] = '*';

        batteField[6][5] = '*';

        batteField[8][5] = '*';

        batteField[9][9] = '!';


        this.numberOfShips = 15;
    }

}

