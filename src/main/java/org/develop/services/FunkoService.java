package org.develop.services;

import org.develop.dto.FunkoCreateDto;
import org.develop.dto.FunkoUpdateDto;
import org.develop.models.Funko;

import java.util.List;

public interface FunkoService {
    List<Funko> findAll(String categoria);
    Funko update(Long id, FunkoUpdateDto funkoUpdateDto);
    Funko findById(Long id);
    Funko save(FunkoCreateDto funkoCreateDto);
    void deleteById(Long id);
}
