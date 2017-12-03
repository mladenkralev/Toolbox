package toolbox.exception;

/**
 * Created by mladen on 01/12/2017.
 */
public class StartUpException extends Exception {

    public StartUpException() {
    }

    public StartUpException(String message) {
        super(message);
    }

    public StartUpException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartUpException(Throwable cause) {
        super(cause);
    }

    public StartUpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
