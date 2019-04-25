package exceptions;

public class InternalErrorException extends Exception {
    public InternalErrorException() {
        super("internal server error");
    }
}
