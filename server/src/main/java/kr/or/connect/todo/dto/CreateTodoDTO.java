package kr.or.connect.todo.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateTodoDTO {
    @NotEmpty
    private String todo;

    public CreateTodoDTO() {
    }

    public CreateTodoDTO(String todo) {
        this.todo = todo;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
