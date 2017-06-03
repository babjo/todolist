package kr.or.connect.todo.service;


import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TodoService {

    private final TodoDao todoDao;

    @Autowired
    public TodoService(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    public Todo create(String todo) {
        Todo newTodo = new Todo();
        newTodo.setTodo(todo);
        newTodo.setDate(LocalDateTime.now());

        Long id = todoDao.insert(newTodo);

        return todoDao.selectById(id);
    }

}
