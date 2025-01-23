package com.example.hotel.hotelapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.hotelapp.entities.Habitacion;


public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
    List<Habitacion> findByHotelId(int hotelId);
    @Query("SELECT h FROM Habitacion h WHERE " +
       "(:id IS NULL OR h.id = :id) AND " +
       "(:idHotel IS NULL OR h.hotel.id = :idHotel) AND " +
       "(:numeroHabitacion IS NULL OR h.numeroHabitacion LIKE %:numeroHabitacion%) AND " +
       "(:tipo IS NULL OR h.tipo LIKE %:tipo%) AND " +
       "(:precioNoche IS NULL OR h.precioNoche = :precioNoche)")
    Page<Habitacion> buscarConFiltros(@Param("id") Integer id,
                                 @Param("idHotel") Integer idHotel,
                                 @Param("numeroHabitacion") String numeroHabitacion,
                                 @Param("tipo") String tipo, 
                                 @Param("precioNoche") Float precioNoche, 
                                 Pageable pageable);
}
