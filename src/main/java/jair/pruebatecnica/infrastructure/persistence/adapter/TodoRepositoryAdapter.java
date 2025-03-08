package jair.pruebatecnica.infrastructure.persistence.adapter;

import jair.pruebatecnica.domain.model.Todo;
import jair.pruebatecnica.domain.ports.spi.TodoRepository;
import jair.pruebatecnica.infrastructure.persistence.entity.TodoEntity;
import jair.pruebatecnica.infrastructure.persistence.repository.JpaTodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoRepositoryAdapter implements TodoRepository {

    private final JpaTodoRepository jpaTodoRepository;

    @Override
    public Todo save(Todo todo) {
        TodoEntity entity = mapToEntity(todo);
        TodoEntity savedEntity = jpaTodoRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return jpaTodoRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public List<Todo> findAll() {
        return jpaTodoRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return jpaTodoRepository.findByCompleted(completed).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaTodoRepository.deleteById(id);
    }

    //Mapea una entidad JPA a un objeto de dominio
    private Todo mapToDomain(TodoEntity entity) {
        return Todo.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .completed(entity.isCompleted())
                .build();
    }

    //Mapea un objeto de dominio a una entidad JPA
    private TodoEntity mapToEntity(Todo todo) {
        return TodoEntity.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .build();
    }
} 