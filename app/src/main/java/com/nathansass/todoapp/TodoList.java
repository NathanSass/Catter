package com.nathansass.todoapp;

/**
 * Created by nathansass on 6/25/16.
 */
public class TodoList extends SuperList {
    private static TodoList instance = null;

    protected TodoList(){
        // Exists only to defeat instantiation.
    }

    public static TodoList get() {
        if (instance == null) {
            instance = new TodoList();
        }

        return instance;
    }


}
