package com.nathansass.todoapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditTodoDialogFragment extends DialogFragment implements View.OnClickListener {

    Context context;
    int duration;

    Todo todo;

    int position;

    EditText etItemTitle;
    Button btnSubmitEdits;

    Communicator communicator;

    public EditTodoDialogFragment() {

    }

    public static EditTodoDialogFragment newInstance(Todo todo) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", todo.title);
        args.putInt("position", todo.position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_todo_dialog, container);

        todo = new Todo();
        todo.title = getArguments().getString("title");
        todo.position = getArguments().getInt("position");

        /* Get UI elements */
        etItemTitle = (EditText) view.findViewById(R.id.etItemTitle);
        btnSubmitEdits = (Button) view.findViewById(R.id.btnSubmitEdits);

        /* Update UI */
        etItemTitle.append(todo.title);

        btnSubmitEdits.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitEdits ) {

            todo.title = etItemTitle.getText().toString();

            communicator.onDialogMessage(todo);
            dismiss();
        }
    }

    interface Communicator {
        void onDialogMessage (Todo todo);
    }
}

