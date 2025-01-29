package com.example.hotel.hotelapp.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.hotelapp.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}
