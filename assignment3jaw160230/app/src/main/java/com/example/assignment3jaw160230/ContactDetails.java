package com.example.assignment3jaw160230;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ContactDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ArrayList<String> new_data;
    ArrayList<String> old_data;
    boolean deleted = false;
    boolean create_new = false;

    int toChange = 0;

    //on create, reads in the old data and the boolean for a new contact. then sets the onclick listener for the dates.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        Intent intent = getIntent();
        old_data = intent.getStringArrayListExtra("old data");
        create_new = intent.getBooleanExtra("create new", false);

        TextView date1 = findViewById(R.id.date1);
        TextView date2 = findViewById(R.id.date2);

        //sets the onclick listener for the two date functions, saves the id for future reference to update the fields
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DateFragment();
                picker.show(getSupportFragmentManager(), "datePicker");
                toChange = v.getId();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DateFragment();
                picker.show(getSupportFragmentManager(), "datePicker");
                toChange = v.getId();
            }
        });



        /*

        displayDate = (TextView) findViewById(R.id.date1);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ContactDetails.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = month + "/" + dayOfMonth + "/" + year;
                displayDate.setText(date);
            }
        };

        displayDate1 = (TextView) findViewById(R.id.date2);
        displayDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ContactDetails.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = month + "/" + dayOfMonth + "/" + year;
                displayDate1.setText(date);
            }
        };

*/
        if (!create_new)
        {
            EditText edittext1 = findViewById(R.id.input1);
            EditText edittext2 = findViewById(R.id.input2);
            EditText edittext3 = findViewById(R.id.input3);
            EditText edittext4 = findViewById(R.id.date1);
            EditText edittext5 = findViewById(R.id.date2);
            edittext1.setText(old_data.get(0));
            edittext2.setText(old_data.get(1));
            edittext3.setText(old_data.get(2));
            edittext4.setText(old_data.get(3));
            edittext5.setText(old_data.get(4));
        }
    }

    public void save_contact(View view)
    {
        read_input();
        return_main();
    }

    public void delete_contact(View view)
    {
        deleted = true;
        return_main();
    }

    //reads from the fields on the screen
    private void read_input()
    {
        ArrayList<String> result = new ArrayList<String>();
        EditText edittext1 = findViewById(R.id.input1);
        EditText edittext2 = findViewById(R.id.input2);
        EditText edittext3 = findViewById(R.id.input3);
        EditText edittext4 = findViewById(R.id.date1);
        EditText edittext5 = findViewById(R.id.date2);
        result.add(edittext1.getText().toString());
        result.add(edittext2.getText().toString());
        result.add(edittext3.getText().toString());
        result.add(edittext4.getText().toString());
        result.add(edittext5.getText().toString());
        new_data = result;
    }

    //sets the data so the parent activity can read it and returns
    public void return_main()
    {
        Intent output = new Intent();
        output.putStringArrayListExtra("new data",new_data);
        output.putStringArrayListExtra("old data",old_data);
        output.putExtra("create new", create_new);

        if (deleted)
        {
            output.putExtra("deleted", true);
        }
        else
        {
            output.putExtra("deleted", false);
        }

        setResult(RESULT_OK, output); // this marks data as good
        finish(); //this ends the activity
    }

    //sets the textview dates to the selected date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String current = DateFormat.getDateInstance().format(c.getTime());

        TextView textView = findViewById(toChange);
        textView.setText(current);
    }
}
