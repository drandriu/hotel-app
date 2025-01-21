package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.*;
import com.example.hotel.hotelapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Hotel> buscarHotelesFiltro(Map<String, String> parameters) {
        return hotelRepository.findAll().stream()
                .filter(hotel -> parameters.get("nombre") == null || 
                        hotel.getNombre().toLowerCase().contains(parameters.get("nombre").toLowerCase()))
                .filter(hotel -> parameters.get("direccion") == null || 
                        hotel.getDireccion().toLowerCase().contains(parameters.get("direccion").toLowerCase()))
                .filter(hotel -> parameters.get("telefono") == null || 
                        hotel.getTelefono().contains(parameters.get("telefono")))
                .filter(hotel -> parameters.get("email") == null || 
                        hotel.getEmail().toLowerCase().contains(parameters.get("email").toLowerCase()))
                .filter(hotel -> parameters.get("sitioWeb") == null || 
                        hotel.getSitioWeb().toLowerCase().contains(parameters.get("sitioWeb").toLowerCase()))
                .collect(Collectors.toList());
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
