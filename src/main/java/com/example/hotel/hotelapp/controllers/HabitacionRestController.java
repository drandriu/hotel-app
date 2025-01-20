package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.hotelapp.entities.Habitacion;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/habitacion")
public class HabitacionRestController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping
    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    @PostMapping
    public Habitacion registrarHabitacion(@RequestBody Habitacion habitacion) {
        if (habitacion.getNumeroHabitacion() == null || habitacion.getTipo() == null) {
            throw new IllegalArgumentException("Número de habitación y tipo son obligatorios");
        }
        return habitacionRepository.save(habitacion);
    }

    @PutMapping("/{id}")
    public Habitacion updateHabitacion(@PathVariable int id, @RequestBody Habitacion habitacionDetails) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        habitacion.setNumeroHabitacion(habitacionDetails.getNumeroHabitacion());
        habitacion.setTipo(habitacionDetails.getTipo());
        habitacion.setPrecioNoche(habitacionDetails.getPrecioNoche());
        habitacion.setIdHotel(habitacionDetails.getIdHotel());
        return habitacionRepository.save(habitacion);
    }

    @GetMapping("/filtros")
    public List<Habitacion> buscarHabitacionesFiltro(@RequestParam Map<String, String> parameters) {
        return habitacionRepository.findAll().stream()
                .filter(habitacion -> parameters.get("tipo") == null || 
                        habitacion.getTipo().toLowerCase().contains(parameters.get("tipo").toLowerCase()))
                .filter(habitacion -> parameters.get("precio") == null ||
                        habitacion.getPrecioNoche() <= Float.parseFloat(parameters.get("precio")))
                .collect(Collectors.toList());
    }
}

