package ir.shop.shop.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private Long roleId;

    private String roleName;

}
