package com.nathansass.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        etEditText = (EditText) findViewById(R.id.etEditText);


        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoArr.remove(position);
                todoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(context, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("itemTitle", todoArr.get(position).title);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    public void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        todoArr = new ArrayList<>();
        try {
            ArrayList<String> todoItems = new ArrayList<>(FileUtils.readLines(file)); // best way to avoid this duplication
            for (int i = 0; i < todoItems.size(); i++) {
                String currentTitle = todoItems.get(i);
                todoArr.add(new Todo(currentTitle));
            }
        } catch (IOException e) {
            todoArr.add(new Todo("Unable to load existing todo items"));
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            ArrayList<String> arrToWrite = new ArrayList<>(); //BUGBUG: Not pretty

            for (int i = 0; i < todoArr.size(); i++) {
                String todoTitle = todoArr.get(i).title;
                arrToWrite.add(todoTitle);
            }

            FileUtils.writeLines(file, arrToWrite);
        } catch (IOException e) {
            Toast.makeText(context, "There was an error saving to do items", duration).show();
        }
    }

    public void populateArrayItems() {
        readItems();
    }

    public void onAddItem(View view) {
        String newItem = etEditText.getText().toString();
        if (newItem.length() > 0) { // validation for new items
            todoAdapter.add(new Todo(etEditText.getText().toString()));
            etEditText.setText("");
            writeItems();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            int position = data.getIntExtra("position", -1);
            String todoContent = data.getStringExtra("itemTitle");
            Todo updatedTodo = new Todo(todoContent);
            todoArr.set(position, updatedTodo);

            todoAdapter.notifyDataSetChanged();
            writeItems();

        }
    }
}
