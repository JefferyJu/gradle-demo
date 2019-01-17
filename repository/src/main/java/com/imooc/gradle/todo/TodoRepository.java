package com.imooc.gradle.todo;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟存储TODO应用程序
 */
public class TodoRepository {
    private static Map<String, TodoItem> itemMap = new HashMap<>();

    /**
     * 保存待办事项
     *
     * @param item 待办事项
     */
    public void save(TodoItem item) {
        System.out.println("" + item);
        itemMap.put(item.getName(), item);
    }

    /**
     * 查询
     *
     * @param name
     * @return
     */
    public TodoItem query(String name) {
        System.out.println("" + name);
        return itemMap.get(name);
    }
}
