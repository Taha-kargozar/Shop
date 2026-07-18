package ir.shop.shop.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartItemResponse {

    private Long id;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private int quantity;

    private BigDecimal subtotal;

}
