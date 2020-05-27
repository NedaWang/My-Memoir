package com.example.memoir;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memoir.entity.Credential;
import com.example.memoir.entity.Person;
import com.example.memoir.network.NetworkConnection;
import com.example.memoir.util.DateUtil;
import com.example.memoir.util.Encrypt;
import java.util.Date;

public class CredentialActivity extends AppCompatActivity {


    NetworkConnection networkConnection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential);

        networkConnection = new NetworkConnection();

        Button button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText password1 = findViewById(R.id.password1);
                EditText password2 = findViewById(R.id.password2);
                // password can't be null
                if(empty(password1) || empty(password2)){
                    Toast.makeText(CredentialActivity.this, "Password can not bu null", Toast.LENGTH_SHORT).show();
                } // 2 password have to be same
                else if (!password1.getText().toString().equals(password2.getText().toString())) {
                    password1.setText("");
                    password2.setText("");
                    Toast.makeText(CredentialActivity.this, "Password input is inconsistent", Toast.LENGTH_SHORT).show();
                } // create credential when passwords are valid value
                else {
                    Person person = getIntent().getExtras().getParcelable("person");
                    Credential credential = new Credential(person.getId(),Encrypt.md5(password1.getText().toString()),DateUtil.toServerFormat(new Date()),person);
                    AddCredentialTask addCredentialTask = new AddCredentialTask();
                    addCredentialTask.execute(credential);
                    // after create a value, go back to login page
                    Intent intent = new Intent(CredentialActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    public boolean empty(EditText et) {
        return et.getText().toString().trim().isEmpty();
    }

    private class AddCredentialTask extends AsyncTask<Credential, Void, String> {

        @Override
        protected String doInBackground(Credential... objects) {
            return networkConnection.addCredential(objects[0]);
        }
    }
}
