package jair.pruebatecnica.application.service;

import jair.pruebatecnica.domain.model.Todo;
import jair.pruebatecnica.domain.ports.spi.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = Todo.builder()
                .id(1L)
                .title("Test Todo")
                .description("Test Description")
                .completed(false)
                .build();
    }

    @Test
    void saveTodo_ShouldReturnSavedTodo() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo result = todoService.saveTodo(todo);

        assertNotNull(result);
        assertEquals(todo.getId(), result.getId());
        assertEquals(todo.getTitle(), result.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodo_WhenTodoExists_ShouldReturnUpdatedTodo() {
        Todo updatedTodo = Todo.builder()
                .id(1L)
                .title("Updated Title")
                .description("Updated Description")
                .completed(true)
                .build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        Todo result = todoService.updateTodo(1L, updatedTodo);

        assertNotNull(result);
        assertEquals(updatedTodo.getTitle(), result.getTitle());
        assertEquals(updatedTodo.getDescription(), result.getDescription());
        assertTrue(result.isCompleted());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodo_WhenTodoDoesNotExist_ShouldReturnNull() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Todo result = todoService.updateTodo(1L, todo);

        assertNull(result);
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void completeTodo_WhenTodoExists_ShouldMarkAsCompleted() {
        Todo completedTodo = Todo.builder()
                .id(1L)
                .title("Test Todo")
                .description("Test Description")
                .completed(true)
                .build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(completedTodo);

        Todo result = todoService.completeTodo(1L);

        assertNotNull(result);
        assertTrue(result.isCompleted());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void findById_WhenTodoExists_ShouldReturnTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Optional<Todo> result = todoService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(todo.getId(), result.get().getId());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnAllTodos() {
        List<Todo> todos = Arrays.asList(
                todo,
                Todo.builder().id(2L).title("Second Todo").build()
        );

        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.findAll();

        assertEquals(2, result.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void findByCompleted_ShouldReturnFilteredTodos() {
        List<Todo> completedTodos = Arrays.asList(
                Todo.builder().id(2L).title("Completed Todo").completed(true).build()
        );

        when(todoRepository.findByCompleted(true)).thenReturn(completedTodos);

        List<Todo> result = todoService.findByCompleted(true);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isCompleted());
        verify(todoRepository, times(1)).findByCompleted(true);
    }

    @Test
    void deleteById_ShouldCallRepositoryMethod() {
        todoService.deleteById(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }
}