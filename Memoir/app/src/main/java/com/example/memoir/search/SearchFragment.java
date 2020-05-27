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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.memoir.R;
import com.example.memoir.network.SearchMovieAPI;
import com.example.memoir.view.ViewFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchFragment extends Fragment {

    private EditText input;
    private TextView output;
    private Button search;

    TableLayout tableLayout;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        input = view.findViewById(R.id.input);
        search = view.findViewById(R.id.search_movie);
        tableLayout = view.findViewById(R.id.movie_list);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTable(tableLayout);
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
            //output.setText(s);
            TableRow row;
            TextView name;
            TextView date;
            ImageView image;
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("results");
                //output.setText(array.length()+"");
                for (int i = 0; i < array.length(); i++) {
                    final String movieName = array.getJSONObject(i).getString("title");
                    final String movieDate = array.getJSONObject(i).getString("release_date");
                    String movieImage = array.getJSONObject(i).getString("poster_path");
                    final String movieID = array.getJSONObject(i).getString("id");
                    // b43e380b2a3295ab244b24f4887d9d0d
                    // https://api.themoviedb.org/3/search/movie?api_key=b43e380b2a3295ab244b24f4887d9d0d&query=Jack+Reacher
                    // https://api.themoviedb.org/3/movie/150540?api_key=b43e380b2a3295ab244b24f4887d9d0d&append_to_response=credits
                    if (movieDate.equals("null") || movieImage.equals("null") || movieName.equals("null")) {

                    } else {
                        row = new TableRow(getActivity());
                        TableLayout.LayoutParams tableRowParams =
                                new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                        tableRowParams.setMargins(0, 20, 4, 0);
                        row.setLayoutParams(tableRowParams);
                        name = new TextView(getActivity());
                        name.setSingleLine(false);
                        name.setMaxWidth(90);
                        //TableLayout.LayoutParams textViewParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                        //textViewParams.setMa
                        //name.setLayoutParams(textViewParams);
                        date = new TextView(getActivity());
                        date.setSingleLine(false);
                        image = new ImageView(getActivity());

                        name.setText(movieName);
                        date.setText(movieDate);
                        final String url = "https://image.tmdb.org/t/p/w500" + movieImage;
                        Picasso.get().load(url).into(image);

                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sharedPref = getActivity().getSharedPreferences("Movies", Context.MODE_PRIVATE);
                                SharedPreferences.Editor spEditor = sharedPref.edit();
                                spEditor.putString("movie_id", movieID);
                                spEditor.putString("movie_name", movieName);
                                spEditor.putString("release_date", movieDate);
                                spEditor.putString("movie_image", url);
                                spEditor.apply();
                                ViewFragment viewFragment = new ViewFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_frame, viewFragment);
                                //content_frame
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
                        row.addView(image);
                        row.addView(name);
                        row.addView(date);

                        tableLayout.addView(row);
                    }

                    //https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
                    //https://developers.themoviedb.org/3/movies/get-movie-details
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }
}
