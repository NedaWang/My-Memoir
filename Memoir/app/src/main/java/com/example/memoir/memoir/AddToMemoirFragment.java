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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.memoir.R;
import com.example.memoir.entity.Cinema;
import com.example.memoir.entity.Memoir;
import com.example.memoir.entity.Person;
import com.example.memoir.network.NetworkConnection;
import com.example.memoir.util.DateUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddToMemoirFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    TextView movieName;
    TextView releaseDate;
    ImageView moviePoster;
    EditText watchDate;
    Spinner cinemas;
    RatingBar score;
    EditText comment;
    Button submit;

    List<String> cinemaInfoList;
    ArrayAdapter<String> cinemaSpinnerAdapter;

    List<Cinema> cinemaList;

    ImageView addCinema;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_memoir,container,false);

        SharedPreferences sharedPref= getActivity().getSharedPreferences("Movies", Context.MODE_PRIVATE);
        final String movie_name= sharedPref.getString("movie_name",null);
        final String release_date= sharedPref.getString("release_date",null);
        String movie_image= sharedPref.getString("movie_image",null);


        movieName = view.findViewById(R.id.movie_name);
        releaseDate = view.findViewById(R.id.release_date);
        moviePoster = view.findViewById(R.id.movie_poster);
        watchDate = view.findViewById(R.id.watch_date);
        cinemas = view.findViewById(R.id.cinema_spinner);
        score = view.findViewById(R.id.score);
        comment = view.findViewById(R.id.comment);
        submit = view.findViewById(R.id.submit);

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

        cinemaInfoList = new ArrayList<>();
        cinemaSpinnerAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,cinemaInfoList);
        cinemaSpinnerAdapter.notifyDataSetChanged();

        addCinema = view.findViewById(R.id.add_cinema);
        addCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCinemaFragment addCinemaFragment = new AddCinemaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, addCinemaFragment);
                //content_frame
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cinema c = new Cinema((cinemas.getSelectedItemPosition()+1)+"");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
                Person p = new Person(sharedPreferences.getString("id", null));
                Memoir m = new Memoir();
                m.setName(movie_name);
                m.setReleaseDate(DateUtil.convertDobToServerFormat(release_date));
                m.setWatchDate(DateUtil.convertDobToServerFormat(watchDate.getText().toString()));
                m.setComment(comment.getText().toString());
                m.setScore(((int)(score.getRating()*20))+"");
                m.setPerson(p);
                m.setCinema(c);
                AddMemoirTask addMemoirTask = new AddMemoirTask();
                addMemoirTask.execute(m);


                //Log.i("add to memoir:", score.getRating()+comment.getText().toString()+cinemas.getSelectedItem().toString());
            }
        });

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
        // set max date
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    // date string
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
        watchDate.setText(date);
    }

    private class SearchCinemaTask extends AsyncTask<Void, Void, List<Cinema>>{

        @Override
        protected List<Cinema> doInBackground(Void... voids) {
            return NetworkConnection.getCinemas();
        }

        @Override
        protected void onPostExecute(List<Cinema> cinemaFromDB) {
            cinemaList = cinemaFromDB;
            for (Cinema c: cinemaFromDB){
                cinemaInfoList.add(c.getName() + ", " + c.getPostcode());
            }
            cinemas.setAdapter(cinemaSpinnerAdapter);
        }
    }

    private class AddMemoirTask extends AsyncTask<Memoir, Void, String>{

        @Override
        protected String doInBackground(Memoir... memoirs) {
            int id = NetworkConnection.countMemoir() + 1;
            Memoir memoir = memoirs[0];
            memoir.setId(id+"");
            return NetworkConnection.addMemoir(memoir);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), "Add Memoir successfully.", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }

}
