package Exeptions;

import java.util.NoSuchElementException;

public class WrongShipNameException extends NoSuchElementException {

    public WrongShipNameException() {
        super();
    }


    public WrongShipNameException(String s) {
        super(s);
    }
}
