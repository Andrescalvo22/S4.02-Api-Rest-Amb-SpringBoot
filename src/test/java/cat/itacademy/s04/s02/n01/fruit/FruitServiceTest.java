package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
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
    FruitRepository repository;

    @InjectMocks
    FruitService fruitService;

    @Test
    void testCreateFruit() {
        FruitDTO dto = new FruitDTO(null, "Apple", 3);

        Fruit savedFruit = new Fruit(1L, "Apple", 3);

        when(repository.save(any())).thenReturn(savedFruit);

        FruitDTO result = fruitService.createFruit(dto);

        assertEquals("Apple", result.getName());
        assertEquals(3, result.getWeightInKilos());
        assertNotNull(result.getId());

        verify(repository, times(1)).save(any());
    }

    @Test
    void testGetAllFruits() {
        List<Fruit> fruits = List.of(
                new Fruit(1L, "Orange", 2),
                new Fruit(2L, "Pear", 1)
        );

        when(repository.findAll()).thenReturn(fruits);

        List<FruitDTO> result = fruitService.getAllFruits();

        assertEquals(2, result.size());
        assertEquals("Orange", result.get(0).getName());
        assertEquals("Pear", result.get(1).getName());
    }

    @Test
    void testGetFruitById() {
        Fruit fruit = new Fruit(1L, "Banana", 4);

        when(repository.findById(1L)).thenReturn(Optional.of(fruit));

        FruitDTO result = fruitService.getFruitById(1L).orElseThrow();

        assertEquals("Banana", result.getName());
        assertEquals(4, result.getWeightInKilos());
    }


    @Test
    void testUpdateFruit() {
        Fruit fruit = new Fruit(1L, "Peach", 2);
        Fruit updated = new Fruit(1L, "Peach", 5);

        when(repository.findById(1L)).thenReturn(Optional.of(fruit));
        when(repository.save(any(Fruit.class))).thenReturn(updated);

        FruitDTO input = new FruitDTO(null, "Peach", 5);

        FruitDTO result = fruitService.updateFruit(1L, input).orElseThrow();

        assertEquals(5, result.getWeightInKilos());
    }

    @Test
    void testDeleteFruit() {
        Fruit fruit = new Fruit(1L, "Kiwi", 1);

        when(repository.findById(1L)).thenReturn(Optional.of(fruit));

        fruitService.deleteFruit(1L);

        verify(repository, times(1)).delete(fruit);
    }

    @Test
    void testGetFruitById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> fruitService.getFruitById(1L));
    }
}
