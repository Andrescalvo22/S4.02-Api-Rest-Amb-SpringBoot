package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.service.ProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> createProvider(@RequestBody @Valid ProviderDTO dto) {
        ProviderDTO created = providerService.createProvider(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAllProviders() {
        List<ProviderDTO> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getProviderById(@PathVariable Long id) {
        ProviderDTO provider = providerService.getProviderById(id);
        return ResponseEntity.ok(provider);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProvider(@PathVariable Long id, @Valid @RequestBody ProviderDTO dto) {
        ProviderDTO updated = providerService.updateProvider(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}

