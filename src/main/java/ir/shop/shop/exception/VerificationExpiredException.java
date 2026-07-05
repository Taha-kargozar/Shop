package ir.shop.shop.exception;

public class VerificationExpiredException extends RuntimeException {

    public VerificationExpiredException() {
        super("Verification Code has Expired");
    }

    public VerificationExpiredException(String message) {
        super(message);
    }

}
