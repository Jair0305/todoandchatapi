package jair.pruebatecnica.infrastructure.web.controller;

import jair.pruebatecnica.application.dto.TodoDto;
import jair.pruebatecnica.domain.model.Todo;
import jair.pruebatecnica.domain.ports.api.TodoService;
import jair.pruebatecnica.infrastructure.web.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/todos")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TodoMapper todoMapper;

    //Crea una nueva tarea
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        Todo todo = todoMapper.toDomain(todoDto);
        Todo createdTodo = todoService.saveTodo(todo);
        return new ResponseEntity<>(todoMapper.toDto(createdTodo), HttpStatus.CREATED);
    }

    //Actualiza una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        Todo todo = todoMapper.toDomain(todoDto);
        Todo updatedTodo = todoService.updateTodo(id, todo);
        
        if (updatedTodo == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(todoMapper.toDto(updatedTodo));
    }

    //Marca una tarea como completada
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id) {
        Todo completedTodo = todoService.completeTodo(id);
        
        if (completedTodo == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(todoMapper.toDto(completedTodo));
    }

    //Obtiene una tarea por su ID
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        return todoService.findById(id)
                .map(todo -> ResponseEntity.ok(todoMapper.toDto(todo)))
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtiene todas las tareas
    @GetMapping
    public List<TodoDto> getAllTodos() {
        return todoService.findAll().stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    //Obtiene tareas filtradas por estado
    @GetMapping("/filter")
    public List<TodoDto> getTodosByCompleted(@RequestParam boolean completed) {
        return todoService.findByCompleted(completed).stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    //Elimina una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 