package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "fruits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Positive(message = "Weight must be positive")
    private int weightInKilos;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    @NotNull(message = "Provider must be assigned")
    private Provider provider;
}
