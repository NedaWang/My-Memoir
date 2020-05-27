package com.example.memoir.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.memoir.R;
import com.example.memoir.memoir.AddToMemoirFragment;
import com.example.memoir.network.SearchMovieAPI;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewFragment extends Fragment {
    //FirebaseFirestore db;
    TextView movieName;
    TextView releaseDate;
    TextView genre;
    TextView storyline;
    TextView cast;
    //TextView score;
    RatingBar score;
    TextView director;

    Button addToMemoir;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        // get info from previous fragment
        SharedPreferences sharedPref= getActivity().getSharedPreferences("Movies", Context.MODE_PRIVATE);
        String message= sharedPref.getString("movie_id",null);

        movieName = view.findViewById(R.id.movie_name);
        releaseDate = view.findViewById(R.id.release_date);
        genre = view.findViewById(R.id.genre);
        storyline = view.findViewById(R.id.storyline);
        cast = view.findViewById(R.id.cast);
        score = view.findViewById(R.id.score);
        director = view.findViewById(R.id.director);

        if (message != null){
            SearchMovieDetailTask searchTask = new SearchMovieDetailTask();
            searchTask.execute(message);
        }

        addToMemoir = view.findViewById(R.id.add_to_memoir);
        addToMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToMemoirFragment addToMemoirFragment = new AddToMemoirFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, addToMemoirFragment);
                //content_frame
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    // https://api.themoviedb.org/3/movie/150540?api_key=###&append_to_response=credits
    public class SearchMovieDetailTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String url_head = "https://api.themoviedb.org/3/movie/";
            String url_middle = "?api_key=";
            String url_end = "&append_to_response=credits";
            String url = url_head + strings[0] + url_middle + getString(R.string.tmdb_api_key) + url_end;
            return SearchMovieAPI.searchByUrl(url);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject json = new JSONObject(s);
                JSONArray genres = json.getJSONArray("genres");
                JSONObject credits = json.getJSONObject("credits");

                String genreString = "";
                for (int i = 0; i < genres.length(); i++){
                    genreString += genres.getJSONObject(i).getString("name") + "\n";
                }
                genreString = genreString.substring(0,genreString.length()-1);
                genre.setText(genreString);

                String castStirng = "";
                JSONArray casts = credits.getJSONArray("cast");
                for (int i = 0; i < casts.length(); i ++){
                    castStirng += casts.getJSONObject(i).getString("name") + "\n";
                }
                castStirng = castStirng.substring(0,castStirng.length()-1);
                cast.setText(castStirng);

                String directors = "";
                JSONArray crews = credits.getJSONArray("crew");
                for (int i = 0; i < crews.length(); i ++){
                    if (crews.getJSONObject(i).getString("job").equals("Director")) {
                        directors += crews.getJSONObject(i).getString("name") + "\n";
                    }
                }
                directors = directors.substring(0,directors.length()-1);
                director.setText(directors);

                releaseDate.setText(json.getString("release_date"));
                movieName.setText(json.getString("original_title"));
                storyline.setText(json.getString("overview"));
                String scoreString = json.getString("vote_average");
                // this field has a default value 0.0
                if (!scoreString.equals("0.0")){
                    //score.setText(scoreString);
                    score.setRating(Float.valueOf(scoreString)/2);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//https://www.tutlane.com/tutorial/android/android-ratingbar-with-examples get user score
}

