package com.example.memoir.memoir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memoir.R;

public class MemoirFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memoir,container,false);

        TextView tv = view.findViewById(R.id.tv_showmessage);
        tv.setText(getActivity().getIntent().getStringExtra("info1"));

        return view;
    }
}
