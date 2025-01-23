package com.example.hotel.hotelapp.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Huesped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idHuesped;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion; //id unico de la habitacion a la que pertenecen

    private String nombre;
    private String apellido;

    private String dniPasaporte;
    private Date fechaCheckIn;
    private Date fechaCheckOut;

    // Constructor vacío
    public Huesped() {
    }


    public Huesped(int idHuesped, Habitacion habitacion, String nombre, String apellido, String dniPasaporte, Date fechaCheckIn, Date fechaCheckOut) {
        this.idHuesped = idHuesped;
        this.habitacion = habitacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dniPasaporte = dniPasaporte;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
    }
    



    
    // Getters y Setters
    public int getIdHuesped(){
        return idHuesped;
    }
    public void setIdHuesped(int id){
        this.idHuesped = id;
    }
    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDniPasaporte() {
        return dniPasaporte;
    }

    public void setDniPasaporte(String dniPasaporte) {
        this.dniPasaporte = dniPasaporte;
    }

    public Date getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(Date fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public Date getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setFechaCheckOut(Date fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }
}
