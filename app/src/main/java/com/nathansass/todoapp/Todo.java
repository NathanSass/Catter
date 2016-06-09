package com.nathansass.todoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by nathansass on 6/8/16.
 */

@Table(name = "Todos")
public class Todo extends Model {

    @Column(name = "Name")
    String title;

    public Todo(String title){
        super();
        this.title = title;
    }

    public Todo() {
        super();
    }

    public static List<Todo> getAll() {
        return new Select()
                .from(Todo.class)
                .orderBy("Name ASC")
                .execute();
    }
}
