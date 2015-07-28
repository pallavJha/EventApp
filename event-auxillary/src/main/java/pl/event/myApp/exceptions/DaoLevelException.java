package pl.event.myApp.exceptions;

public class DaoLevelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DaoLevelException() {
        super();
    }

    public DaoLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoLevelException(String message) {
        super(message);
    }

    public DaoLevelException(Throwable cause) {
        super(cause);
    }

}
