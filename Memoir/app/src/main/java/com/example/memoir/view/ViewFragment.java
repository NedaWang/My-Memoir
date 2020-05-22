package com.example.memoir.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.fragment.app.Fragment;

import com.example.memoir.MainActivity;
import com.example.memoir.R;

public class ViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        //SharedPreferences sharedPref= getActivity(). getSharedPreferences("Message", Context.MODE_PRIVATE);
        //String message= sharedPref.getString("message",null); tvMessage.setText(message);


        //m.youtube.com/watch?v=KK8FHdFluOQ&app=m
        // set the uri for the video view
        // mu lan
        //rtsp://r5---sn-npoeenl7.googlevideo.com/Cj0LENy73wIaNAnkuGXRHQWvKBMYESARFC3exsZeMOCoAUIASARg4NPUyoDwkpNeigELcjQwVl9JUVNhc1UM/522C29E0CAAA3610E531006CA12F8FCA40FFEC1B.DA9F14411CCFBC338E7D2815ED3E9CBB51C54B05/yt8/1/video.3gp

        VideoView videoView =(VideoView)view.findViewById(R.id.videoView);
        MediaController mediaController= new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        Uri uri=Uri.parse("https://www.youtube.com/watch?v=JWU8gw1zis8");
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

//https://api.themoviedb.org/3/movie/75780?api_key=b43e380b2a3295ab244b24f4887d9d0d&append_to_response=videos
        //b43e380b2a3295ab244b24f4887d9d0d
        //http://api.themoviedb.org/3/movie/75780/videos?api_key=b43e380b2a3295ab244b24f4887d9d0d
//https://www.youtube.com/watch?v=JWU8gw1zis8
        //533ec6c6c3a368544800702c
        //http://api.themoviedb.org/3/movie/550/videos?api_key=b43e380b2a3295ab244b24f4887d9d0d
        //https://api.themoviedb.org/3/search/movie?api_key=b43e380b2a3295ab244b24f4887d9d0d&query=Jack+Reacher
        return view;
    }
}
//tmdb first get movie id
//then http://api.themoviedb.org/3/movie/75780/videos?api_key=b43e380b2a3295ab244b24f4887d9d0d get a key from this link
// or https://api.themoviedb.org/3/movie/297762?api_key=###&append_to_response=videos directly
// then go to youtube  https://www.youtube.com/watch?v=

// article
// https://www.androhub.com/implement-youtube-player-fragment-android-app/
//https://www.oodlestechnologies.com/blogs/How-to-play-youtube-video-on-fragment-in-Android/
// https://www.programcreek.com/java-api-examples/?api=com.google.android.youtube.player.YouTubePlayerSupportFragment