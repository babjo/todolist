function createTodo(todo, callback) {
    if (todo.length === 0) {
        alert('빈칸은 할일로 추가할 수 없어요. 할일 내용을 적어주세요!');
    } else {
        $.ajax({
            type: "POST",
            url: "/api/todos",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify({todo: todo}),
            success: function (data) {
                callback();
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    }
}

function completeTodo(todoId, callback) {
    $.ajax({
        type: "PUT",
        url: "/api/todos/" + todoId,
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            callback();
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function getTodoList(callback) {
    $.ajax({
        type: "GET",
        url: "/api/todos",
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            callback(data);
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function removeTodo(todoId, callback) {
    $.ajax({
        type: "DELETE",
        url: "/api/todos/" + todoId,
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            callback();
        },
        error: function (e) {
            alert(e.responseText);
        }
    });
}

function removeTodos(todoIds, callback){
    if (todoIds.length === 0) {
        callback();
    }else{
        var todoId = todoIds[0];
        removeTodo(todoId, function(){
            removeTodos(todoIds.slice(1, todoIds.length), callback);
        });
    }
}