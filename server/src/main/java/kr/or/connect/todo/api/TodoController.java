package kr.or.connect.todo.api;


import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.dto.CreateTodoDTO;
import kr.or.connect.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {


    private final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;


    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody CreateTodoDTO createTodoDTO) {
        return todoService.create(createTodoDTO.getTodo());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> get(){
        return todoService.findAll();
    }


}
