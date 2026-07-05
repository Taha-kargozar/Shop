package ir.shop.shop.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException() {
        super("Email Already Exits");
    }

    public EmailExistException(String message) {
        super(message);
    }

}
