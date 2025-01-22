package com.example.hotel.hotelapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Habitacion {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private int idHotel; //foreing key
    private String numeroHabitacion;
    private String tipo;
    private float precioNoche;


    // Constructor vacío
    public Habitacion() {
    }

    public Habitacion(int id, int idHotel, String numeroHabitacion, String tipo, float precioNoche) {
        this.id = id;
        this.idHotel = idHotel;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
    }



    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(String numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(float precioNoche) {
        this.precioNoche = precioNoche;
    }


}
