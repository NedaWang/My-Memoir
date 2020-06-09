package com.example.memoir.memoir;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoir.R;
import com.example.memoir.entity.Memoir;
import com.example.memoir.network.NetworkConnection;
import com.example.memoir.util.JsonConvert;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MemoirFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Memoir> units;
    private RecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memoir,container,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String personID = sharedPreferences.getString("id", null);
        SearchMemoirTask searchMemoirTask = new SearchMemoirTask();
        searchMemoirTask.execute(personID);

        return view;
    }

    private class SearchMemoirTask extends AsyncTask<String, Void, List<Memoir>>{

        @Override
        protected List<Memoir> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            final String methodPath = "http://10.0.2.2:8080/MemoirDB/webresources/memoir.memoir/findByPersonID/" + strings[0];
            Request.Builder builder = new Request.Builder();
            builder.url(methodPath);
            Request request = builder.build();
            String results = "result";
            try {
                Response response = client.newCall(request).execute();
                results = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return JsonConvert.stringToMemoirs(results);
        }

        @Override
        protected void onPostExecute(List<Memoir> memoirs) {
            for (Memoir m : memoirs){
                Log.i("memoirname", m.getName());
            }
        }
    }
}
