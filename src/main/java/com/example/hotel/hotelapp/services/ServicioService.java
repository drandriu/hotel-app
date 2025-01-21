package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.Servicio;
import com.example.hotel.hotelapp.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Servicio registrarServicio(Servicio servicio) {
        if (servicio.getNombre() == null || servicio.getDescripcion() == null) {
            throw new IllegalArgumentException("Nombre y descripción son obligatorios");
        }
        return servicioRepository.save(servicio);
    }

    public Servicio updateServicio(int id, Servicio servicioDetails) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setNombre(servicioDetails.getNombre());
        servicio.setDescripcion(servicioDetails.getDescripcion());
        return servicioRepository.save(servicio);
    }

    public List<Servicio> buscarServiciosFiltro(Map<String, String> parameters) {
        return servicioRepository.findAll().stream()
                .filter(servicio -> parameters.get("nombre") == null ||
                        servicio.getNombre().toLowerCase().contains(parameters.get("nombre").toLowerCase()))
                .filter(servicio -> parameters.get("descripcion") == null ||
                        servicio.getDescripcion().toLowerCase().contains(parameters.get("descripcion").toLowerCase()))
                .collect(Collectors.toList());
    }

    public void eliminarServicio(int id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicioRepository.delete(servicio);
    }
}
