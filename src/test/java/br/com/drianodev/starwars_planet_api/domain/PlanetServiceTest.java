package br.com.drianodev.starwars_planet_api.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.drianodev.starwars_planet_api.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

// teste unitário de PlanetService
//@SpringBootTest(classes = PlanetService.class) -> removido pq não tem necessidade de carregar o spring para uma classe
@ExtendWith(MockitoExtension.class) // ideal para testes unitários
public class PlanetServiceTest {

//    @Autowired
    @InjectMocks
    private PlanetService planetService;

//    @MockBean
    @Mock
    private PlanetRepository planetRepository;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // arrange
        when(planetRepository.save(PLANET)).thenReturn(PLANET); // stub

        // act
        Planet planet = planetService.create(PLANET);

        // assert
        assertThat(planet).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        Optional<Planet> getById = planetService.getById(1L);

        assertThat(getById).isNotEmpty();
        assertThat(getById.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Planet> getById = planetService.getById(1L);

        assertThat(getById).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        Optional<Planet> getByName = planetService.getByName(PLANET.getName());

        assertThat(getByName).isNotEmpty();
        assertThat(getByName.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        final String name = "Unexisting name";
        when(planetRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<Planet> getByName = planetService.getByName(name);

        assertThat(getByName).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        Example<Planet> query = QueryBuilder.makeQuery(QUERY_PLANET);
        when(planetRepository.findAll(query)).thenReturn(List.of(PLANET));

        List<Planet> sut = planetService.listPlanets(QUERY_PLANET.getClimate(), QUERY_PLANET.getTerrain());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.listPlanets(PLANET.getClimate(), PLANET.getTerrain());

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_DoesNotThrowAnyException() {
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowAnyException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);

        assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);
    }
}
