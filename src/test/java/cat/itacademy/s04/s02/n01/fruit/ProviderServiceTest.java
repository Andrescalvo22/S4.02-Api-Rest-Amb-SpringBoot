package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.dto.ProviderDTO;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import cat.itacademy.s04.s02.n01.fruit.service.ProviderService;
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
public class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    @Test
    void testCreateProvider() {
        ProviderDTO dto = new ProviderDTO();
        dto.setName("Provider A");
        dto.setCountry("Spain");

        Provider saved = new Provider(1L, "Provider A", "Spain");

        when(providerRepository.existsByName(dto.getName())).thenReturn(false);
        when(providerRepository.save(any(Provider.class))).thenReturn(saved);

        ProviderDTO result = providerService.createProvider(dto);

        assertEquals("Provider A", result.getName());
        assertEquals("Spain", result.getCountry());
        assertNotNull(result.getId());

        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    void testGetAllProviders() {
        List<Provider> providers = List.of(
                new Provider(1L, "P1", "Spain"),
                new Provider(2L, "P2", "France")
        );

        when(providerRepository.findAll()).thenReturn(providers);

        List<ProviderDTO> result = providerService.getAllProviders();

        assertEquals(2, result.size());
        assertEquals("P1", result.get(0).getName());
        assertEquals("P2", result.get(1).getName());
    }

    @Test
    void testGetProviderById() {
        Provider provider = new Provider(1L, "P1", "Spain");
        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        ProviderDTO result = providerService.getProviderById(1L);

        assertEquals("P1", result.getName());
        assertEquals("Spain", result.getCountry());
    }

    @Test
    void testUpdateProvider() {
        Provider existing = new Provider(1L, "Old Name", "Spain");
        Provider updated = new Provider(1L, "New Name", "Spain");

        ProviderDTO dto = new ProviderDTO();
        dto.setName("New Name");
        dto.setCountry("Spain");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(providerRepository.existsByName("New Name")).thenReturn(false);
        when(providerRepository.save(any(Provider.class))).thenReturn(updated);

        ProviderDTO result = providerService.updateProvider(1L, dto);

        assertEquals("New Name", result.getName());
        assertEquals("Spain", result.getCountry());
    }

    @Test
    void testDeleteProvider_Success() {
        Provider provider = new Provider(1L, "P1", "Spain");
        provider.setFruits(List.of());

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        providerService.deleteProvider(1L);

        verify(providerRepository, times(1)).deleteById(1L);
    }


    @Test
    void testDeleteProvider_WithFruits() {
        Provider provider = new Provider(1L, "P1", "Spain");
        provider.setFruits(List.of(new Fruit()));

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> providerService.deleteProvider(1L));

        assertEquals("Cannot delete provider with associated fruits", exception.getMessage());
    }

    @Test
    void testCreateProvider_NameExists() {
        ProviderDTO dto = new ProviderDTO();
        dto.setName("P1");
        dto.setCountry("Spain");

        when(providerRepository.existsByName("P1")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> providerService.createProvider(dto));

        assertEquals("Provider with this name already exists", exception.getMessage());
    }
}
