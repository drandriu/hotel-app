package com.example.hotel.hotelapp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Habitacion {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;
    private String numeroHabitacion;
    private String tipo;
    private float precioNoche;


    // Constructor vacío
    public Habitacion() {
    }

    public Habitacion(int id, Hotel hotel, String numeroHabitacion, String tipo, float precioNoche) {
        this.id = id;
        this.hotel = hotel;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
    }


    // Sobrescribir toString para imprimir los valores de los campos
    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", idHotel=" + hotel.getId() +
                ", numeroHabitacion='" + numeroHabitacion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precioNoche=" + precioNoche +
                '}';
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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
