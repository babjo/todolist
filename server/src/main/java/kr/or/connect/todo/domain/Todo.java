package kr.or.connect.todo.domain;


import java.time.LocalDateTime;

public class Todo {
    private Long id;
    private String todo;
    private boolean completed;
    private LocalDateTime date;

    public Todo() {
    }

    public Todo(String todo, boolean completed, LocalDateTime date) {
        this(null, todo, completed, date);
    }

    private Todo(Long id, String todo, boolean completed, LocalDateTime date) {
        this.id = id;
        this.todo = todo;
        this.completed = completed;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
