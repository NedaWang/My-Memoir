package com.example.memoir.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memoir.R;
import com.example.memoir.entity.Cinema;
import com.example.memoir.network.NetworkConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SearchCinemaTask searchCinemaTask = new SearchCinemaTask();
        searchCinemaTask.execute();

        return view;
    }

    //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
    /*Zoom levels
    1: World
    5: Landmass/continent
    10: City
    15: Streets
    20: Buildings
    */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //https://developers.google.com/maps/documentation/geocoding/start
        map = googleMap;
        LatLng monash = new LatLng(-37.8771, 145.04493);
        //map.addMarker(new MarkerOptions().position(home).title("my home"));
        //alpha : transparent
        //marker.showInfoWindow()
        map.addMarker(new MarkerOptions().position(monash).title("Monash Caulfield"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(monash));
        float zoomLevel = (float) 10.0;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(monash, zoomLevel));

    }

    private class SearchCinemaTask extends AsyncTask<Void, Void, List<Cinema>> {
        @Override
        protected List<Cinema> doInBackground(Void... voids) {
            return NetworkConnection.getCinemas();
        }

        @Override
        protected void onPostExecute(List<Cinema> cinemas) {
            for (Cinema c : cinemas){
                MarkerOptions cinemaMarker = new MarkerOptions().position(getLocationFromAddress(getActivity(),c.getLocation())).title(c.getName())
                        .visible(true).alpha(0.8f).icon(BitmapDescriptorFactory.defaultMarker(36));
                map.addMarker(cinemaMarker);
            }
        }
    }

    // convert address to latlng
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
}
