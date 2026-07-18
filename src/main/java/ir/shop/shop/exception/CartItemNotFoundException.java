package ir.shop.shop.exception;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException() {
        super("Item cart is Not Found");
    }

    public CartItemNotFoundException(String massage) {
        super(massage);
    }

}
