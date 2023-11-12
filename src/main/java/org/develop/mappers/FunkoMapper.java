package org.develop.mappers;

import org.develop.dto.FunkoCreateDto;
import org.develop.dto.FunkoResponseDto;
import org.develop.dto.FunkoUpdateDto;
import org.develop.models.Funko;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class FunkoMapper {

    public Funko toFunko(Long id, FunkoCreateDto dto){
        return new Funko(
                id,
                dto.getNombre(),
                dto.getPrecio(),
                dto.getCantidad(),
                dto.getImagen(),
                dto.getCategoria(),
                LocalDate.now(),
                LocalDateTime.now()
        );
    }

    public Funko toFunko(FunkoUpdateDto dto, Funko funko){
        return new Funko(
                funko.getId(),
                dto.getNombre() != null ? dto.getNombre() : funko.getNombre(),
                dto.getPrecio() > 0 ? dto.getPrecio() : funko.getPrecio(),
                dto.getCantidad() > 0 ? dto.getCantidad() : funko.getCantidad(),
                dto.getImagen() != null ? dto.getImagen() : funko.getImagen(),
                dto.getCategoria() != null ? dto.getCategoria() : funko.getCategoria(),
                funko.getFechaDeCreacion(),
                LocalDateTime.now()
        );
    }

    public FunkoResponseDto toFunkoResponseDto(Funko funko){
        return FunkoResponseDto.builder()
                .id(funko.getId())
                .nombre(funko.getNombre())
                .precio(funko.getPrecio())
                .cantidad(funko.getCantidad())
                .imagen(funko.getImagen())
                .categoria(funko.getCategoria())
                .fechaDeCreacion(funko.getFechaDeCreacion())
                .fechaDeActualizacion(funko.getFechaDeActualizacion())
                .build();
    }

}
