package org.develop.mappers;


import org.develop.dto.FunkoCreateDto;
import org.develop.dto.FunkoResponseDto;
import org.develop.dto.FunkoUpdateDto;
import org.develop.models.Funko;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FunkoMapperTest {
    FunkoMapper funkoMapper = new FunkoMapper();

    @Test
    void toFunkoTest(){
        Long idTest = 1L;
        FunkoCreateDto funkoCreateDto = FunkoCreateDto.builder()
                .nombre("Test")
                .precio(15.00)
                .cantidad(10)
                .imagen("imagen")
                .categoria("MARVEL")
                .build();

        Funko funkoCreado = funkoMapper.toFunko(idTest, funkoCreateDto);

        assertNotNull(funkoCreado.getId());
        assertNotNull(funkoCreado.getFechaDeCreacion());
        assertNotNull(funkoCreado.getFechaDeActualizacion());
        assertEquals(funkoCreateDto.getNombre(), funkoCreado.getNombre());
        assertEquals(funkoCreateDto.getPrecio(), funkoCreado.getPrecio());
        assertEquals(funkoCreateDto.getCantidad(), funkoCreado.getCantidad());
        assertEquals(funkoCreateDto.getImagen(), funkoCreado.getImagen());
        assertEquals(funkoCreateDto.getCategoria(), funkoCreado.getCategoria());
    }


    @Test
    void toFunkoTest2(){
        LocalDate fechaTest = LocalDate.now();
        LocalDateTime horaTest = LocalDateTime.now();
        FunkoUpdateDto funkoUpdateDto = FunkoUpdateDto.builder()
                .nombre("funko3")
                .precio(11.99)
                .cantidad(1)
                .imagen("holamundo")
                .categoria("MARVEL")
                .build();
        Funko funkoPrueba = new Funko(1L, "pepe", 99.99, 10,"imagentest", "OTROS", fechaTest, horaTest);

        Funko funkoActualizado = funkoMapper.toFunko(funkoUpdateDto, funkoPrueba);

        assertEquals(funkoPrueba.getId(), funkoActualizado.getId());
        assertEquals(funkoPrueba.getFechaDeCreacion(), funkoActualizado.getFechaDeCreacion());
        assertNotNull(funkoActualizado.getFechaDeActualizacion());
        assertEquals(funkoUpdateDto.getNombre(), funkoActualizado.getNombre());
        assertEquals(funkoUpdateDto.getPrecio(), funkoActualizado.getPrecio());
        assertEquals(funkoUpdateDto.getCantidad(), funkoActualizado.getCantidad());
        assertEquals(funkoUpdateDto.getImagen(), funkoActualizado.getImagen());
        assertEquals(funkoUpdateDto.getCategoria(), funkoActualizado.getCategoria());
    }

    @Test
    void toFunkoResponseTest(){
        LocalDate fechaTest = LocalDate.now();
        LocalDateTime horaTest = LocalDateTime.now();

        Funko funkoPrueba = new Funko(1L, "pepe", 99.99, 10,"imagentest", "OTROS", fechaTest, horaTest);

        FunkoResponseDto funkoResponseDto = funkoMapper.toFunkoResponseDto(funkoPrueba);

        assertEquals(funkoPrueba.getId(), funkoResponseDto.getId());
        assertEquals(funkoPrueba.getFechaDeCreacion(), funkoResponseDto.getFechaDeCreacion());
        assertNotNull(funkoResponseDto.getFechaDeActualizacion());
        assertEquals(funkoPrueba.getNombre(), funkoResponseDto.getNombre());
        assertEquals(funkoPrueba.getPrecio(), funkoResponseDto.getPrecio());
        assertEquals(funkoPrueba.getCantidad(), funkoResponseDto.getCantidad());
        assertEquals(funkoPrueba.getImagen(), funkoResponseDto.getImagen());
        assertEquals(funkoPrueba.getCategoria(), funkoResponseDto.getCategoria());
    }
}
