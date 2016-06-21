package com.nathansass.todoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;


public class EditTodoDialogFragment extends DialogFragment implements View.OnClickListener {

    Context context;
    int duration;

    Todo todo;

    ImageView catPicture;
    EditText etItemTitle, etAddBday;
    Button btnSubmitEdits;

    Communicator communicator;

    private Calendar calendar;
    private int year, month, day;

    public EditTodoDialogFragment() {

    }

    public static EditTodoDialogFragment newInstance(Todo todo) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", todo.title);
        args.putInt("position", todo.position);
        args.putLong("birthDay", todo.birthDay);
        args.putString("imageUrl", todo.imageUrl);
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
        todo.birthDay = getArguments().getLong("birthDay");
        todo.imageUrl = getArguments().getString("imageUrl");

        /* Get UI elements */
        etItemTitle = (EditText) view.findViewById(R.id.etItemTitle);
        etAddBday = (EditText) view.findViewById(R.id.etAddBday);
        btnSubmitEdits = (Button) view.findViewById(R.id.btnSubmitEdits);
        catPicture = (ImageView) view.findViewById(R.id.catPicture);

        /* Datepicker */
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(todo.birthDay);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        /* Update UI */
        etItemTitle.append(todo.title);
        setCatPicture();

        showDate(year, month, day);

        showDatepickerOnFocus();

        btnSubmitEdits.setOnClickListener(this);

        return view;
    }

    public void setCatPicture() {
        final DataRequests dataRequests = new DataRequests();
        dataRequests.fetchImageInBackground(todo.imageUrl, new GetImageCallback() {
            @Override
            public void done(Bitmap returnedImage) {
                catPicture.setImageBitmap(returnedImage);
            }
        });
    }

    public void showDatepickerOnFocus() {

        etAddBday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showPickerDialog();

                }
            }
        });

        etAddBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Clicked2", Toast.LENGTH_LONG).show();
                showPickerDialog();
            }
        });
    }

    public void showPickerDialog() {
        SelectDateFragment newFragment = new SelectDateFragment();
        newFragment.show(this.getFragmentManager(), "DatePicker");
    }

    public void showDate(int year, int month, int day) {
        /* updates value of todoItem here, gets sent automatically when onclick is called*/
        calendar.set(year, month, day);
        long bDay = calendar.getTimeInMillis();
        todo.birthDay = bDay;

        etAddBday.setText(new StringBuilder().append(month + 1).append("/")
                .append(day).append("/").append(year));
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
