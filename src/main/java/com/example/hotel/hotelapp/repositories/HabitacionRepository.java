package com.example.hotel.hotelapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.hotelapp.entities.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
    List<Habitacion> findByIdHotel(int idHotel);

}
