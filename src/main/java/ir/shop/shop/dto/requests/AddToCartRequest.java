package ir.shop.shop.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequest {

    private Long productId;

    private int quantity;

}
