package ir.shop.shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private BigDecimal price;

    @Column()
    private String Description;

    @PositiveOrZero(message = "قیمت باید بیشتر یا مساوی صفر باشد")
    @NotNull
    private int quantity;

    @ManyToOne
    @NotNull
    private Category category;

    private String imageUrl;
}
