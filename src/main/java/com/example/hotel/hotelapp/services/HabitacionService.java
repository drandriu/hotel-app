package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.dtos.HabitacionDTO;
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
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    public List<HabitacionDTO> findAll() {
        //return habitacionRepository.findAll();
        return habitacionRepository.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    public HabitacionDTO registrarHabitacion(HabitacionDTO habitacionDTO) {
        /*if (habitacion.getNumeroHabitacion() == null || habitacion.getTipo() == null
        ||habitacion.getNumeroHabitacion() == "" || habitacion.getTipo() == "") {
            return null;
        }
        return habitacionRepository.save(habitacion);*/
        Habitacion habitacion = convertirA_Entidad(habitacionDTO);
        if (habitacion.getNumeroHabitacion() == null || habitacion.getTipo() == null
        ||habitacion.getNumeroHabitacion() == "" || habitacion.getTipo() == "") {
            return null;
        }
        habitacionRepository.save(habitacion);
        return convertirA_DTO(habitacion);
    }

    public HabitacionDTO updateHabitacion(int id, HabitacionDTO habitacionDTO) {
        Optional<Habitacion> habitacionOptional = habitacionRepository.findById(id);
        if(!habitacionOptional.isPresent()){
            return null;
        }
        Habitacion habitacion = habitacionOptional.get();
        habitacion.setNumeroHabitacion(habitacionDTO.getNumeroHabitacion());
        habitacion.setTipo(habitacionDTO.getTipo());
        habitacion.setPrecioNoche(habitacionDTO.getPrecioNoche());
        habitacion.setIdHotel(habitacionDTO.getIdHotel());
        if (habitacion.getNumeroHabitacion() == null || habitacion.getTipo() == null
        ||habitacion.getNumeroHabitacion() == "" || habitacion.getTipo() == "") {
            return null;
        }
        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);
        return convertirA_DTO(habitacionActualizada);
    }

    public Page<HabitacionDTO> buscarHabitacionesFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        Integer id = parameters.containsKey("id") ? Integer.parseInt(parameters.get("id")) : null;
        Integer idHotel = parameters.containsKey("idHotel") ? Integer.parseInt(parameters.get("idHotel")) : null;
        String numeroHabitacion = parameters.getOrDefault("numeroHabitacion", null);
        String tipo = parameters.getOrDefault("tipo", null);
        String precioStr = parameters.getOrDefault("precioNoche", null);
        Float precioNoche = (precioStr != null && !precioStr.isEmpty()) ? Float.parseFloat(precioStr) : null;
    
        Page<Habitacion> habitacionPage = habitacionRepository.buscarConFiltros(id, idHotel, numeroHabitacion, tipo, precioNoche, pageable);
        return habitacionPage.map(this::convertirA_DTO);
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

    private HabitacionDTO convertirA_DTO(Habitacion habitacion) {
        return new HabitacionDTO(
        habitacion.getId(),
        habitacion.getIdHotel(),
        habitacion.getNumeroHabitacion(),
        habitacion.getTipo(),
        habitacion.getPrecioNoche()
        );
    }
    private Habitacion convertirA_Entidad(HabitacionDTO habitacionDTO) {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(habitacionDTO.getId());
        habitacion.setIdHotel(habitacionDTO.getIdHotel());
        habitacion.setNumeroHabitacion(habitacionDTO.getNumeroHabitacion());
        habitacion.setTipo(habitacionDTO.getTipo());
        habitacion.setPrecioNoche(habitacionDTO.getPrecioNoche());
        return habitacion;
    }

    
}
