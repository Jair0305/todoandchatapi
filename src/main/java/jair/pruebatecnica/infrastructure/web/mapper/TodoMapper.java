package jair.pruebatecnica.infrastructure.web.mapper;

import jair.pruebatecnica.application.dto.TodoDto;
import jair.pruebatecnica.domain.model.Todo;
import org.springframework.stereotype.Component;


@Component
public class TodoMapper {

    //Convierte un objeto de dominio a DTO
    public TodoDto toDto(Todo todo) {
        if (todo == null) {
            return null;
        }
        
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .build();
    }

    //Convierte un DTO a objeto de dominio
    public Todo toDomain(TodoDto dto) {
        if (dto == null) {
            return null;
        }
        
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(dto.isCompleted())
                .build();
    }
} 