package oocl.springboottodosbackend.repository;

import oocl.springboottodosbackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepository extends JpaRepository<Todo, Integer> {
}