package Exception;

public class DBException extends RuntimeException {
    public DBException(String errorMessage) {
        super(errorMessage);
    }
}
