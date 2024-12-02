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


}
