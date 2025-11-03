package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @PostMapping
    public ResponseEntity<FruitDTO> createFruit(@RequestBody @Valid FruitDTO dto) {
        FruitDTO created = fruitService.createFruit(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<FruitDTO>> getAllFruits() {
        List<FruitDTO> fruits = fruitService.getAllFruits();
        return ResponseEntity.ok(fruits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitDTO> getFruitByID(@PathVariable Long id) {
        return fruitService.getFruitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitDTO> updateFruit(@PathVariable Long id, @Valid @RequestBody FruitDTO dto) {
        return fruitService.updateFruit(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
}
