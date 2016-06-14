package com.nathansass.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/* Deprecated */
public class EditItemActivity extends AppCompatActivity {
    Context context;
    int duration;

    int position;
    String todoContent;

    EditText etItemTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

         /* Toast Things*/
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        /* Get intent */
        position = getIntent().getIntExtra("position", -1);
        todoContent = getIntent().getStringExtra("itemTitle");

        /* UI Elements */
        etItemTitle = (EditText) findViewById(R.id.etItemTitle);

        updateUI();
    }

    public void updateUI() {
        etItemTitle.append(todoContent);
    }

    public void onUpdateItem(View view) {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("position", position);
        i.putExtra("itemTitle", etItemTitle.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
}
