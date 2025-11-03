package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.*;


public class FruitDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be positive")
    private Integer weightInKilos;

    public FruitDTO() {}

    public FruitDTO(Long id, String name, Integer weightInKilos) {
        this.id = id;
        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeightInKilos() {
        return weightInKilos;
    }

    public void setWeightInKilos(Integer weightInKilos) {
        this.weightInKilos = weightInKilos;
    }
}
