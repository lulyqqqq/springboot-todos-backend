package oocl.springboottodosbackend.controller;

import jakarta.annotation.Resource;
import oocl.springboottodosbackend.exception.NotExistsException;
import oocl.springboottodosbackend.model.Todo;
import oocl.springboottodosbackend.repository.TodosRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class TodosControllerTest {
    @Autowired
    private MockMvc client;

    @Autowired
    private TodosRepository todoRepository;

    @Resource
    private JacksonTester<List<Todo>> todosJacksonTester;


    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
        todoRepository.save(new Todo(null, "text1", false));
        todoRepository.save(new Todo(null, "text2", true));
        todoRepository.save(new Todo(null, "text3", false));
        todoRepository.save(new Todo(null, "text4", false));
        todoRepository.save(new Todo(null, "text5", true));
    }

    @Test
    void should_return_todos_when_get_all_given_todo_exist() throws Exception {
        //given
        final List<Todo> givenEmployees = todoRepository.findAll();

        //when
        //then
        final String jsonResponse = client.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<Todo> employeesResult = todosJacksonTester.parseObject(jsonResponse);
        assertThat(employeesResult)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenEmployees);
    }

    @Test
    void should_return_todo_when_get_by_id() throws Exception {
        // Given
        final Todo givenTodo = todoRepository.findAll().get(0);

        // When
        // Then
        client.perform(MockMvcRequestBuilders.get("/todos/" + givenTodo.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(givenTodo.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(givenTodo.getText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(givenTodo.getDone()));
    }

    @Test
    void should_create_todo_success() throws Exception {
        // Given
        todoRepository.deleteAll();
        String givenText = "text";
        boolean givenDone = false;

        String givenEmployee = String.format(
                "{\"text\": \"%s\", \"done\": \"%b\"}",
                givenText,
                givenDone
        );
        // When
        // Then
        client.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(givenText))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(givenDone));
        List<Todo> todos = todoRepository.findAll();
        assertThat(todos).hasSize(1);
        AssertionsForClassTypes.assertThat(todos.get(0).getId()).isNotNull();
        AssertionsForClassTypes.assertThat(todos.get(0).getText()).isEqualTo(givenText);
        AssertionsForClassTypes.assertThat(todos.get(0).getDone()).isFalse();
    }

    @Test
    void should_return_success_when_remove_todo_given_a_id() throws Exception {
        // Given
        final List<Todo> givenTodo = todoRepository.findAll();
        int givenId = givenTodo.get(0).getId();

        // When
        // Then
        client.perform(MockMvcRequestBuilders.delete("/todos/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Todo> todos = todoRepository.findAll();
        assertThat(todos).hasSize(4);
        AssertionsForClassTypes.assertThat(todos.get(0).getId()).isEqualTo(givenTodo.get(1).getId());
        AssertionsForClassTypes.assertThat(todos.get(1).getId()).isEqualTo(givenTodo.get(2).getId());
        AssertionsForClassTypes.assertThat(todos.get(2).getId()).isEqualTo(givenTodo.get(3).getId());
        AssertionsForClassTypes.assertThat(todos.get(3).getId()).isEqualTo(givenTodo.get(4).getId());
    }






}
