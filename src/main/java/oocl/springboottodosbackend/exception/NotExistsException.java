package oocl.springboottodosbackend.exception;

public class NotExistsException extends RuntimeException {
    final public static String NOT_EXISTS = "Not Exists";
    public NotExistsException() {
        super(NOT_EXISTS);
    }
}
