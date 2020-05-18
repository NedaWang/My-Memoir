package com.example.memoir.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.memoir.R;

public class SearchFragment extends Fragment {

    private EditText etMessage;
    private Button button;

    public SearchFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        TextView tv = view.findViewById(R.id.tv_showmessage);
        return view; }

}
