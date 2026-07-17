package ir.shop.shop.exception;

public class CategoryHasProductsException extends RuntimeException {

    public CategoryHasProductsException() {
        super("Category Has Product");
    }

    public CategoryHasProductsException(String massage) {
        super(massage);
    }

}
