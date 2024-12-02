package oocl.springboottodosbackend.service;

import oocl.springboottodosbackend.exception.NotExistsException;
import oocl.springboottodosbackend.model.Todo;
import oocl.springboottodosbackend.repository.TodosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TodoService {
    private final TodosRepository todosRepository;

    public TodoService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    public Todo findById(Integer id) {
        Todo todo = todosRepository.findById(id).orElse(null);
        if (Objects.isNull(todo)) {
            throw new NotExistsException();
        }

        return todo;
    }

    public List<Todo> getAll() {
        return todosRepository.findAll();
    }

    public Todo create(Todo todo) {
        return todosRepository.save(todo);
    }

    public void delete(Integer id) {
        todosRepository.deleteById(id);
    }

    public Todo update(Integer id, Todo todo) {
        Todo existTodos = todosRepository.findById(id).orElseThrow();
        if (existTodos == null) {
            throw new NotExistsException();
        }
        return todosRepository.save(todo);
    }

}
