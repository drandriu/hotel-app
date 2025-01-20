package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.hotelapp.entities.Servicio;
import com.example.hotel.hotelapp.repositories.ServicioRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicio")
public class ServicioRestController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @PostMapping
    public Servicio registrarServicio(@RequestBody Servicio servicio){
        if(servicio.getNombre() == null || servicio.getDescripcion() == null){
            throw new IllegalArgumentException("Nombre y descripción son obligatorios");
        }
        return servicioRepository.save(servicio);
    }

    @PutMapping("/{id}")
    public Servicio updateServicio(@PathVariable int id, @RequestBody Servicio servicioDetails) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setNombre(servicioDetails.getNombre());
        servicio.setDescripcion(servicioDetails.getDescripcion());
        return servicioRepository.save(servicio);
    }

    @GetMapping("/filtros")
    public List<Servicio> buscarServiciosFiltro(@RequestParam Map<String, String> parameters) {
        return servicioRepository.findAll().stream()
                .filter(servicio -> parameters.get("nombre") == null || 
                                    servicio.getNombre().toLowerCase().contains(parameters.get("nombre").toLowerCase()))
                .filter(servicio -> parameters.get("descripcion") == null || 
                                    servicio.getDescripcion().toLowerCase().contains(parameters.get("descripcion").toLowerCase()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminarServicio(@PathVariable int id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicioRepository.delete(servicio);
    }

}

