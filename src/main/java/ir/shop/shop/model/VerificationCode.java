package ir.shop.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    private LocalDateTime expireTime;

    private boolean Used = false;

    @OneToOne
    @JoinColumn(name = "User_Code")
    private User user;

}
