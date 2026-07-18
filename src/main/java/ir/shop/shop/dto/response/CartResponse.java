package ir.shop.shop.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CartResponse {

    private Long id;

    private List<CartItemResponse> items;

    private BigDecimal totalPrice;

}
