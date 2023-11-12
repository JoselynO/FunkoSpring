package org.develop.repositories;

import org.develop.models.Funko;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FunkoRepositoryTest {
    FunkosRepositoryImpl funkosRepository = new FunkosRepositoryImpl();

    @BeforeEach
    void setTest(){
        funkosRepository.limpiarRepositorio();
        Funko funkoPrueba1 = new Funko(1L, "pepe", 99.99, 10,"imagentest1", "OTROS", LocalDate.now(), LocalDateTime.now());
        Funko funkoPrueba2 = new Funko(2L, "maria", 33.22, 5,"imagentest2", "DISNEY", LocalDate.now(), LocalDateTime.now());
        funkosRepository.save(funkoPrueba1);
        funkosRepository.save(funkoPrueba2);
    }

    @Test
    void findAllTest(){
        List<Funko> listaFunkos = funkosRepository.findAll();

        assertNotNull(listaFunkos);
        assertEquals(2, listaFunkos.size());
    }

    @Test
    void findAllCategoriaTest(){
        String categoria = "DISNEY";
        List<Funko> listaFunkos = funkosRepository.findAllByCategoria(categoria);

        assertNotNull(listaFunkos);
        assertEquals(1, listaFunkos.size());
     }

     @Test
    void findByIdTest(){
        Long id = 1L;
        Optional<Funko> funko = funkosRepository.findById(id);

        assertNotNull(funko);
        assertTrue(funko.isPresent());
     }

    @Test
    void findByIdTestFalse(){
        long id = 88L;
        Optional<Funko> funko = funkosRepository.findById(id);

        assertTrue(funko.isEmpty());
    }

    @Test
    void deleteByIdTest(){
        Long id = 1L;
        funkosRepository.deleteById(id);

        List<Funko> funkos = funkosRepository.findAll();

        assertEquals(1, funkos.size());
    }



}




