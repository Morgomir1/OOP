package ships;

import java.util.EnumSet;

public enum ShipType {

    SUBMARINE(1, "s", 4 ),
    FRIGATE(2, "f", 3),
    CRUISER(3, "c", 2),
    DESTROYER(4, "d", 1);

    private int numberOfDescks;
    private String name;
    private int maxShipCount;


    ShipType(int numberOfDescks, String name, int maxShipCount) {
        this.numberOfDescks = numberOfDescks;
        this.name = name;
        this.maxShipCount = maxShipCount;
    }

    public int getNumberOfDescks() {
        return numberOfDescks;
    }

    public String getShipName() {
        return name;
    }

    public int getMaxShipCount() {
        return maxShipCount;
    }
}

