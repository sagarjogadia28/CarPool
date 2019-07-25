package com.example.carpool;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.carpool.generated.callback.OnClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

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

        //Speeddial fab
        fab_button = findViewById(R.id.first_screen_fab);


        fab_button.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.first_screen_fab_menu_post_ride: return true;
                    case R.id.first_screen_fab_menu_search: return true;
                    default: return true;
                }
            }

            @Override
            public void onMenuClosed() {

            }
        });

    }

    @Override
    protected void onStop() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
