function renderTodoApp(todolist, state) {
    renderHeaderView();
    renderMainView(todolist, state);
    renderFooterView(todolist, state);
}

function renderHeaderView() {
    var headerHtml = '<h1>todos</h1><input class="new-todo" placeholder="What needs to be done?" autofocus="">';
    $('.header').html(headerHtml);

    $(".new-todo").keyup(function (e) {
        if (e.keyCode === 13) {
            var todoInput = $(this);
            createTodo(todoInput.val(), function () {
                getTodoList(function (todolist) {
                    renderTodoApp(todolist, $('.todoapp').data('state'));
                    todoInput.val("");
                });
            });
        }
    });
}

function renderMainView(todolist, state) {
    if (state === "active") {
        todolist = todolist.filter(function (todo) {
            return !todo.completed
        });
    } else if (state === 'completed') {
        todolist = todolist.filter(function (todo) {
            return todo.completed
        });
    }

    var copiedTodolist = JSON.parse(JSON.stringify(todolist));
    copiedTodolist.sort(function (a, b) {
        var aDate = new Date(a.date);
        var bDate = new Date(b.date);
        return ((aDate < bDate) ? 1 : ((aDate > bDate) ? -1 : 0));
    });

    var todolistHtml = '';
    for (var i = 0; i < copiedTodolist.length; i++) {
        todolistHtml += todoHtml(copiedTodolist[i].id, copiedTodolist[i].todo, copiedTodolist[i].completed);
    }

    var mainHtml = '<input class="toggle-all" type="checkbox"><label for="toggle-all">Mark all as complete</label><ul class="todo-list">' + todolistHtml + '</ul>';
    $('.main').html(mainHtml);

    $('.toggle').click(function (e) {
        var todoId = $(this).data('id');
        completeTodo(todoId, function () {
            getTodoList(function (todolist) {
                renderTodoApp(todolist, $('.todoapp').data('state'));
            });
        });
    });

    $('.destroy').click(function (e) {
        var todoId = $(this).data('id');
        removeTodo(todoId, function () {
            getTodoList(function (todolist) {
                renderTodoApp(todolist, $('.todoapp').data('state'));
            });
        });
    });
}

function todoHtml(id, todo, completed) {
    return '<li class="' + (completed ? "completed" : "") + '"><div class="view"><input data-id=' + id + ' class="toggle" type="checkbox" ' + (completed ? "checked" : "") + '><label>' + todo + '</label><button data-id=' + id + ' class="destroy"></button></div> <input class="edit" value="Create a TodoMVC template"></li>';
}

function renderFooterView(todolist, state) {
    var notCompletedCount = todolist.filter(function (todo) {
        return !todo.completed
    }).length;

    var todoCountHtml = '<span class="todo-count"><strong>' + notCompletedCount + '</strong> item left</span>';
    var filtersHtml = '<ul class="filters"><li><a ' + (state === "all" ? "class=selected" : "") + ' href="#/">All</a></li><li><a href="#/active" ' + (state === "active" ? "class=selected" : "") + '>Active</a></li><li><a href="#/completed" ' + (state === "completed" ? "class=selected" : "") + '>Completed</a></li></ul>';
    var footerHtml = '<!-- This should be `0 items left` by default -->' + todoCountHtml + '<!-- Remove this if you dont implement routing -->' + filtersHtml + '</ul> <!-- Hidden if no completed items are left ↓ --> <button class="clear-completed">Clear completed</button>';
    $('.footer').html(footerHtml);

    $('a[href="#/"]').click(function (e) {
        e.preventDefault();
        getTodoList(function (todolist) {
            renderTodoApp(todolist, 'all');
            $('.todoapp').data('state', 'all');
        });
    });

    $('a[href="#/active"]').click(function (e) {
        e.preventDefault();
        getTodoList(function (todolist) {
            renderTodoApp(todolist, 'active');
            $('.todoapp').data('state', 'active');
        });
    });

    $('a[href="#/completed"]').click(function (e) {
        e.preventDefault();
        getTodoList(function (todolist) {
            renderTodoApp(todolist, 'completed');
            $('.todoapp').data('state', 'completed');
        });
    });

    $('.clear-completed').click(function(e) {
        var completedTodoIds = todolist.filter(function (todo) {
            return todo.completed
        }).map(function(todo){
            return todo.id;
        });

        removeTodos(completedTodoIds, function(){
            getTodoList(function (todolist) {
                renderTodoApp(todolist, $('.todoapp').data('state'));
            });
        });
    });
}