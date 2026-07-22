package ir.shop.shop.dto.requests;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyRequest {

    private String email;

    private String code;

}
