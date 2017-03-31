package utils;

/**
 * Created by Tom on 26.03.2017.
 */
public class InvalidStringException extends Exception {
    private String invalidString;

    public InvalidStringException(String invalidString) {
        super("Invalid string exception. {"+invalidString+"}");
        this.invalidString = invalidString;
    }
}
