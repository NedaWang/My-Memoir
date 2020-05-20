package com.example.memoir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.memoir.entity.Person;
import com.example.memoir.network.NetworkConnection;
import com.example.memoir.util.DateUtil;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public EditText dob;
    public Button submit;
    public boolean checkAddPerson = false;

    NetworkConnection networkConnection = null;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        networkConnection = new NetworkConnection();

        //show date picker
        dob = findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountPersonTask countPersonTask = new CountPersonTask();
                countPersonTask.execute();
                showDatePickerDialog();
            }
        });

        // submit application form
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] info = new String[9];
                EditText firstname = findViewById(R.id.firstname);
                EditText surname = findViewById(R.id.surname);
                RadioButton female = findViewById(R.id.female);
                String gender = "m";
                if (female.isChecked()) {
                    gender = "f";
                }
                EditText dob = findViewById(R.id.dob);
                EditText address = findViewById(R.id.address);
                Spinner state = findViewById(R.id.state);
                EditText postcode = findViewById(R.id.postcode);
                EditText email = findViewById(R.id.email);

                if (empty(firstname) || empty(surname) || empty(address) || empty(postcode) || empty(email)) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all the cells", Toast.LENGTH_SHORT).show();
                } else {
                    CountPersonTask countPersonTask = new CountPersonTask();
                    countPersonTask.execute();
                    info[0] = id + "";
                    info[1] = formatString(firstname);
                    info[2] = formatString(surname);
                    info[3] = gender;
                    info[4] = DateUtil.convertDobToServerFormat(dob.getText().toString());
                    info[5] = formatString(address);
                    info[6] = state.getSelectedItem().toString();
                    info[7] = formatString(postcode);
                    info[8] = formatString(email);
                }
                Person person = new Person(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8]);
                AddPersonTask addPersonTask = new AddPersonTask();
                addPersonTask.execute(person);
                Intent intent = new Intent(RegisterActivity.this, CredentialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("person",person);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    // if an edit text is empty
    public boolean empty(EditText et) {
        return et.getText().toString().trim().isEmpty();
    }

    public String formatString(EditText et) {
        return et.getText().toString().trim();
    }

    // show date picker
    public void showDatePickerDialog() {
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
        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
        dob.setText(date);
    }

    // count person task
    private class CountPersonTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            return networkConnection.countPerson();
        }
        @Override
        protected void onPostExecute(Integer integer) {
            id = integer + 1;
        }
    }

    // add person
    private class AddPersonTask extends AsyncTask<Person, Void, String> {
        @Override
        protected String doInBackground(Person... objects) {
            return networkConnection.addPerson((Person) objects[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            checkAddPerson = true;
        }
    }
}
