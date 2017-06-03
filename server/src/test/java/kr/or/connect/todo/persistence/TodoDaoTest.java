package kr.or.connect.todo.persistence;


import kr.or.connect.todo.TodoApplication;
import kr.or.connect.todo.domain.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TodoDaoTest {

    @Autowired
    private TodoDao todoDao;

    @Test
    public void testInsert_todoIsRight() {
        // GIVEN
        Todo todo = new Todo("Buy a bag", false, LocalDateTime.now());

        // WHEN
        Long id = todoDao.insert(todo);

        // THEN
        Todo selected = todoDao.selectById(id);
        assertThat(selected.getTodo(), is("Buy a bag"));
    }
}
