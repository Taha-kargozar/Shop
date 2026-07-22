package ir.shop.shop.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull(message = "category is null")
    private Long categoryId;

    @NotNull
    private int quantity;

    private String description;

    private String imageUrl;
}
