package com.example.hotel.hotelapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.hotelapp.entities.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

}
