package jair.pruebatecnica.domain.ports.spi;

import jair.pruebatecnica.domain.model.Todo;

import java.util.List;
import java.util.Optional;


public interface TodoRepository {
    
    //Guarda una tarea
    Todo save(Todo todo);
    
    //Busca una tarea por su ID
    Optional<Todo> findById(Long id);
    
    //Obtiene todas las tareas
    List<Todo> findAll();
    
    //Obtiene tareas filtradas por su estado
    List<Todo> findByCompleted(boolean completed);
    
    //Elimina una tarea por su ID
    void deleteById(Long id);
} 