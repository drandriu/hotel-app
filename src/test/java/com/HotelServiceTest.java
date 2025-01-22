package com;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.hotel.hotelapp.dtos.HotelDTO;
import com.example.hotel.hotelapp.entities.Hotel;
import com.example.hotel.hotelapp.repositories.HotelRepository;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;
import com.example.hotel.hotelapp.repositories.HuespedRepository;
import com.example.hotel.hotelapp.services.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private HuespedRepository huespedRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Hotel> hoteles = Arrays.asList(
                new Hotel(1, "Hotel A", "Calle 123", "123456", "hotelA@test.com", "www.hotelA.com"),
                new Hotel(2, "Hotel B", "Avenida 456", "654321", "hotelB@test.com", "www.hotelB.com")
        );
        when(hotelRepository.findAll()).thenReturn(hoteles);
        List<HotelDTO> resultado = hotelService.findAll();
        assertEquals(2, resultado.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void testRegistrarHotel() {
        HotelDTO hotelDTO = new HotelDTO(1, "Hotel Test", "Direccion Test", "999999", "test@test.com", "www.test.com");
        Hotel hotel = new Hotel(1, "Hotel Test", "Direccion Test", "999999", "test@test.com", "www.test.com");

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        HotelDTO resultado = hotelService.registrarHotel(hotelDTO);

        assertNotNull(resultado);
        assertEquals("Hotel Test", resultado.getNombre());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void testRegistrarHotelConDatosInvalidos() {
        HotelDTO hotelDTO = new HotelDTO(0, "", "", "12345", "test@test.com", "www.test.com");
        HotelDTO resultado = hotelService.registrarHotel(hotelDTO);
        assertNull(resultado);
    }

    @Test
    void testUpdateHotel() {
        Hotel hotelExistente = new Hotel(1, "Hotel Antiguo", "Direccion Ant", "123456", "old@test.com", "www.old.com");
        HotelDTO hotelDTO = new HotelDTO(1, "Hotel Nuevo", "Direccion Nueva", "654321", "new@test.com", "www.new.com");
        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelExistente));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(new Hotel(1, "Hotel Nuevo", "Direccion Nueva", "654321", "new@test.com", "www.new.com"));

        HotelDTO resultado = hotelService.updateHotel(1, hotelDTO);
        assertNotNull(resultado);
        assertEquals("Hotel Nuevo", resultado.getNombre());
        verify(hotelRepository, times(1)).findById(1);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void testEliminarHotel() {
        when(hotelRepository.existsById(1)).thenReturn(true);
        when(habitacionRepository.findByIdHotel(1)).thenReturn(Collections.emptyList());
        doNothing().when(hotelRepository).deleteById(1);

        boolean resultado = hotelService.eliminarHotel(1);
        assertTrue(resultado);
        verify(hotelRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarHotelNoExistente() {
        when(hotelRepository.existsById(1)).thenReturn(false);
        boolean resultado = hotelService.eliminarHotel(1);
        assertFalse(resultado);
    }
}

