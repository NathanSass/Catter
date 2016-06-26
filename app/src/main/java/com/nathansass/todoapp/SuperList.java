package com.nathansass.todoapp;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathansass on 6/25/16.
 */
public class SuperList {
    public static final String TAG = SuperList.class.getSimpleName();

    private ArrayList <Todo> allList = null;

    SuperList() {
        allList = new ArrayList<>();
    }

    public List<Todo> getDeck() {
        return allList;
    }

    public void set(ArrayList todoList){
        allList = todoList;
    }

    public void addTodo(Todo todo) {

        allList.add(todo);

    }

    public void getBitmapImages() {
        final DataRequests dataRequests = new DataRequests();
        for (int i = 0; i < allList.size(); i++) {
            final Todo todo = allList.get(i);
            dataRequests.fetchImageInBackground(todo.imageUrl, new GetImageCallback() {
                @Override
                public void done(Bitmap returnedImage) {
                    todo.catPic = returnedImage;
                    todo.save(); // not necessary
                }
            });

        }
    }

    public void removeTodo(Todo todo) {

        allList.remove(todo);
    }

    public ArrayList<Todo> getTodos(){
        return allList;
    }

    public Todo getTodo(int position) {
        return allList.get(position);
    }

    public int getTodoCount() {
        return allList.size();
    }

    public void clearList() {
        allList = new ArrayList<>();
    }
}
