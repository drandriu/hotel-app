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
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

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
            return null;
        }
        return habitacionRepository.save(habitacion);
    }

    public Habitacion updateHabitacion(int id, Habitacion habitacionDetails) {
        Optional<Habitacion> habitacionOptional = habitacionRepository.findById(id);
        if(!habitacionOptional.isPresent()){
            return null;
        }
        Habitacion habitacion = habitacionOptional.get();
        habitacion.setNumeroHabitacion(habitacionDetails.getNumeroHabitacion());
        habitacion.setTipo(habitacionDetails.getTipo());
        habitacion.setPrecioNoche(habitacionDetails.getPrecioNoche());
        habitacion.setIdHotel(habitacionDetails.getIdHotel());
        if (habitacion.getNumeroHabitacion() == null || habitacion.getTipo() == null) {
            return null;
        }
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
    public boolean eliminarHabitacion(int id){
        if (!habitacionRepository.existsById(id)) {
            return false;
        }
    
        huespedRepository.deleteByIdHabitacion(id);
        habitacionRepository.deleteById(id);
        return true;
    }

    

}
