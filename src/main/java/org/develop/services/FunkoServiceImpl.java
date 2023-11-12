package org.develop.services;

import lombok.extern.slf4j.Slf4j;
import org.develop.dto.FunkoCreateDto;
import org.develop.dto.FunkoUpdateDto;
import org.develop.exceptions.FunkoNotFound;
import org.develop.mappers.FunkoMapper;
import org.develop.models.Funko;
import org.develop.repositories.FunkosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@CacheConfig(cacheNames = {"funkos"})
@Service
public class FunkoServiceImpl implements FunkoService{
    private final FunkosRepository funkosRepository;
    private final FunkoMapper funkoMapper;

    @Autowired
    public FunkoServiceImpl(FunkosRepository funkosRepository, FunkoMapper funkoMapper) {
        this.funkosRepository = funkosRepository;
        this.funkoMapper = funkoMapper;
    }

    @Override
    public List<Funko> findAll(String categoria) {
        if (categoria == null || categoria.isEmpty()){
            log.info("Buscando todos los Funkos");
            return funkosRepository.findAll();
        }
        log.info("Buscando todos los Funkos por su categoria: " + categoria);
        return funkosRepository.findAllByCategoria(categoria);
    }

    @Override
    @Cacheable
    public Funko findById(Long id) {
        log.info("Buscando Funkos por id: " + id);
        return funkosRepository.findById(id).orElseThrow(() -> new FunkoNotFound(id));
    }

    @Override
    @CachePut
    public Funko save(FunkoCreateDto funkoCreateDto) {
        log.info("Guardando Funko: " + funkoCreateDto);
        long id = funkosRepository.nextId();
        Funko funko = funkoMapper.toFunko(id, funkoCreateDto);
        return funkosRepository.save(funko);
    }

    @Override
    @CachePut
    public Funko update(Long id, FunkoUpdateDto funkoUpdateDto){
        log.info("Actualizando Funko por id: " + id);
        Funko funkoExiste = this.findById(id);
        Funko funkoActualizado = funkoMapper.toFunko(funkoUpdateDto, funkoExiste);
        return funkosRepository.save(funkoActualizado);
    }

    @Override
    @CacheEvict
    public void deleteById(Long id) {
        log.info("Borrando Funko por id: " + id);
        this.findById(id);
        funkosRepository.deleteById(id);
    }

    public void limpiarServicio(){
        funkosRepository.limpiarRepositorio();
    }
}
