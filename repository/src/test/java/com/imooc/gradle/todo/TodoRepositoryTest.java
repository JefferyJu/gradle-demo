package com.imooc.gradle.todo;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试用例
 * @author  Jeffery_Ju
 * @date 2019/1/17 22:15:27
 */
public class TodoRepositoryTest {
    private TodoRepository repository = new TodoRepository();

    @Test
    public void TestSave() {
        TodoItem item = new TodoItem("luban");
        repository.save(item);
        Assert.assertNotNull(repository.query(item.getName()));
        //Assert.assertNull(repository.query(item.getName()));
    }
}
