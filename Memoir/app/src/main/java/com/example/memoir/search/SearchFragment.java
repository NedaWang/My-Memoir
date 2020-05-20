package com.example.memoir.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.memoir.R;
import com.example.memoir.network.SearchMovieAPI;

public class SearchFragment extends Fragment {

    private EditText input;
    private TextView output;
    private Button search;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        input = view.findViewById(R.id.input);
        output = view.findViewById(R.id.output);
        search = view.findViewById(R.id.search_movie);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMovieTask searchMovieTask = new SearchMovieTask();
                searchMovieTask.execute(input.getText().toString());
            }
        });

        return view;
    }

    private class SearchMovieTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return SearchMovieAPI.searchMovie(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            output.setText(s);
        }
    }

}
