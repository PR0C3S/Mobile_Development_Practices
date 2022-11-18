package com.pucmm.practica3.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(tableName = "producto_table")
public class Producto {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String nombre ="";
    private String descripcion="";
    private Double precio=0d;
    private String fotoLocation = "";


    public Producto()
    {

    }

    public Producto(Long id, String nombre, String descripcion, Double precio, String fotoLocation) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fotoLocation = fotoLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFotoLocation() {
        return fotoLocation;
    }

    public void setFotoLocation(String fotoLocation) {
        this.fotoLocation = fotoLocation;
    }
}
