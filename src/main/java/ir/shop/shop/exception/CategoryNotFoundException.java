package ir.shop.shop.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Category Not Found");
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
