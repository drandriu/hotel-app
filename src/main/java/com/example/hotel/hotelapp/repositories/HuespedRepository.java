package com.example.hotel.hotelapp.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.hotelapp.entities.Huesped;

public interface HuespedRepository extends JpaRepository<Huesped, Integer>{
    void deleteByIdHabitacion(int id);
    @Query("SELECT h FROM Huesped h WHERE " +
           "(:idHuesped IS NULL OR h.idHuesped = :idHuesped) AND " +
           "(:idHabitacion IS NULL OR h.idHabitacion = :idHabitacion) AND " +
           "(:nombre IS NULL OR h.nombre LIKE %:nombre%) AND " +
           "(:apellido IS NULL OR h.apellido LIKE %:apellido%) AND " +
           "(:dniPasaporte IS NULL OR h.dniPasaporte LIKE %:dniPasaporte%) AND " +
           "(:fechaCheckIn IS NULL OR h.fechaCheckIn = :fechaCheckIn) AND " +
           "(:fechaCheckOut IS NULL OR h.fechaCheckOut = :fechaCheckOut)")
    Page<Huesped> buscarConFiltros(@Param("idHuesped") Integer idHuesped,
                                  @Param("idHabitacion") Integer idHabitacion,
                                  @Param("nombre") String nombre,
                                  @Param("apellido") String apellido,
                                  @Param("dniPasaporte") String dniPasaporte,
                                  @Param("fechaCheckIn") Date fechaCheckIn,
                                  @Param("fechaCheckOut") Date fechaCheckOut,
                                  Pageable pageable);
}
