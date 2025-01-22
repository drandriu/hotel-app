package com.example.hotel.hotelapp.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Huesped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int idHuesped;


    private int idHabitacion; //id unico de la habitacion a la que pertenecen

    private String nombre;
    private String apellido;
    private String dniPasaporte;
    private Date fechaCheckIn;
    private Date fechaCheckOut;

    // Constructor vacío
    public Huesped() {
    }


    public Huesped(int idHuesped, int idHabitacion, String nombre, String apellido, String dniPasaporte, Date fechaCheckIn, Date fechaCheckOut) {
        this.idHuesped = idHuesped;
        this.idHabitacion = idHabitacion;
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
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int id) {
        this.idHabitacion = id;
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
