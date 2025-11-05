package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "providers", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<Fruit> fruits;
}
