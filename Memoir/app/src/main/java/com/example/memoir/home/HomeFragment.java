package com.example.memoir.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.memoir.R;

public class HomeFragment extends Fragment {

    private TextView textView;

    public HomeFragment() {
// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.tv_showmessage);
        textView.setText("This is HOME");
        return view;

    }
}