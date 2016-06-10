package com.nathansass.todoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditTodoDialogFragment.Communicator{

    public static final String TAG = MainActivity.class.getSimpleName();
    private final int REQUEST_CODE = 20;

    Context context;
    int duration;
    CustomTodoAdapter todoAdapter;
    ArrayList<Todo> todoArr;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Adds image to Appbar*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.catty_icon);

        /* Toast Things*/
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        populateArrayItems();

        todoAdapter = new CustomTodoAdapter(this, todoArr);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);

        /* UI Elements*/
        etEditText = (EditText) findViewById(R.id.etEditText);

        /* Click listeners */
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoArr.get(position);
                todoArr.remove(position);
                todoAdapter.notifyDataSetChanged();
                todo.delete();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Todo todo = todoArr.get(position);
                todo.position = position;
                showAlertDialog(todo);

            }
        });
    }

    public void populateArrayItems() {
        todoArr = (ArrayList<Todo>) Todo.getAll();
    }

    public void onAddItem(View view) {
        String todoTitle = etEditText.getText().toString();

        if (todoTitle.length() > 0) { // validation for new items
            Todo newTodo = new Todo();
            newTodo.title = todoTitle;
            newTodo.save();
            todoAdapter.add(newTodo);
            etEditText.setText("");
        }
    }

    private void showAlertDialog(Todo todo) {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoDialogFragment dialog = EditTodoDialogFragment.newInstance(todo);
        dialog.show(fm, "fragment_alert");
    }

    @Override
    public void onDialogMessage(Todo newTodo) {
        Todo todo = todoArr.get(newTodo.position);
        todo.title = newTodo.title;

        todoArr.set(newTodo.position, todo);
        todoAdapter.notifyDataSetChanged();

        todo.save();
    }

    /* Deprecated */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            String todoTitle = data.getStringExtra("itemTitle");
//            int position = data.getIntExtra("position", -1);
//
//            Todo todo = todoArr.get(position);
//            todo.title = todoTitle;
//
//            todoArr.set(position, todo);
//            todoAdapter.notifyDataSetChanged();
//            todo.save();
//        }
//    }
}
