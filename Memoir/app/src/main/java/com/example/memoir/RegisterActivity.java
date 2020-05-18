package com.example.memoir;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public EditText dob;
    public RadioButton female;
    public RadioButton male;
    public Spinner state;
    public Button submit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //show date picker
        dob = findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        female = findViewById(R.id.female);
        male = findViewById(R.id.male);

        state = findViewById(R.id.state);

        // submit application form
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstname = findViewById(R.id.firstname);
                EditText surname = findViewById(R.id.surname);
                EditText address = findViewById(R.id.address);
                EditText postcode = findViewById(R.id.postcode);
                EditText email = findViewById(R.id.email);
                EditText password1 = findViewById(R.id.password1);
                EditText password2 = findViewById(R.id.password2);
                if (empty(firstname) || empty(surname) || empty(address) ||empty(postcode) ||empty(email) ||empty(password1) ||empty(password2)){
                    Toast.makeText(RegisterActivity.this, "Please fill in all the cells", Toast.LENGTH_SHORT).show();
                }else if(!password1.getText().toString().equals(password2.getText().toString())){
                    password1.setText("");
                    password2.setText("");
                    Toast.makeText(RegisterActivity.this, "Password input is inconsistent", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // if an edit text is empty
    public boolean empty(EditText et){
        return  et.getText().toString().trim().isEmpty();
    }

    // show date picker
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // date string
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        dob.setText(date);
    }
}
