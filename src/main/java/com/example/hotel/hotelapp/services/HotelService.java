package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.*;
import com.example.hotel.hotelapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel registrarHotel(Hotel hotel) {
        if (hotel.getNombre() == null || hotel.getDireccion() == null) {
            throw new IllegalArgumentException("Nombre y dirección son obligatorios");
        }
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(int id, Hotel hotelDetails) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        hotel.setNombre(hotelDetails.getNombre());
        hotel.setDireccion(hotelDetails.getDireccion());
        hotel.setTelefono(hotelDetails.getTelefono());
        hotel.setEmail(hotelDetails.getEmail());
        hotel.setSitioWeb(hotelDetails.getSitioWeb());
        return hotelRepository.save(hotel);
    }

    public Page<Hotel> buscarHotelesFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Integer id = parameters.containsKey("id") ? Integer.parseInt(parameters.get("id")) : null;
        String nombre = parameters.getOrDefault("nombre", null);
        String direccion = parameters.getOrDefault("direccion", null);
        String telefono = parameters.getOrDefault("telefono", null);
        String email = parameters.getOrDefault("email", null);
        String sitioWeb = parameters.getOrDefault("sitioWeb", null);
    
        return hotelRepository.buscarConFiltros(id, nombre, direccion, telefono, email, sitioWeb, pageable);
    }

    public void eliminarHotel(int id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel con ID " + id + " no encontrado");
        }
    
        // Obtener todas las habitaciones del hotel
        List<Habitacion> habitaciones = habitacionRepository.findByIdHotel(id);
    
        // Eliminar huéspedes de cada habitación antes de eliminar la habitación
        for (Habitacion habitacion : habitaciones) {
            huespedRepository.deleteById(habitacion.getId());
            habitacionRepository.deleteById(habitacion.getId());
        }
    
        // Finalmente, eliminar el hotel
        hotelRepository.deleteById(id);
    }
    
}
