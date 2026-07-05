package ir.shop.shop.exception;

import ir.shop.shop.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> HandlerUserNotFound(UserNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value() , LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> HandlerProductNotFound(ProductNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value() , LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> HandlerCategoryNotFound(CategoryNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value() , LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorResponseDto> HandlerEmailExist(EmailExistException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value() , LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VerificationExpiredException.class)
    public ResponseEntity<ErrorResponseDto> HandlerVerificationExpired(VerificationExpiredException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value() , LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND);
    }

}
