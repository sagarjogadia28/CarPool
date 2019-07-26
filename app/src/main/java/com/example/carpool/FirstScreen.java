package com.example.carpool;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class FirstScreen extends AppCompatActivity {

    //Speed Dial fab
    FabSpeedDial fab_button;

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

        //Speed dial fab
        fab_button = findViewById(R.id.first_screen_fab);


        fab_button.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first_screen_fab_menu_post_ride:
                        PostRideDialogFragment.display(getSupportFragmentManager());
                        return true;
                    case R.id.first_screen_fab_menu_search:
                        RequestRideDialogFragment.display(getSupportFragmentManager());
                        return true;
                    default:
                        return true;
                }
            }

            @Override
            public void onMenuClosed() {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
