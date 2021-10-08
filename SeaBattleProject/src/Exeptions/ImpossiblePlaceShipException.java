package Exeptions;

public class ImpossiblePlaceShipException extends RuntimeException {

    public ImpossiblePlaceShipException() {
        super();
    }

    public ImpossiblePlaceShipException(int index) {
        super("Ship deck out of batte field: " + index);
    }

    public ImpossiblePlaceShipException(String s) {
        super(s);
    }
}
