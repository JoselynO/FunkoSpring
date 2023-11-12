package org.develop.repositories;

import org.develop.models.Funko;

import java.util.List;
import java.util.Optional;

public interface FunkosRepository {
    List<Funko> findAll();
    List<Funko> findAllByCategoria(String categoria);
    Optional<Funko> findById(Long id);
    Funko save(Funko funko);
    void deleteById(Long id);

    void limpiarRepositorio();
    int nextId();
}
