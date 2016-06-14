package com.nathansass.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nathansass on 6/8/16.
 */
public class CustomTodoAdapter extends ArrayAdapter<Todo> {
    public static final String TAG = CustomTodoAdapter.class.getSimpleName();

    TextView tvTodoTitle, tvTodoBday;
    int year, month, day;
    Calendar calendar;

    public CustomTodoAdapter(Context context, ArrayList<Todo> todoArr) {
        super(context, 0, todoArr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        /* Date Manipulation */
        calendar = Calendar.getInstance();
        long millis = todo.birthDay;


        Date date = new Date(millis);
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        /* UI components */
        tvTodoTitle = (TextView) convertView.findViewById(R.id.todoTitle);
        tvTodoBday = (TextView) convertView.findViewById(R.id.tvBirthday);

        tvTodoTitle.setText(todo.title);
        tvTodoBday.setText(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year));

        return convertView;
    }

}
