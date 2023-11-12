package org.develop.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class FunkoResponseDto {
    private Long id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String imagen;
    private String categoria;
    private LocalDate fechaDeCreacion;
    @Builder.Default
    private LocalDateTime fechaDeActualizacion = LocalDateTime.now();
}
