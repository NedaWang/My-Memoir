package com.example.memoir.memoir;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private RecyclerView.LayoutManager layoutManager;
    private List<Memoir> memoirs;
    private RecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memoir,container,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String personID = sharedPreferences.getString("id", null);
        SearchMemoirTask searchMemoirTask = new SearchMemoirTask();
        searchMemoirTask.execute(personID);

        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }


    private class SearchMemoirTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return NetworkConnection.getMemoirByPersonID(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            memoirs = JsonConvert.stringToMemoirs(s);

            adapter = new RecyclerViewAdapter(memoirs);

            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);

            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    // https://api.themoviedb.org/3/discover/movie?api_key=b43e380b2a3295ab244b24f4887d9d0d&query=Mulan
    //https://api.themoviedb.org/3/search/movie?api_key=b43e380b2a3295ab244b24f4887d9d0d&query=Mulan&primary_release_year=1998
    // primary_release_year
}
