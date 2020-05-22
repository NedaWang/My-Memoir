package com.example.memoir.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.memoir.R;
import com.example.memoir.entity.Memoir;
import com.example.memoir.util.JsonConvert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private TextView welcome;
    private TextView currentDate;
    TableLayout tableLayout;

    public HomeFragment() {
// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        welcome = view.findViewById(R.id.welcome);
        currentDate = view.findViewById(R.id.current_date);
        currentDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String message = sharedPreferences.getString("firstname", null);
        welcome.setText(message);



        new GetFiveMemoir().execute("1");

        // initialize movie table
        tableLayout = view.findViewById(R.id.movie_table);
        TableRow row0 = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());
        tv0.setText("Movie");
        TextView tv1 = new TextView(getActivity());
        tv1.setText("Release Date");
        TextView tv2 = new TextView(getActivity());
        tv2.setText("Score");
        row0.addView(tv0);
        row0.addView(tv1);
        row0.addView(tv2);
        tableLayout.addView(row0);


        return view;


    }

    public class GetFiveMemoir extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://10.0.2.2:8080/MemoirDB/webresources/memoir.memoir/findTopFiveByIdInCurrentYear/" + strings[0];
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();
            String results = "result";
            try {
                Response response = client.newCall(request).execute();
                results = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            // add data to table
            TableRow row;
            TextView name;
            TextView date;
            TextView score;
            List<Memoir> memoirs = JsonConvert.stringToMemoirs(s);
            for(Memoir m : memoirs)
            {
                row = new TableRow(getActivity());
                name = new TextView(getActivity());
                date = new TextView(getActivity());
                score = new TextView(getActivity());
                name.setText(m.getName());
                date.setText(m.getReleaseDate());
                score.setText(m.getScore());
                row.addView(name);
                row.addView(date);
                row.addView(score);
                tableLayout.addView(row);
            }
        }
    }

}