package com.example.carpool.fragmentScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carpool.PostRideDialogFragment;
import com.example.carpool.R;
import com.example.carpool.RequestRideDialogFragment;
import com.google.android.material.internal.NavigationMenu;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class MyRidesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_rides, container, false);

        //Speed dial fab
        //Speed Dial fab
        FabSpeedDial fabButton = view.findViewById(R.id.first_screen_fab);
        View maskView = view.findViewById(R.id.view_mask);


        fabButton.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                maskView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                maskView.setVisibility(View.GONE);
                switch (menuItem.getItemId()) {
                    case R.id.first_screen_fab_menu_post_ride:
                        PostRideDialogFragment.display(getFragmentManager());
                        return true;
                    case R.id.first_screen_fab_menu_request_ride:
                        RequestRideDialogFragment.display(getFragmentManager());
                        return true;
                    default:
                        return true;
                }
            }

            @Override
            public void onMenuClosed() {
                maskView.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
