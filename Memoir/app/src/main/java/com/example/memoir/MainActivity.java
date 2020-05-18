package com.example.memoir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.memoir.home.HomeFragment;
import com.example.memoir.map.MapFragment;
import com.example.memoir.memoir.MemoirFragment;
import com.example.memoir.report.ReportFragment;
import com.example.memoir.search.SearchFragment;
import com.example.memoir.view.ViewFragment;
import com.example.memoir.watchlist.WatchlistFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //these two lines of code show the navicon drawer icon top left hand side
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.homePage:
                replaceFragment(new HomeFragment());
                Toast.makeText(MainActivity.this,"home",Toast.LENGTH_SHORT).show();
                break;
            case R.id.searchPage:
                replaceFragment(new SearchFragment());
                Toast.makeText(MainActivity.this,"search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewPage:
                replaceFragment(new ViewFragment());
                Toast.makeText(MainActivity.this,"view",Toast.LENGTH_SHORT).show();
                break;
            case R.id.watchlistPage:
                replaceFragment(new WatchlistFragment());
                break;
            case R.id.memoirPage:
                replaceFragment(new MemoirFragment());
                break;
            case R.id.reportPage:
                replaceFragment(new ReportFragment());
                break;
            case R.id.mapPage:
                replaceFragment(new MapFragment());
                break;
        }
        //this code closes the drawer after you selected an item from the menu, otherwise stay open
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


}
