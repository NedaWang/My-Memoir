package com.example.memoir.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memoir.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        FindCinemas findCinemas = new FindCinemas();
        findCinemas.execute();

       //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        //String message = sharedPreferences.getString("firstname", null);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //https://developers.google.com/maps/documentation/geocoding/start
        map = googleMap;
        LatLng monash = new LatLng(-37.8771, 145.04493);
        //map.addMarker(new MarkerOptions().position(home).title("my home"));
        //alpha : transparent
        //marker.showInfoWindow()

        map.addMarker(new MarkerOptions().position(monash).title("Monash Caulfield"));
        map.moveCamera(CameraUpdateFactory.newLatLng(monash));
        float zoomLevel = (float) 13.0;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(monash, zoomLevel));
        MarkerOptions hoytsChadstonOption = new MarkerOptions().position(new LatLng(-37.884153, 145.084093)).title("Hoyts Chadstone")
                .visible(true).alpha(0.8f).icon(BitmapDescriptorFactory.defaultMarker(36));
        Marker hoytsChadston = googleMap.addMarker(hoytsChadstonOption);

    }

    public class FindCinemas extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String url = "http://10.0.2.2:8080/MemoirDB/webresources/memoir.cinema";
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
            Toast.makeText(getActivity(),""+getLocationFromAddress(getActivity(),"Caulfield").latitude + getLocationFromAddress(getActivity(),
                    "Caulfiled").longitude,Toast.LENGTH_SHORT).show();
            //Log.i("latitude",""+getLocationFromAddress(getActivity(),"Caulfield").latitude);
            //Log.i("longitude",""+getLocationFromAddress(getActivity(),"Caulfield").longitude);
        }
    }
}
