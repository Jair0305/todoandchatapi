package jair.pruebatecnica.application.service;

import jair.pruebatecnica.domain.model.Todo;
import jair.pruebatecnica.domain.ports.api.TodoService;
import jair.pruebatecnica.domain.ports.spi.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Implementación de la lógica de negocio para tareas (Todo)
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        // Validamos que exista la tarea
        return todoRepository.findById(id)
                .map(existingTodo -> {
                    // Actualizamos solo los campos permitidos
                    existingTodo.setTitle(todo.getTitle());
                    existingTodo.setDescription(todo.getDescription());
                    existingTodo.setCompleted(todo.isCompleted());
                    // Mantenemos el ID original
                    return todoRepository.save(existingTodo);
                })
                .orElse(null); // Retorna null si no existe
    }

    @Override
    public Todo completeTodo(Long id) {
        // Busca y actualiza el estado de la tarea si existe
        return todoRepository.findById(id)
                .map(existingTodo -> {
                    existingTodo.setCompleted(true);
                    return todoRepository.save(existingTodo);
                })
                .orElse(null);
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
} 