package main.java.field;


import main.java.ship.ShipType;


import java.util.Random;
import java.util.Scanner;

public class Field {


    private final Scanner scanner = new Scanner(System.in);
    private int numberOfShips = 0;
    private final int fieldSize = 10;
    private final String verticalDirection = "v";
    private final String gorisontalDirection = "g";
    private final int maxShipCount = 10;
    private final char[][] batteField = new char[fieldSize][fieldSize];

    public void fillBatteField() {
        while (numberOfShips != maxShipCount) {
            printBattleField();
            printShipsCount(numberOfShips);
            ShipType newShip = chooseShip();

            if (newShip == null) {
                continue;
            }
            boolean isChipCreated = newShip.createShip(batteField);
            if (isChipCreated) {
                tryToPlaceShip(newShip);
            }

        }
        System.out.println("Your battle field:");
        printBattleField();
        System.out.println("Battle field completed");
    }

    private ShipType chooseShip() {
        System.out.println("Choose ship type:");
        String name = scanner.next();
        if (!isShipNameCorrect(name)) {
            System.out.println("Wrong ship name!");
            return null;
        }
        if (name.equals(ShipType.SUBMARINE.getShipName())) {
            return ShipType.SUBMARINE;
        }
        if (name.equals(ShipType.FRIGATE.getShipName())) {
            return ShipType.FRIGATE;
        }
        if (name.equals(ShipType.CRUISER.getShipName())) {
            return ShipType.CRUISER;
        }
        if (name.equals(ShipType.DESTROYER.getShipName())) {
            return ShipType.DESTROYER;
        }
        return null;
    }

    private void tryToPlaceShip(ShipType ship) {
        int x = ship.getX() - 1;
        int y = ship.getY() - 1;
        String direction = ship.getDirection();
        int numberOfDescks = ship.getNumberOfDescks();
        boolean isBattleFieldFree = checkShipPositions(numberOfDescks, x, y, direction);
        if (isBattleFieldFree) {
            for (int i = 0; i < numberOfDescks; i++) {
                if (direction.equals(gorisontalDirection)) {
                    batteField[y][x + i] = '*';
                }
                if (direction.equals(verticalDirection)) {
                    batteField[y + i][x] = '*';
                }
            }
            numberOfShips++;
        } else {
            System.out.println("Cannot place ship here!");
            ship.setCurrShipCount(ship.getCurrShipCount() - 1);
        }

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

    private void printShipsCount(int i) {
        System.out.println("Ships installed: " +  i);
        for (ShipType shipType : ShipType.values()) {
            System.out.println(shipType.name() + "S: " + shipType.getCurrShipCount() + "/" + shipType.getMaxShipCount());
        }
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

