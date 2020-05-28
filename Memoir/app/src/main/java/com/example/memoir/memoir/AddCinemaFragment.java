package com.example.memoir.memoir;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memoir.R;
import com.example.memoir.entity.Cinema;
import com.example.memoir.network.NetworkConnection;

public class AddCinemaFragment extends Fragment {

    Button submitButton;

    EditText name;
    EditText location;
    EditText postcode;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cinema, container, false);

        name = view.findViewById(R.id.name);
        location = view.findViewById(R.id.location);
        postcode = view.findViewById(R.id.postcode);


        submitButton = view.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCinemaTask addCinemaTask = new AddCinemaTask();
                addCinemaTask.execute(name.getText().toString(),location.getText().toString(),postcode.getText().toString());
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private class AddCinemaTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            int id = NetworkConnection.countCinema() + 1;
            Cinema cinema = new Cinema(id+"",strings[0],strings[1],strings[2]); //String id, String name, String location, String postcode
            return NetworkConnection.addCinema(cinema);
        }
    }
}
