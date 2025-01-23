/* 
package com;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.hotel.hotelapp.dtos.HuespedDTO;
import com.example.hotel.hotelapp.entities.Huesped;
import com.example.hotel.hotelapp.repositories.HuespedRepository;
import com.example.hotel.hotelapp.services.HuespedService;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HuespedServiceTest {

    @Mock
    private HuespedRepository huespedRepository;

    @InjectMocks
    private HuespedService huespedService;

    @Test
    void testFindAll_DeberiaRetornarListaDeHuespedes() {
        List<Huesped> huespedes = Arrays.asList(
                new Huesped(1, 101, "Juan", "Perez", "12345678", Date.valueOf("2024-01-01"), Date.valueOf("2024-01-10")),
                new Huesped(2, 102, "Maria", "Gomez", "87654321", Date.valueOf("2024-02-01"), Date.valueOf("2024-02-10"))
        );
        when(huespedRepository.findAll()).thenReturn(huespedes);

        List<HuespedDTO> resultado = huespedService.findAll();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(huespedRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_DeberiaRetornarListaVacia() {
        when(huespedRepository.findAll()).thenReturn(Collections.emptyList());

        List<HuespedDTO> resultado = huespedService.findAll();

        assertTrue(resultado.isEmpty());
        verify(huespedRepository, times(1)).findAll();
    }

    @Test
    void testRegistrarHuesped_DeberiaGuardarYRetornarDTO() {
        HuespedDTO dto = new HuespedDTO(1, 101, "Juan", "Perez", "12345678", Date.valueOf("2024-01-01"), Date.valueOf("2024-01-10"));
        Huesped huesped = new Huesped();
        huesped.setNombre(dto.getNombre());
        huesped.setDniPasaporte(dto.getDniPasaporte());

        when(huespedRepository.save(any(Huesped.class))).thenReturn(huesped);

        HuespedDTO resultado = huespedService.registrarHuesped(dto);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(huespedRepository, times(1)).save(any(Huesped.class));
    }

    @Test
    void testRegistrarHuesped_DeberiaRetornarNullSiDatosInvalidos() {
        HuespedDTO dto = new HuespedDTO(1, 101, "", "", "", null, null);

        HuespedDTO resultado = huespedService.registrarHuesped(dto);

        assertNull(resultado);
        verify(huespedRepository, never()).save(any(Huesped.class));
    }

    @Test
    void testUpdateHuesped_DeberiaActualizarHuespedExistente() {
        Huesped huespedExistente = new Huesped(1, 101, "Antiguo", "Perez", "12345678", Date.valueOf("2024-01-01"), Date.valueOf("2024-01-10"));
        HuespedDTO dtoActualizado = new HuespedDTO(1, 101, "Nuevo", "Perez", "87654321", Date.valueOf("2024-01-05"), Date.valueOf("2024-01-15"));

        when(huespedRepository.findById(1)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepository.save(any(Huesped.class))).thenReturn(huespedExistente);

        HuespedDTO resultado = huespedService.updateHuesped(1, dtoActualizado);

        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getNombre());
        verify(huespedRepository, times(1)).findById(1);
        verify(huespedRepository, times(1)).save(any(Huesped.class));
    }

    @Test
    void testUpdateHuesped_DeberiaRetornarNullSiNoExiste() {
        when(huespedRepository.findById(1)).thenReturn(Optional.empty());

        HuespedDTO resultado = huespedService.updateHuesped(1, new HuespedDTO(1, 101, "Nuevo", "Perez", "87654321", Date.valueOf("2024-01-05"), Date.valueOf("2024-01-15")));

        assertNull(resultado);
        verify(huespedRepository, times(1)).findById(1);
    }

    @Test
    void testEliminarHuesped_DeberiaEliminarHuespedExistente() {
        when(huespedRepository.existsById(1)).thenReturn(true);

        boolean resultado = huespedService.eliminarHuesped(1);

        assertTrue(resultado);
        verify(huespedRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarHuesped_DeberiaRetornarFalseSiNoExiste() {
        when(huespedRepository.existsById(1)).thenReturn(false);

        boolean resultado = huespedService.eliminarHuesped(1);

        assertFalse(resultado);
        verify(huespedRepository, never()).deleteById(anyInt());
    }
}

*/