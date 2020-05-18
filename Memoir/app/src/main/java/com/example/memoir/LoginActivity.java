package com.example.memoir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memoir.network.NetworkConnection;


public class LoginActivity extends AppCompatActivity {

    NetworkConnection networkConnection = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        networkConnection = new NetworkConnection();

        //skip to register activity
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //login
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                LoginTask loginTask = new LoginTask();
                loginTask.execute(username.getText().toString(),password.getText().toString());
            }
        });

    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.login(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
