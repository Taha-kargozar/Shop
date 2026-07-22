package ir.shop.shop.exception;

public class CategoryHasProductsException extends RuntimeException {

    public CategoryHasProductsException() {
        super("دسته بندی دارای محصول است");
    }

    public CategoryHasProductsException(String massage) {
        super(massage);
    }

}
