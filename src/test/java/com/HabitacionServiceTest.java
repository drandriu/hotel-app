/* 

package com;
import com.example.hotel.hotelapp.dtos.HabitacionDTO;
import com.example.hotel.hotelapp.entities.Habitacion;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;
import com.example.hotel.hotelapp.repositories.HuespedRepository;
import com.example.hotel.hotelapp.services.HabitacionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private HuespedRepository huespedRepository;

    @InjectMocks
    private HabitacionService habitacionService;

    @Test
    void testFindAll_DeberiaRetornarListaDeHabitaciones() {
        List<Habitacion> habitaciones = Arrays.asList(
                new Habitacion(1, 101, "A101", "Simple", 100.0f),
                new Habitacion(2, 102, "B202", "Doble", 200.0f)
        );
        when(habitacionRepository.findAll()).thenReturn(habitaciones);

        List<HabitacionDTO> resultado = habitacionService.findAll();

        assertEquals(2, resultado.size());
        assertEquals("A101", resultado.get(0).getNumeroHabitacion());
        verify(habitacionRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_DeberiaRetornarListaVacia() {
        when(habitacionRepository.findAll()).thenReturn(Collections.emptyList());

        List<HabitacionDTO> resultado = habitacionService.findAll();

        assertTrue(resultado.isEmpty());
        verify(habitacionRepository, times(1)).findAll();
    }

    @Test
    void testRegistrarHabitacion_DeberiaGuardarYRetornarDTO() {
        HabitacionDTO dto = new HabitacionDTO(1, 101, "C303", "Suite", 300.0f);
        Habitacion habitacion = new Habitacion(1, 101, "C303", "Suite", 300.0f);

        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacion);

        HabitacionDTO resultado = habitacionService.registrarHabitacion(dto);

        assertNotNull(resultado);
        assertEquals("C303", resultado.getNumeroHabitacion());
        verify(habitacionRepository, times(1)).save(any(Habitacion.class));
    }

    @Test
    void testRegistrarHabitacion_DeberiaRetornarNullSiDatosInvalidos() {
        HabitacionDTO dto = new HabitacionDTO(1, 101, "", "", 0);

        HabitacionDTO resultado = habitacionService.registrarHabitacion(dto);

        assertNull(resultado);
        verify(habitacionRepository, never()).save(any(Habitacion.class));
    }

    @Test
    void testUpdateHabitacion_DeberiaActualizarHabitacionExistente() {
        Habitacion habitacionExistente = new Habitacion(1, 101, "D404", "Doble", 250.0f);
        HabitacionDTO dtoActualizado = new HabitacionDTO(1, 101, "D404", "Suite", 350.0f);

        when(habitacionRepository.findById(1)).thenReturn(Optional.of(habitacionExistente));
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacionExistente);

        HabitacionDTO resultado = habitacionService.updateHabitacion(1, dtoActualizado);

        assertNotNull(resultado);
        assertEquals("Suite", resultado.getTipo());
        verify(habitacionRepository, times(1)).findById(1);
        verify(habitacionRepository, times(1)).save(any(Habitacion.class));
    }

    @Test
    void testUpdateHabitacion_DeberiaRetornarNullSiNoExiste() {
        when(habitacionRepository.findById(1)).thenReturn(Optional.empty());

        HabitacionDTO resultado = habitacionService.updateHabitacion(1, new HabitacionDTO(1, 101, "E505", "Triple", 400.0f));

        assertNull(resultado);
        verify(habitacionRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarHabitacionesFiltro_DeberiaRetornarResultadosPaginados() {
        Habitacion habitacion = new Habitacion(1, 101, "F606", "Suite", 500.0f);
        Page<Habitacion> pagina = new PageImpl<>(Collections.singletonList(habitacion));
        Pageable pageable = PageRequest.of(0, 10);

        when(habitacionRepository.buscarConFiltros(null, 101, null, null, null, pageable)).thenReturn(pagina);

        Map<String, String> params = new HashMap<>();
        params.put("idHotel", "101");

        Page<HabitacionDTO> resultado = habitacionService.buscarHabitacionesFiltro(params, 0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("F606", resultado.getContent().get(0).getNumeroHabitacion());
        verify(habitacionRepository, times(1)).buscarConFiltros(null, 101, null, null, null, pageable);
    }

    @Test
    void testEliminarHabitacion_DeberiaEliminarHabitacionExistente() {
        when(habitacionRepository.existsById(1)).thenReturn(true);

        boolean resultado = habitacionService.eliminarHabitacion(1);

        assertTrue(resultado);
        verify(huespedRepository, times(1)).deleteByIdHabitacion(1);
        verify(habitacionRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarHabitacion_DeberiaRetornarFalseSiNoExiste() {
        when(habitacionRepository.existsById(1)).thenReturn(false);

        boolean resultado = habitacionService.eliminarHabitacion(1);

        assertFalse(resultado);
        verify(habitacionRepository, never()).deleteById(anyInt());
    }
}

*/