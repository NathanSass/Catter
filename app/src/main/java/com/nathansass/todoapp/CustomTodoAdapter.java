package com.nathansass.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nathansass on 6/8/16.
 */
public class CustomTodoAdapter extends ArrayAdapter {
    public static final String TAG = CustomTodoAdapter.class.getSimpleName();

    public CustomTodoAdapter(Context context, ArrayList<String> todoArr) {
        super(context, 0, todoArr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String todoTitle = (String) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        TextView tvTodoTitle = (TextView) convertView.findViewById(R.id.todoTitle);

        tvTodoTitle.setText(todoTitle);

        return convertView;
    }

}