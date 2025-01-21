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
import java.util.Optional;


@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Servicio registrarServicio(Servicio servicio) {
        if (servicio.getNombre() == null || servicio.getDescripcion() == null) {
            return null;
        }
        return servicioRepository.save(servicio);
    }

    public Servicio updateServicio(int id, Servicio servicioDetails) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        if(!servicioOptional.isPresent()){
            return null;
        }
        Servicio servicio = servicioOptional.get();
        servicio.setNombre(servicioDetails.getNombre());
        servicio.setDescripcion(servicioDetails.getDescripcion());
        if (servicio.getNombre() == null || servicio.getDescripcion() == null) {
            return null;
        }
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

    public boolean eliminarServicio(int id) {
        if (!servicioRepository.existsById(id)) {
            return false;
        }
    
        servicioRepository.deleteById(id);
        return true;
    }
}
