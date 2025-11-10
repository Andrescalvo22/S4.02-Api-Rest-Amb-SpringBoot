package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitService(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    private FruitDTO convertToDTO(Fruit fruit) {
        return new FruitDTO(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider().getId()
        );
    }

    private void applyDtoToEntity(FruitDTO dto, Fruit fruit) {
        fruit.setName(dto.getName());
        fruit.setWeightInKilos(dto.getWeightInKilos());

        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException(
                        "Provider not found with id: " + dto.getProviderId()
                ));
        fruit.setProvider(provider);
    }

    public FruitDTO createFruit(FruitDTO dto) {
        if (dto.getProviderId() == null) {
            throw new RuntimeException("providerId is required");
        }
        Fruit fruit = new Fruit();
        applyDtoToEntity(dto, fruit);
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
                    applyDtoToEntity(dto, fruit);
                    Fruit updated = fruitRepository.save(fruit);
                    return convertToDTO(updated);
                });
    }

    public void deleteFruit(Long id) {
        if (!fruitRepository.existsById(id)) {
            throw new RuntimeException("Fruit not found with id: " + id);
        }
        fruitRepository.deleteById(id);
    }

    public List<FruitDTO> getFruitsByProviderId(Long providerId) {
        if (!providerRepository.existsById(providerId)) {
            throw new RuntimeException("Provider not found with id: " + providerId);
        }
        return fruitRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}



