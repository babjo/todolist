package kr.or.connect.todo.api;


import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.todo.dto.CreateTodoDTO;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TodoControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.wac)
                .alwaysDo(print(System.out))
                .build();
    }


    @Test
    public void testCreateTodo_todoIsEmpty() throws Exception {
        // GIVEN
        String requestBody = objectMapper.writeValueAsString(new CreateTodoDTO(""));

        // WHEN
        mvc.perform(
                post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message").value("todo : may not be empty"));
    }

    @Test
    public void testCreateTodo_todoHasSomething() throws Exception {
        // GIVEN
        String requestBody = objectMapper.writeValueAsString(new CreateTodoDTO("Buy a bag"));

        // WHEN
        mvc.perform(
                post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                // THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.todo").value("Buy a bag"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    public void testGetTodos() throws Exception {
        // GIVEN
        createTodo("Have dinner with my family");
        createTodo("Have dinner with my friends");

        // WHEN
        mvc.perform(
                get("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].todo").value("Have dinner with my family"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[1].todo").value("Have dinner with my friends"))
                .andExpect(jsonPath("$[1].completed").value(false))
                .andExpect(jsonPath("$[1].date").exists());
    }


    public void createTodo(String todo) throws Exception {
        String requestBody = objectMapper.writeValueAsString(new CreateTodoDTO(todo));
        mvc.perform(
                post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
    }
}
