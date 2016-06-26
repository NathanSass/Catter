package com.nathansass.todoapp;

import android.graphics.Bitmap;

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
    public static final String TAG =Todo.class.getSimpleName();

    @Column(name = "Name")
    String title;

    @Column(name = "Birthday")
    long birthDay;

    @Column(name = "ImageUrl")
    String imageUrl;

    @Column(name = "Personality")
    int personality;

    int position;

    Bitmap catPic;

    public Todo(String title, int position, long birthDay, String imageUrl, Bitmap catPic, int personality){
        super();
        this.title = title;
        this.position = position;
        this.birthDay = birthDay;
        this.imageUrl = imageUrl;
        this.catPic = catPic;
        this.personality = personality;

    }

    public Todo() {
        super();
    }

    public static List<Todo> getAll() {
        return new Select()
                .from(Todo.class)
//                .orderBy("Name ASC")
                .execute();
    }
}
