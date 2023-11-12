package org.develop.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class Funko {
    private Long id;
    @NotBlank( message = "El nombre no puede estar vacio")
    private String nombre;
    @Min(value = 0, message = "El precio no puede ser negativo")
    private double precio;
    @Min(value = 0, message = "La cantidad no puede ser menor a 0")
    private int cantidad;
    private String imagen;
    @NotBlank( message = "La categoria no puede estar vacia")
    private String categoria;
    private LocalDate fechaDeCreacion;
    private LocalDateTime fechaDeActualizacion;

    public Funko setFunko(String line){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] lineas = line.split(",");
        setNombre(lineas[1]);
        setCategoria(lineas[2]);
        setPrecio(Double.parseDouble(lineas[3]));
        setFechaDeCreacion(LocalDate.parse(lineas[4],formatter));
        setFechaDeActualizacion(LocalDateTime.now());
        return this;
    }

    @JsonCreator
    public Funko(
            @JsonProperty("id") long id,
            @JsonProperty("nombre") String nombre,
            @JsonProperty("precio") double precio,
            @JsonProperty("cantidad") int cantidad,
            @JsonProperty("imagen") String imagen,
            @JsonProperty("categoria") String categoria,
            @JsonProperty("fechaDeCreacion") LocalDate fechaCreacion,
            @JsonProperty("fechaDeActualizacion") LocalDateTime fechaActualizacion
    ) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.categoria = categoria;
        this.fechaDeCreacion = fechaCreacion;
        this.fechaDeActualizacion = fechaActualizacion;
    }



}
