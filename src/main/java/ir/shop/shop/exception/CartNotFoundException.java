package ir.shop.shop.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
        super("Cart Not Found");
    }

    public CartNotFoundException(String massage) {
        super(massage);
    }

}
