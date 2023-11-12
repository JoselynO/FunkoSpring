package org.develop.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.develop.dto.FunkoCreateDto;
import org.develop.dto.FunkoUpdateDto;
import org.develop.models.Funko;
import org.develop.services.FunkoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class FunkoRestController {
   private final FunkoService funkoService;

    @Autowired
    public FunkoRestController(FunkoService funkoService) {
        this.funkoService = funkoService;
    }

    @GetMapping("/funkos")
    public ResponseEntity<List<Funko>> getAllFunkos(@RequestParam(required = false) String categoria) {
        log.info("Buscando todos los Funkos con categoria: "  + categoria);
        return ResponseEntity.ok(funkoService.findAll(categoria));
    }

    @GetMapping("/funkos/{id}")
    public ResponseEntity<Funko> getFunkoById(@PathVariable Long id) {
        log.info("Buscando Funko por id: " + id);
        return ResponseEntity.ok(funkoService.findById(id));
    }

    @PostMapping("/funkos")
    public ResponseEntity<Funko> createFunko(@Valid @RequestBody FunkoCreateDto funkoCreateDto) {
        log.info("Creando Funko: " + funkoCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(funkoService.save(funkoCreateDto));
    }

    @PutMapping("/funkos/{id}")
    public ResponseEntity<Funko> updateFunko(@PathVariable Long id, @Valid @RequestBody FunkoUpdateDto funkoUpdateDto) {
        log.info("Actualizando Funko con id: " + id + " con Funko " + funkoUpdateDto);
        return ResponseEntity.ok(funkoService.update(id, funkoUpdateDto));
    }

    @PatchMapping("/funkos/{id}")
    public ResponseEntity<Funko> patchFunko(@PathVariable Long id, @Valid @RequestBody FunkoUpdateDto funkoUpdateDto) {
        log.info("Actualizando parcialmente Funko con id: " + id + " con Funko " + funkoUpdateDto);
        return ResponseEntity.ok(funkoService.update(id, funkoUpdateDto));
    }



    @DeleteMapping("/funkos/{id}")
    public ResponseEntity<Void> deleteFunko(@PathVariable Long id) {
        log.info("Borrando Funko por id: " + id);
        funkoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
