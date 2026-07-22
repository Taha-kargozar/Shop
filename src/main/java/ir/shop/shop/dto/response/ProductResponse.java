package ir.shop.shop.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int quantity;

    private String categoryName;

    private String imageUrl;

    private Long categoryId;
}
