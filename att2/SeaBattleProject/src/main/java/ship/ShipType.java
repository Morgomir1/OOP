package ship;

public enum ShipType {

    SUBMARINE(1, '*', 4),
    FRIGATE(2, '*', 3),
    CRUISER(3, '*', 2),
    DESTROYER(4, '*', 1),
    MINE(1, '!', 5);

    private final int numberOfDescks;
    private final char chipChar;
    private final int maxShipCount;
    private int currShipCount = 0;
    private int x;
    private int y;

    ShipType(int numberOfDescks, char chipChar, int maxShipCount) {
        this.numberOfDescks = numberOfDescks;
        this.chipChar = chipChar;
        this.maxShipCount = maxShipCount;
    }

    public void setShipLocation(String direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    private String direction;

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

    public char getShipName() {
        return chipChar;
    }

}

