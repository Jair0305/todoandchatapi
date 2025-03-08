package jair.pruebatecnica.domain.ports.api;

import jair.pruebatecnica.domain.model.Todo;

import java.util.List;
import java.util.Optional;


public interface TodoService {
    
    //Guarda una nueva tarea
    Todo saveTodo(Todo todo);
    
    //Actualiza una tarea existente
    Todo updateTodo(Long id, Todo todo);
    
    //Marca una tarea como completada
    Todo completeTodo(Long id);
    
    //Busca una tarea por su ID
    Optional<Todo> findById(Long id);
    
    //Obtiene todas las tareas
    List<Todo> findAll();
    
    //Obtiene las tareas filtradas por estado (completadas o no)
    List<Todo> findByCompleted(boolean completed);
    
    //Elimina una tarea por su ID
    void deleteById(Long id);
} 