package Exception;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String errorMessage) {
        super(errorMessage);
    }
}
