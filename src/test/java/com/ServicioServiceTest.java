/*package com;
import com.example.hotel.hotelapp.dtos.ServicioDTO;
import com.example.hotel.hotelapp.entities.Servicio;
import com.example.hotel.hotelapp.repositories.ServicioRepository;
import com.example.hotel.hotelapp.services.ServicioService;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    // Test para findAll()
    @Test
    void testFindAll_DeberiaRetornarListaDeServicios() {
        // Arrange
        List<Servicio> servicios = Arrays.asList(
                new Servicio(1, "Servicio 1", "Descripcion 1"),
                new Servicio(2, "Servicio 2", "Descripcion 2")
        );
        when(servicioRepository.findAll()).thenReturn(servicios);

        // Act
        List<ServicioDTO> resultado = servicioService.findAll();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Servicio 1", resultado.get(0).getNombre());
        verify(servicioRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_DeberiaRetornarListaVacia() {
        when(servicioRepository.findAll()).thenReturn(Collections.emptyList());

        List<ServicioDTO> resultado = servicioService.findAll();

        assertTrue(resultado.isEmpty());
        verify(servicioRepository, times(1)).findAll();
    }

    // Test para registrarServicio()
    @Test
    void testRegistrarServicio_DeberiaGuardarYRetornarDTO() {
        // Arrange
        ServicioDTO dto = new ServicioDTO(1, "Servicio Nuevo", "Descripcion Nueva");
        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());

        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        // Act
        ServicioDTO resultado = servicioService.registrarServicio(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Servicio Nuevo", resultado.getNombre());
        verify(servicioRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    void testRegistrarServicio_DeberiaRetornarNullSiDatosInvalidos() {
        ServicioDTO dto = new ServicioDTO(1, "", "");

        ServicioDTO resultado = servicioService.registrarServicio(dto);

        assertNull(resultado);
        verify(servicioRepository, never()).save(any(Servicio.class));
    }

    // Test para updateServicio()
    @Test
    void testUpdateServicio_DeberiaActualizarServicioExistente() {
        Servicio servicioExistente = new Servicio(1, "Antiguo", "Descripcion antigua");
        ServicioDTO dtoActualizado = new ServicioDTO(1, "Nuevo", "Descripcion nueva");

        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicioExistente));
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicioExistente);

        ServicioDTO resultado = servicioService.updateServicio(1, dtoActualizado);

        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getNombre());
        verify(servicioRepository, times(1)).findById(1);
        verify(servicioRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    void testUpdateServicio_DeberiaRetornarNullSiServicioNoExiste() {
        when(servicioRepository.findById(1)).thenReturn(Optional.empty());

        ServicioDTO resultado = servicioService.updateServicio(1, new ServicioDTO(1, "Nuevo", "Descripcion nueva"));

        assertNull(resultado);
        verify(servicioRepository, times(1)).findById(1);
    }

    // Test para buscarServiciosFiltro()
    @Test
    void testBuscarServiciosFiltro_DeberiaRetornarResultadosPaginados() {
        Servicio servicio = new Servicio(1, "Servicio Filtrado", "Descripcion Filtrada");
        Page<Servicio> pagina = new PageImpl<>(Collections.singletonList(servicio));
        Pageable pageable = PageRequest.of(0, 10);

        when(servicioRepository.buscarConFiltros(null, "Servicio Filtrado", null, pageable))
                .thenReturn(pagina);

        Map<String, String> params = new HashMap<>();
        params.put("nombre", "Servicio Filtrado");

        Page<ServicioDTO> resultado = servicioService.buscarServiciosFiltro(params, 0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Servicio Filtrado", resultado.getContent().get(0).getNombre());
        verify(servicioRepository, times(1)).buscarConFiltros(null, "Servicio Filtrado", null, pageable);
    }

    // Test para eliminarServicio()
    @Test
    void testEliminarServicio_DeberiaEliminarServicioExistente() {
        when(servicioRepository.existsById(1)).thenReturn(true);

        boolean resultado = servicioService.eliminarServicio(1);

        assertTrue(resultado);
        verify(servicioRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarServicio_DeberiaRetornarFalseSiNoExiste() {
        when(servicioRepository.existsById(1)).thenReturn(false);

        boolean resultado = servicioService.eliminarServicio(1);

        assertFalse(resultado);
        verify(servicioRepository, never()).deleteById(anyInt());
    }
}
*/
