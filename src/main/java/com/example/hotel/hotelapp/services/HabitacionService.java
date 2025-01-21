package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.Habitacion;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;
import com.example.hotel.hotelapp.repositories.HuespedRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    public List<Habitacion> buscarHabitacionesFiltro(Map<String, String> parameters) {
        return habitacionRepository.findAll().stream()
                .filter(habitacion -> parameters.get("tipo") == null || 
                        habitacion.getTipo().toLowerCase().contains(parameters.get("tipo").toLowerCase()))
                .filter(habitacion -> parameters.get("precio") == null ||
                        habitacion.getPrecioNoche() <= Float.parseFloat(parameters.get("precio")))
                .collect(Collectors.toList());
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
