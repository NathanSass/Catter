package com.nathansass.todoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;


public class EditTodoDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = EditTodoDialogFragment.class.getSimpleName();
    Context context;
    int duration;

    Todo todo;

    ImageView catPicture;
    EditText etItemTitle, etAddBday;
    Button btnSubmitEdits, btnChangePhoto;
    TextView tvDisposition;

    SeekBar sbDisposition;

    Communicator communicator;

    private Calendar calendar;
    private int year, month, day;

    public EditTodoDialogFragment() {

    }

    public static EditTodoDialogFragment newInstance(Todo todo) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle args = new Bundle();

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

        context = getContext();

        int position = getArguments().getInt("position");

        todo = TodoList.get().getTodos().get(position);

        /* Get UI elements */
        etItemTitle = (EditText) view.findViewById(R.id.etItemTitle);
        etAddBday = (EditText) view.findViewById(R.id.etAddBday);
        btnSubmitEdits = (Button) view.findViewById(R.id.btnSubmitEdits);
        btnChangePhoto = (Button) view.findViewById(R.id.btnChangePhoto);
        catPicture = (ImageView) view.findViewById(R.id.catPicture);
        sbDisposition = (SeekBar) view.findViewById(R.id.sbDisposition);
        tvDisposition = (TextView) view.findViewById(R.id.tvDisposition);

        /* Datepicker */
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(todo.birthDay);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        /* Update UI */
        etItemTitle.append(todo.title);
        catPicture.setImageBitmap(todo.catPic);

        showDate(year, month, day);

        showDatepickerOnFocus();
        setSeekBarListener();

        btnSubmitEdits.setOnClickListener(this);
        btnChangePhoto.setOnClickListener(this);

        return view;
    }

    public void  setSeekBarListener() {
        sbDisposition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String mood = "";
                int color = 1;
                if (progress == 0) {
                    mood = context.getString(R.string.lowMood);
                    color = ContextCompat.getColor(context, R.color.lowMood);
                } else if (progress == 1) {
                    mood = context.getString(R.string.mediumMood);
                   color = ContextCompat.getColor(context, R.color.mediumMood);
                } else if ( progress ==2 ) {
                    mood = context.getString(R.string.highMood);
                    color = ContextCompat.getColor(context, R.color.highMood);
                }

                todo.personality = progress;
                tvDisposition.setText(mood);
                tvDisposition.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        } else if (v.getId() == R.id.btnChangePhoto ) {
            changeCatPhoto();
        }
    }

    public void changeCatPhoto() {
        final DataRequests dataRequests = new DataRequests();
        dataRequests.fetchImageUrlsInBackground("cat", new GetImageUrlsCallback() {
            @Override
            public void done(JSONArray returnedUrls) {
                String imageUrl = null;
                try {
                    JSONObject returnedUrl = (JSONObject) returnedUrls.get(new Random().nextInt(returnedUrls.length()));

                    String farmId = returnedUrl.getInt("farm") + "";
                    String serverId = returnedUrl.getString("server");
                    String id = returnedUrl.getString("id");
                    String secret = returnedUrl.getString("secret");
                    String size = "n";

                    imageUrl = "https://farm" + farmId + ".staticflickr.com/" + serverId + "/" + id + "_" + secret + "_" + size + ".jpg";

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                todo.imageUrl = imageUrl;

                dataRequests.fetchImageInBackground(imageUrl, new GetImageCallback() {
                    @Override
                    public void done(Bitmap returnedImage) {
                        todo.catPic = returnedImage;
                        catPicture.setImageBitmap(returnedImage);
                    }
                });
            }
        });
    }

    interface Communicator {
        void onDialogMessage (Todo todo);
    }
}

