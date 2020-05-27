package com.example.memoir.memoir;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memoir.R;
import com.example.memoir.network.NetworkConnection;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AddToMemoirFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    TextView movieName;
    TextView releaseDate;
    ImageView moviePoster;
    EditText watchDate;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_memoir,container,false);

        SharedPreferences sharedPref= getActivity().getSharedPreferences("Movies", Context.MODE_PRIVATE);
        String movie_name= sharedPref.getString("movie_name",null);
        String release_date= sharedPref.getString("release_date",null);
        String movie_image= sharedPref.getString("movie_image",null);

        movieName = view.findViewById(R.id.movie_name);
        releaseDate = view.findViewById(R.id.release_date);
        moviePoster = view.findViewById(R.id.movie_poster);
        watchDate = view.findViewById(R.id.watch_date);

        movieName.setText(movie_name);
        releaseDate.setText(release_date);
        Picasso.get().load(movie_image).into(moviePoster);
        // let user select date
        watchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        SearchCinemaTask searchCinemaTask = new SearchCinemaTask();
        searchCinemaTask.execute();

        return view;
    }

    // show date picker
    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
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
        watchDate.setText(date);
    }

    private class SearchCinemaTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkConnection.getCinemas();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("cinema", s);
            //[{"id":1,"location":"1341 Dandenong Road","name":"Hoyts Chadstone","postcode":3148},
            //{"id":2,"location":"Pinewood Shopping Centre, Blackburn Rd, Mount Waverley","name":"Waverley Cinema","postcode":3149},
            //{"id":3,"location":"285/287 Springvale Rd","name":"Village Cinemas Century City","postcode":3150}]
        }
    }
}
