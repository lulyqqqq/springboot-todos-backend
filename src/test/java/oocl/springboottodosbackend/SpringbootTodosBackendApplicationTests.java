package oocl.springboottodosbackend;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class SpringbootTodosBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
