package ir.shop.shop.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product Not Found");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
