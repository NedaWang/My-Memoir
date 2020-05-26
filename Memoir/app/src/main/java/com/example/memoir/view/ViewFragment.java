package com.example.memoir.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memoir.MainActivity;
import com.example.memoir.R;
import com.example.memoir.util.FireBaseDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ViewFragment extends Fragment {
    //FirebaseFirestore db;

    YouTubePlayerView player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        player = view.findViewById(R.id.youtube_fragment);


        return view;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(new Date());

        Map<String, Object> movie = new HashMap<>();
        movie.put("title", "Jack Reacher");
        movie.put("release_date", "2012-12-21");
        movie.put("add_date", date );

        // Add a new document with a generated ID
        db.collection("watchlist")
                .add(movie)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        return view;
    }
     */
}
//tmdb first get movie id
//then http://api.themoviedb.org/3/movie/75780/videos?api_key=b43e380b2a3295ab244b24f4887d9d0d get a key from this link
// or https://api.themoviedb.org/3/movie/297762?api_key=###&append_to_response=videos directly
// then go to youtube  https://www.youtube.com/watch?v=

// article
// https://www.androhub.com/implement-youtube-player-fragment-android-app/
//https://www.oodlestechnologies.com/blogs/How-to-play-youtube-video-on-fragment-in-Android/
// https://www.programcreek.com/java-api-examples/?api=com.google.android.youtube.player.YouTubePlayerSupportFragment



//https://api.themoviedb.org/3/movie/75780?api_key=b43e380b2a3295ab244b24f4887d9d0d&append_to_response=videos
//b43e380b2a3295ab244b24f4887d9d0d
//http://api.themoviedb.org/3/movie/75780/videos?api_key=b43e380b2a3295ab244b24f4887d9d0d
//https://www.youtube.com/watch?v=JWU8gw1zis8
//533ec6c6c3a368544800702c
//http://api.themoviedb.org/3/movie/550/videos?api_key=b43e380b2a3295ab244b24f4887d9d0d
//https://api.themoviedb.org/3/search/movie?api_key=b43e380b2a3295ab244b24f4887d9d0d&query=Jack+Reacher
