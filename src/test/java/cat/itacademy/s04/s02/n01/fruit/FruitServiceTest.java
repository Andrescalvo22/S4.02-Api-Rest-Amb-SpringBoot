package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceTest {

    @Mock
    private FruitRepository repository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private FruitService fruitService;

    //------------------------------------------
    @Test
    void testCreateFruit() {
        Provider provider = new Provider(1L, "Provider A", "Spain");

        FruitDTO dto = new FruitDTO(null, "Apple", 3, provider.getId());
        Fruit savedFruit = new Fruit(1L, "Apple", 3, provider);

        when(providerRepository.findById(provider.getId()))
                .thenReturn(Optional.of(provider));

        when(repository.save(any(Fruit.class)))
                .thenReturn(savedFruit);

        FruitDTO result = fruitService.createFruit(dto);

        assertEquals("Apple", result.getName());
        assertEquals(3, result.getWeightInKilos());
        assertEquals(provider.getId(), result.getProviderId());
        assertNotNull(result.getId());

        verify(repository, times(1)).save(any(Fruit.class));
    }

    //------------------------------------------
    @Test
    void testGetAllFruits() {
        Provider provider = new Provider(1L, "P1", "Spain");

        List<Fruit> fruits = List.of(
                new Fruit(1L, "Orange", 2, provider),
                new Fruit(2L, "Pear", 1, provider)
        );

        when(repository.findAll()).thenReturn(fruits);

        List<FruitDTO> result = fruitService.getAllFruits();

        assertEquals(2, result.size());
        assertEquals("Orange", result.get(0).getName());
        assertEquals("Pear", result.get(1).getName());
    }

    //------------------------------------------
    @Test
    void testGetFruitById() {
        Provider provider = new Provider(1L, "P1", "Spain");
        Fruit fruit = new Fruit(1L, "Banana", 4, provider);

        when(repository.findById(1L)).thenReturn(Optional.of(fruit));

        FruitDTO result = fruitService.getFruitById(1L).orElseThrow();

        assertEquals("Banana", result.getName());
        assertEquals(4, result.getWeightInKilos());
        assertEquals(provider.getId(), result.getProviderId());
    }

    //------------------------------------------
    @Test
    void testUpdateFruit() {
        Provider provider = new Provider(1L, "P1", "Spain");

        Fruit existing = new Fruit(1L, "Peach", 2, provider);
        Fruit updated = new Fruit(1L, "Peach", 5, provider);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Fruit.class))).thenReturn(updated);

        FruitDTO input = new FruitDTO(null, "Peach", 5, provider.getId());

        FruitDTO result = fruitService.updateFruit(1L, input).orElseThrow();

        assertEquals(5, result.getWeightInKilos());
    }

    //------------------------------------------
    @Test
    void testDeleteFruit() {
        Provider provider = new Provider(1L, "P1", "Spain");
        Fruit fruit = new Fruit(1L, "Kiwi", 1, provider);

        when(repository.existsById(1L)).thenReturn(true);

        fruitService.deleteFruit(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    //------------------------------------------
    @Test
    void testGetFruitById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(fruitService.getFruitById(1L).isPresent());
    }
}
