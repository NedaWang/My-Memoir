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

    private class SearchMemoirTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return NetworkConnection.getMemoirByPersonID(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            List<Memoir> memoirs = JsonConvert.stringToMemoirs(s);
            for (Memoir m : memoirs){
                Log.i("memoirname", m.getName());
            }
        }
    }
}
