package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.Servicio;
import com.example.hotel.hotelapp.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Servicio> buscarServiciosFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        String idStr = parameters.getOrDefault("id", null);
        Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;
        String nombre = parameters.getOrDefault("nombre", null);
        String descripcion = parameters.getOrDefault("descripcion", null);
    
        return servicioRepository.buscarConFiltros(id, nombre, descripcion, pageable);
    }

    public void eliminarServicio(int id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicioRepository.delete(servicio);
    }
}
