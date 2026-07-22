package ir.shop.shop.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserUpdateRequest {

    private String firstname;

    private String lastname;

    private Long roleId;

}
