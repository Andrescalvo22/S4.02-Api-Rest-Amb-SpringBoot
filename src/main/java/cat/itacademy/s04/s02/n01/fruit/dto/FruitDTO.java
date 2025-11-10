package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FruitDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be greater than zero")
    private Integer weightInKilos;

    @NotNull(message = "Provider ID cannot be null")
    private Long providerId;
}
