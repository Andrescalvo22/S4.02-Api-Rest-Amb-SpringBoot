package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FruitService {

    @Autowired
    private FruitRepository fruitRepository;

    private FruitDTO convertToDTO(Fruit fruit) {
        return new FruitDTO(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos()
        );
    }

    public FruitDTO createFruit(FruitDTO dto) {
        Fruit fruit = new Fruit();
        fruit.setName(dto.getName());
        fruit.setWeightInKilos(dto.getWeightInKilos());
        Fruit saved = fruitRepository.save(fruit);
        return convertToDTO(saved);
    }

    public List<FruitDTO> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FruitDTO> getFruitById(Long id) {
        return fruitRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<FruitDTO> updateFruit(Long id, FruitDTO dto) {
        return fruitRepository.findById(id)
                .map(fruit -> {
                    fruit.setName(dto.getName());
                    fruit.setWeightInKilos(dto.getWeightInKilos());
                    Fruit updated = fruitRepository.save(fruit);
                    return convertToDTO(updated);
                });
    }

    public void deleteFruit(Long id) {
        fruitRepository.deleteById(id);
    }
}



