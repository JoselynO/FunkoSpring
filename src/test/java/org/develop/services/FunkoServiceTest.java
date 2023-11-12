package org.develop.services;

import org.develop.exceptions.FunkoNotFound;
import org.develop.mappers.FunkoMapper;
import org.develop.models.Funko;
import org.develop.repositories.FunkosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FunkoServiceTest {
    @Mock
    private FunkosRepository funkosRepository;
    @Mock
    private FunkoMapper funkoMapper;
    @InjectMocks
    private FunkoServiceImpl funkoService;
    Funko funkoPrueba1 = new Funko(1L, "pepe", 99.99, 10,"imagentest1", "OTROS", LocalDate.now(), LocalDateTime.now());
    Funko funkoPrueba2 = new Funko(2L, "maria", 33.22, 5,"imagentest2", "DISNEY", LocalDate.now(), LocalDateTime.now());


    @Test
    void testFindAll(){
        List<Funko> listaFunkos = new ArrayList<>();
        listaFunkos.add(funkoPrueba1);
        listaFunkos.add(funkoPrueba2);

        when(funkosRepository.findAll()).thenReturn(listaFunkos);

        List <Funko> funkosActuales = funkoService.findAll("");

        assertNotNull(funkosActuales);

        verify(funkosRepository, times(1)).findAll();
    }

    @Test
    void testFindAllCategoria(){
        String categoria = "DISNEY";
        List<Funko> listaFunkos = new ArrayList<>();
        listaFunkos.add(funkoPrueba2);

        when(funkosRepository.findAllByCategoria(categoria)).thenReturn(listaFunkos);

        List <Funko> funkosActuales = funkoService.findAll(categoria);

        assertNotNull(funkosActuales);

        verify(funkosRepository, times(1)).findAllByCategoria(categoria);
    }

    @Test
    void deleteByIdTest(){
        long id = 1L;

        when(funkosRepository.findById(id)).thenReturn(Optional.of(funkoPrueba1));

        funkoService.deleteById(id);

        verify(funkosRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteByIdTestFalse(){
        long id = 100L;

        when(funkosRepository.findById(id)).thenReturn(Optional.empty());

        var error = assertThrows(FunkoNotFound.class, () -> funkoService.deleteById(id));
        assertEquals("Funko no encontrado con id: " + id, error.getMessage() );
    }
}
