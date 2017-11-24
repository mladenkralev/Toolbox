package exceptions;

/**
 * Created by MLKR on 24/11/2017.
 */
public class ReadXMLTestException extends Exception {
    public ReadXMLTestException() {
    }

    public ReadXMLTestException(String message) {
        super(message);
    }

    public ReadXMLTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadXMLTestException(Throwable cause) {
        super(cause);
    }

    public ReadXMLTestException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
