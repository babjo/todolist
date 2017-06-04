(function (window) {
    'use strict';

    // Your starting point. Enjoy the ride!
    $('.todoapp').data('state', 'all');
    getTodoList(function (todolist) {
        renderTodoApp(todolist, $('.todoapp').data('state'));
    });

})(window);