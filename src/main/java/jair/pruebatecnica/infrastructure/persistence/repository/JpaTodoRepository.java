package jair.pruebatecnica.infrastructure.persistence.repository;

import jair.pruebatecnica.infrastructure.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Todo
 */
@Repository
public interface JpaTodoRepository extends JpaRepository<TodoEntity, Long> {
    
    List<TodoEntity> findByCompleted(boolean completed);
} 