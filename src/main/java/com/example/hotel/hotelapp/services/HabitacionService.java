package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.dtos.DynamicSearchDTO;
import com.example.hotel.hotelapp.dtos.HabitacionDTO;
import com.example.hotel.hotelapp.entities.Habitacion;
import com.example.hotel.hotelapp.entities.Hotel;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;
import com.example.hotel.hotelapp.repositories.HotelRepository;
import com.example.hotel.hotelapp.repositories.HuespedRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired HotelRepository hotelRepository;

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
        // Imprimir el objeto para ver su contenido
        //System.out.println("***************Habitacion: " + habitacion.toString());
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
        Optional<Hotel> hotel = (hotelRepository.findById(habitacionDTO.getIdHotel()));
        if(!hotel.isPresent()){
            return null;
        }
        habitacion.setHotel(hotel.get());
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
    
        huespedRepository.deleteByHabitacionId(id);
        habitacionRepository.deleteById(id);
        return true;
    }

    private HabitacionDTO convertirA_DTO(Habitacion habitacion) {
        return new HabitacionDTO(
        habitacion.getId(),
        habitacion.getHotel().getId(),
        habitacion.getNumeroHabitacion(),
        habitacion.getTipo(),
        habitacion.getPrecioNoche()
        );
    }
    private Habitacion convertirA_Entidad(HabitacionDTO habitacionDTO) {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(habitacionDTO.getId());
        Optional<Hotel> hotel = hotelRepository.findById(habitacionDTO.getIdHotel());
        if(!hotel.isPresent()){
            return null;
        }
        habitacion.setHotel(hotel.get());
        habitacion.setNumeroHabitacion(habitacionDTO.getNumeroHabitacion());
        habitacion.setTipo(habitacionDTO.getTipo());
        habitacion.setPrecioNoche(habitacionDTO.getPrecioNoche());
        return habitacion;
    }

    public Page<HabitacionDTO> buscarHabitacionesDinamico(DynamicSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(
            searchDTO.getPage().getPageIndex(),
            searchDTO.getPage().getPageSize()
        );

        Specification<Habitacion> spec = buildSpecification(searchDTO.getListSearchCriteria());

        if (searchDTO.getListOrderCriteria() != null && !searchDTO.getListOrderCriteria().isEmpty()) {
            Sort sort = buildSort(searchDTO.getListOrderCriteria());
            pageable = PageRequest.of(searchDTO.getPage().getPageIndex(), searchDTO.getPage().getPageSize(), sort);
        }

        Page<Habitacion> habitaciones = habitacionRepository.findAll(spec, pageable);
        return habitaciones.map(this::convertirA_DTO);
        }

    private Specification<Habitacion> buildSpecification(List<DynamicSearchDTO.SearchCriteria> criteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
                
            for (DynamicSearchDTO.SearchCriteria criteria : criteriaList) {
                switch (criteria.getOperation().toLowerCase()) {
                    case "equals":
                        if (criteria.getKey().equals("hotel")) {
                            // Aquí, buscamos por el id de la habitación (relación)
                            predicates.add(criteriaBuilder.equal(root.get("hotel").get("id"), criteria.getValue()));
                        } else {
                            // Búsqueda normal para otros campos
                            predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
                        }
                        break;
                    case "lower":
                        predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue()));
                        break;
                    case "higher":
                        predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue()));
                        break;
                    case "like":
                        predicates.add(criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                        break;
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Sort buildSort(List<DynamicSearchDTO.OrderCriteria> orderCriteria) {
        List<Sort.Order> orders = orderCriteria.stream()
            .map(criteria -> new Sort.Order(
                criteria.getValuesortOrder().equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                criteria.getSortBy()
            ))
            .collect(Collectors.toList());
        return Sort.by(orders);
    }
}
