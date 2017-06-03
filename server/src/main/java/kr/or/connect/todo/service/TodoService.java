package kr.or.connect.todo.service;


import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Todo> findAll() {
        return todoDao.selectAll();
    }

    public void complete(Long id) {
        Todo todo = todoDao.selectById(id);
        if (todo == null){
            throw new IllegalArgumentException("id : doesn't exist");
        }

        todo.setCompleted(true);
        todoDao.update(todo);
    }

    public void remove(Long id) {
        if(!todoDao.deleteById(id)){
            throw new IllegalArgumentException("id : doesn't exist");
        }
    }
}
