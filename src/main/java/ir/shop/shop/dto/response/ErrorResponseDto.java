package ir.shop.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private String message;

    private int statusCode;

    private LocalDateTime time;

}
