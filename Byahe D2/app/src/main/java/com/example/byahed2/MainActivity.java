package com.example.byahed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    TextView Overlay;
    ProgressBar Pbar;
    BottomNavigationView bottomnav;

    void Enable()
    {
        Overlay.setVisibility(View.GONE);
        Pbar.setVisibility(View.GONE);
        for(int i = 0 ; i < bottomnav.getMenu().size(); i++)
        {
            bottomnav.getMenu().getItem(i).setEnabled(true);
        }
        bottomnav.setElevation(-2);
    }
    void Disable()
    {
        Overlay.setVisibility(View.VISIBLE);
        Pbar.setVisibility(View.VISIBLE);
        for(int i = 0 ; i < bottomnav.getMenu().size(); i++)
        {
            bottomnav.getMenu().getItem(i).setEnabled(false);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Overlay = findViewById(R.id.M_Overlay);
        Pbar = findViewById(R.id.M_ProgressBar);


        bottomnav = findViewById(R.id.MainBottomNav);

        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.MainFragmentContainer,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;

            switch (item.getItemId())
            {
                case R.id.bottom_home:
                    selected = new HomeFragment();
                    break;
                case R.id.bottom_locations:
                    selected = new LocationFragment();
                    break;
                case R.id.bottom_settings:
                    selected = new SettingsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.MainFragmentContainer,selected).commit();

            return true;
        }
    };
}