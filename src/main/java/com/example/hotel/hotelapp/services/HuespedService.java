package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.Huesped;
import com.example.hotel.hotelapp.repositories.HuespedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    public List<Huesped> findAll() {
        return huespedRepository.findAll();
    }

    public Huesped registrarHuesped(Huesped huesped) {
        if (huesped.getNombre() == null || huesped.getDniPasaporte() == null) {
            throw new IllegalArgumentException("Nombre y DNI/Pasaporte son obligatorios");
        }
        return huespedRepository.save(huesped);
    }

    public Huesped updateHuesped(int id, Huesped huespedDetails) {
        Huesped huesped = huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));
        huesped.setNombre(huespedDetails.getNombre());
        huesped.setApellido(huespedDetails.getApellido());
        huesped.setDniPasaporte(huespedDetails.getDniPasaporte());
        huesped.setFechaCheckIn(huespedDetails.getFechaCheckIn());
        huesped.setFechaCheckOut(huespedDetails.getFechaCheckOut());
        return huespedRepository.save(huesped);
    }

    public Page<Huesped> buscarHuespedFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Integer idHuesped = parameters.get("idHuesped") != null ? Integer.parseInt(parameters.get("idHuesped")) : null;
        Integer idHabitacion = parameters.get("idHabitacion") != null ? Integer.parseInt(parameters.get("idHabitacion")) : null;
        String nombre = parameters.getOrDefault("nombre", null);
        String apellido = parameters.getOrDefault("apellido", null);
        String dniPasaporte = parameters.getOrDefault("dniPasaporte", null);
        Date fechaCheckIn = parameters.get("fechaCheckIn") != null ? Date.valueOf(parameters.get("fechaCheckIn")) : null;
        Date fechaCheckOut = parameters.get("fechaCheckOut") != null ? Date.valueOf(parameters.get("fechaCheckOut")) : null;
    
        return huespedRepository.buscarConFiltros(idHuesped, idHabitacion, nombre, apellido, dniPasaporte, fechaCheckIn, fechaCheckOut, pageable);
    }

    public void eliminarHuesped(int id) {
        Huesped huesped = huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));
        huespedRepository.delete(huesped);
    }
}
