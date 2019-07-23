package com.example.carpool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set default tab to MyRidesFragment
        bottomNavigationView.setSelectedItemId(R.id.navigation_my_rides);

        //Setup navController with NavigationView
        NavController navController = Navigation.findNavController(this, R.id.myNavHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }
}
