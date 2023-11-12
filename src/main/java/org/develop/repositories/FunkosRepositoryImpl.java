package org.develop.repositories;

import lombok.extern.slf4j.Slf4j;
import org.develop.models.Funko;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

@Repository
@Slf4j
public class FunkosRepositoryImpl implements FunkosRepository{
    private Map<Long, Funko> funkos = new LinkedHashMap<>();
    private IdGenerator contador = new IdGenerator();

    public FunkosRepositoryImpl(){
        readFile("funkos.csv")
                .subscribe(fk -> save(fk));
    }
    @Override
    public List<Funko> findAll() {
        return funkos.values().stream().toList();
    }

    @Override
    public List<Funko> findAllByCategoria(String categoria) {
        return funkos.values().stream()
                .filter(funko -> funko.getCategoria().toLowerCase().contains(categoria.toLowerCase()))
                .toList();
    }

    public int nextId(){
        return contador.getAndIncrement();
    }

    @Override
    public Optional<Funko> findById(Long id) {
        return funkos.get(id) != null ? Optional.of(funkos.get(id)) : Optional.empty();
    }

    @Override
    public Funko save(Funko funko) {
        if(funko.getId() == null) {
            funko.setId((long) contador.getAndIncrement());
        }
        funkos.put(funko.getId(), funko);
        return funko;
    }

    public void limpiarRepositorio(){
        funkos.clear();
    }

    @Override
    public void deleteById(Long id) {
          funkos.remove(id);
    }

    public Flux<Funko> readFile(String nomFile) {
        log.debug("Leyendo fichero CSV");
        String path = Paths.get("").toAbsolutePath().toString() + File.separator + "data" + File.separator + nomFile;
        return Flux.create(sink->{
            try(BufferedReader reader =new BufferedReader(new FileReader(path))){
                String line;
                String[] lineas;
                reader.readLine();
                while ((line = reader.readLine()) != null){
                    Funko fk = Funko.builder().build();
                    fk.setFunko(line);
                    sink.next(fk);
                }
                sink.complete();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }





}

