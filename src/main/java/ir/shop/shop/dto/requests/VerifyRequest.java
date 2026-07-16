package ir.shop.shop.dto.requests;

import lombok.Data;

@Data
public class VerifyRequest {

    private String email;

    private String code;

}
