package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    private ProviderDTO convertToDTO(Provider provider) {
        return new ProviderDTO(
                provider.getId(),
                provider.getName(),
                provider.getCountry()
        );
    }

    private void applyDtoToEntity(ProviderDTO dto, Provider provider) {
        provider.setName(dto.getName());
        provider.setCountry(dto.getCountry());
    }

    public ProviderDTO createProvider(ProviderDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Provider name cannot be blank");
        }
        if (providerRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Provider with this name already exists");
        }

        Provider provider = new Provider();
        applyDtoToEntity(dto, provider);
        Provider saved = providerRepository.save(provider);
        return convertToDTO(saved);
    }

    public List<ProviderDTO> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProviderDTO getProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + id));
        return convertToDTO(provider);
    }

    public ProviderDTO updateProvider(Long id, ProviderDTO dto) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + id));

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Provider name cannot be blank");
        }

        if (!provider.getName().equals(dto.getName()) && providerRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Provider with this name already exists");
        }

        applyDtoToEntity(dto, provider);
        Provider updated = providerRepository.save(provider);
        return convertToDTO(updated);
    }

    public void deleteProvider(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + id));

        if (provider.getFruits() != null && !provider.getFruits().isEmpty()) {
            throw new RuntimeException("Cannot delete provider with associated fruits");
        }

        providerRepository.deleteById(id);
    }
}
