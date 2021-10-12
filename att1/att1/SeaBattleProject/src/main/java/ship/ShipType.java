package main.java.ship;

import java.util.Scanner;

public enum ShipType {

    SUBMARINE(1, "s", 4),
    FRIGATE(2, "f", 3),
    CRUISER(3, "c", 2),
    DESTROYER(4, "d", 1);

    private static final Scanner scanner = new Scanner(System.in);
    private final String verticalDirection = "v";
    private final String gorisontalDirection = "g";
    private final int numberOfDescks;
    private final String name;
    private final int maxShipCount;
    private int currShipCount = 0;
    private int x;
    private int y;
    private String direction;

    ShipType(int numberOfDescks, String name, int maxShipCount) {
        this.numberOfDescks = numberOfDescks;
        this.name = name;
        this.maxShipCount = maxShipCount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }

    public int getCurrShipCount() {
        return currShipCount;
    }

    public void setCurrShipCount(int currShipCount) {
        this.currShipCount = currShipCount;
    }


    public int getMaxShipCount() {
        return maxShipCount;
    }




    public int getNumberOfDescks() {
        return numberOfDescks;
    }

    public String getShipName() {
        return name;
    }

    public boolean createShip (char[][] battleField) {
        if (maxShipCount < currShipCount + 1) {
            System.out.println("This type of ship is full!");
            return false;
        }
        System.out.println("Enter direction: " + verticalDirection + "/" + gorisontalDirection);
        String direction = scanner.next();
        if (!isDirectionCorrect(direction)) {
            System.out.println("Wrong direction!");
            return false;
        }
        int x = 0;
        System.out.println("Enter x cord:");
        if (scanner.hasNextInt()) {
            x = scanner.nextInt();
        } else  {
            System.out.println("Wrong coordinates!");
            return false;
        }
        int y = 0;
        System.out.println("Enter y cord:");
        if (scanner.hasNextInt()) {
            y = scanner.nextInt();
        } else  {
            System.out.println("Wrong coordinates!");
            return false;

        }
        if (!isCoordinatesCorrect(x, y, battleField)) {
            System.out.println("Wrong coordinates!");
            return false;
        }

        for (ShipType shipType : ShipType.values()) {
            if (shipType.getShipName().equals(name)) {
                shipType.x = x;
                shipType.y = y;
                shipType.direction = direction;
                shipType.currShipCount++;
                return true;
            }
        }
        return  false;
    }

    private boolean isCoordinatesCorrect(int x, int y, char[][] battleField) {
        return ((x >= 1 && x < battleField.length + 1) && (y >= 1 && y < battleField.length + 1));
    }

    private boolean isDirectionCorrect (String name) {
        return (name.equals(verticalDirection) || name.equals(gorisontalDirection));
    }
}

