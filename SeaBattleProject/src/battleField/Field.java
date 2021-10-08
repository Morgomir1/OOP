package battleField;

import Exeptions.ImpossiblePlaceShipException;
import Exeptions.WrongShipNameException;
import ships.ShipType;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Field {

    static Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Integer> ships = new HashMap<>();
    private int[] currNumberOfShipType = new int[] {0, 0, 0, 0};
    private final int numberOfShips = 10;
    private final int fieldSize = 10;
    private final String verticalDirection = "v";
    private final String gorisontalDirection = "g";
    private final char[][] batteField = new char[fieldSize][fieldSize];

    public void fillBatteField() {
        for (int i = 0; i < numberOfShips; i++ ) {
            printBattleField();
            System.out.println("It remains to place ships: " + (numberOfShips - i));
            System.out.println("Enter ship name:");
            String name = scanner.next();
            if (!isShipNameCorrect(name)) {
                System.out.println("Wrong ship name!");
                i -= 1;
                continue;
            }
            System.out.println("Enter direction: " + verticalDirection + "/" + gorisontalDirection);
            String direction = scanner.next();
            if (!isDirectionCorrect(direction)) {
                System.out.println("Wrong direction!");
                i -= 1;
                continue;
            }
            int x = 0;
            System.out.println("Enter x cord:");
            if (scanner.hasNextInt()) {
                x = scanner.nextInt();
            } else  {
                i -= 1;
                System.out.println("Wrong coordinates!");
                continue;
            }
            int y = 0;
            System.out.println("Enter y cord:");
            if (scanner.hasNextInt()) {
                y = scanner.nextInt();
            } else  {
                System.out.println("Wrong coordinates!");
                i -= 1;
                continue;

            }
            if (!isCoordinatesCorrect(x, y)) {
                System.out.println("Wrong coordinates!");
                i -= 1;
                continue;
            }
            for (ShipType ship : ShipType.values()) {
                if (ship.getShipName().equals(name)) {
                    i += tryToPlaceShip(ship, x, y, direction);
                        break;
                }
            }
        }
        System.out.println("Your battle field:");
        printBattleField();
        System.out.println("Battle field is full completed!");
    }

    private int tryToPlaceShip(ShipType ship, int x, int y, String direction) {
        x -= 1;
        y -= 1;
        try {
            for (int shipType = 0; shipType < currNumberOfShipType.length; shipType++) {
                if (ship.getMaxShipCount() == currNumberOfShipType[shipType]) {
                    System.out.println("This type of ship is full!");
                    return -1;
                }
            }
            currNumberOfShipType[ship.getNumberOfDescks() - 1]++;
            int numberOfDescks = ship.getNumberOfDescks();
            boolean isShipSquareFree = checkShipPositions(numberOfDescks, x, y, direction);
            if (isShipSquareFree) {
                for (int i = 0; i < numberOfDescks; i++) {
                    if (direction.equals(gorisontalDirection)) {
                        batteField[y][x + i] = '*';
                    }
                    if (direction.equals(verticalDirection)) {
                        batteField[y + i][x] = '*';
                    }
                }
            } else {
                System.out.println("Cannot place ship here!");
                return -1;
            }
            return 0;
        } catch (ImpossiblePlaceShipException e) {
            String msg = e.getMessage();
            System.out.println("An exception has occurred. The exception is: " + msg);
            return -1;
        }
    }

    private boolean isShipNameCorrect (String name) {
        boolean isShipNameCorrect = false;
        for (ShipType ship : ShipType.values()) {
            if (ship.getShipName().equals(name)) {
                isShipNameCorrect = true;
                break;
            }
        }
        return isShipNameCorrect;
    }

    private boolean isCoordinatesCorrect(int x, int y) {
        return ((x >= 1 && x < batteField.length + 1) && (y >= 1 && y < batteField.length + 1));
    }

    private boolean isDirectionCorrect (String name) {
        return (name.equals(verticalDirection) || name.equals(gorisontalDirection));
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

    private boolean checkShipPositions (int numberOfDescks,int x, int y, String direction) {
        for (int i = 0; i < numberOfDescks; i++) {
            if (direction.equals(gorisontalDirection)) {
              if(batteField[y][x + i] == '*' && x + i <= batteField.length) {
                  return false;
              }
            }
            if (direction.equals(verticalDirection)) {
                if(batteField[y + i][x] == '*' && y + i <= batteField.length){
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] getBatteField() {
        return batteField;
    }

    public void testFieldInitialize () {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (random.nextInt(10) == 9) {
                    batteField[i][j] = '*';
                } else  {
                    batteField[i][j] = ' ';
                }
            }
        }
    }
}

