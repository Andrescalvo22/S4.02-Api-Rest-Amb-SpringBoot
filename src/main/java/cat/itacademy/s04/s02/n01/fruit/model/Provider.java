package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Country cannot be blank")
    private String country;

    @OneToMany(mappedBy = "provider")
    private List<Fruit> fruits;
    public Provider(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
