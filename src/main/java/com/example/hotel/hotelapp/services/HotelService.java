package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.dtos.HotelDTO;
import com.example.hotel.hotelapp.entities.*;
import com.example.hotel.hotelapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    public List<HotelDTO> findAll() {
        return hotelRepository.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    public HotelDTO registrarHotel(HotelDTO hotelDTO) {
        Hotel hotel = convertirA_Entidad(hotelDTO);
        if (hotel.getNombre() == null || hotel.getDireccion() == null
        ||hotel.getNombre() == "" || hotel.getDireccion() == "") {
            return null;
        }
        hotelRepository.save(hotel);
        return convertirA_DTO(hotel);
    }

    public HotelDTO updateHotel(int id, HotelDTO hotelDTO) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if(!hotelOptional.isPresent()){
            return null;
        }
        Hotel hotel = hotelOptional.get();
        hotel.setNombre(hotelDTO.getNombre());
        hotel.setDireccion(hotelDTO.getDireccion());
        hotel.setTelefono(hotelDTO.getTelefono());
        hotel.setEmail(hotelDTO.getEmail());
        hotel.setSitioWeb(hotelDTO.getSitioWeb());
        if(hotel.getNombre() == null || hotel.getDireccion() == null
        ||hotel.getNombre() == "" || hotel.getDireccion() == ""){
            return null;
        }
        Hotel hotelActualizado = hotelRepository.save(hotel);
        return convertirA_DTO(hotelActualizado);
    }

    public Page<HotelDTO> buscarHotelesFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Integer id = parameters.containsKey("id") ? Integer.parseInt(parameters.get("id")) : null;
        String nombre = parameters.getOrDefault("nombre", null);
        String direccion = parameters.getOrDefault("direccion", null);
        String telefono = parameters.getOrDefault("telefono", null);
        String email = parameters.getOrDefault("email", null);
        String sitioWeb = parameters.getOrDefault("sitioWeb", null);
    
        Page<Hotel> hotelPage = hotelRepository.buscarConFiltros(id, nombre, direccion, telefono, email, sitioWeb, pageable);
        return hotelPage.map(this::convertirA_DTO);
    }

    @Transactional
    public boolean eliminarHotel(int id) {
        if (!hotelRepository.existsById(id)) {
            return false;
        }
    
        // Obtener todas las habitaciones del hotel
        List<Habitacion> habitaciones = habitacionRepository.findByHotelId(id);
    
        // Eliminar huéspedes de cada habitación antes de eliminar la habitación
        for (Habitacion habitacion : habitaciones) {
            huespedRepository.deleteByHabitacionId(habitacion.getId());
            habitacionRepository.deleteById(habitacion.getId());
        }
    
        // Finalmente, eliminar el hotel
        hotelRepository.deleteById(id);
        return true;
    }
    
    private HotelDTO convertirA_DTO(Hotel hotel) {
        return new HotelDTO(
            hotel.getId(),
            hotel.getNombre(),
            hotel.getDireccion(),
            hotel.getTelefono(),
            hotel.getEmail(),
            hotel.getSitioWeb()
        );
    }
    
    private Hotel convertirA_Entidad(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.getId());
        hotel.setNombre(hotelDTO.getNombre());
        hotel.setDireccion(hotelDTO.getDireccion());
        hotel.setTelefono(hotelDTO.getTelefono());
        hotel.setEmail(hotelDTO.getEmail());
        hotel.setSitioWeb(hotelDTO.getSitioWeb());
        return hotel;
    }
    
}
