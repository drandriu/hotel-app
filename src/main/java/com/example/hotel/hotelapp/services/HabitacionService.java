package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.Habitacion;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;
import com.example.hotel.hotelapp.repositories.HuespedRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    public Habitacion registrarHabitacion(Habitacion habitacion) {
        if (habitacion.getNumeroHabitacion() == null || habitacion.getTipo() == null) {
            throw new IllegalArgumentException("Número de habitación y tipo son obligatorios");
        }
        return habitacionRepository.save(habitacion);
    }

    public Habitacion updateHabitacion(int id, Habitacion habitacionDetails) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        habitacion.setNumeroHabitacion(habitacionDetails.getNumeroHabitacion());
        habitacion.setTipo(habitacionDetails.getTipo());
        habitacion.setPrecioNoche(habitacionDetails.getPrecioNoche());
        habitacion.setIdHotel(habitacionDetails.getIdHotel());
        return habitacionRepository.save(habitacion);
    }

    public Page<Habitacion> buscarHabitacionesFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        Integer id = parameters.containsKey("id") ? Integer.parseInt(parameters.get("id")) : null;
        Integer idHotel = parameters.containsKey("idHotel") ? Integer.parseInt(parameters.get("idHotel")) : null;
        String numeroHabitacion = parameters.getOrDefault("numeroHabitacion", null);
        String tipo = parameters.getOrDefault("tipo", null);
        String precioStr = parameters.getOrDefault("precioNoche", null);
        Float precioNoche = (precioStr != null && !precioStr.isEmpty()) ? Float.parseFloat(precioStr) : null;
    
        return habitacionRepository.buscarConFiltros(id, idHotel, numeroHabitacion, tipo, precioNoche, pageable);
    }
    

    @Transactional
    public void eliminarHabitacion(int id){
        if (!habitacionRepository.existsById(id)) {
            throw new RuntimeException("Habitación con ID " + id + " no encontrada");
        }
    
        huespedRepository.deleteByIdHabitacion(id);
        habitacionRepository.deleteById(id);
    }

    

}
