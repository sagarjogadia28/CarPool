package com.example.carpool.fragmentScreens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Adapters.MyRidesAdapter;
import com.example.carpool.Constants;
import com.example.carpool.PostRideDialogFragment;
import com.example.carpool.R;
import com.example.carpool.RequestRideDialogFragment;
import com.example.carpool.modelClasses.RequestedRideContent;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class MyRidesFragment extends Fragment {

    public static final String TAG = MyRidesFragment.class.getName();
    private ArrayList<Object> myRidesList = new ArrayList<>();
    private MyRidesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initialize the views
        View view = inflater.inflate(R.layout.fragment_my_rides, container, false);
        FabSpeedDial fabButton = view.findViewById(R.id.first_screen_fab);
        RecyclerView recyclerView = view.findViewById(R.id.rv_my_rides);
        View maskView = view.findViewById(R.id.view_mask);

        //Get the logged-in user
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        String userId = mFirebaseAuth.getUid();

        //Initialize the recycler view and set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyRidesAdapter(myRidesList);
        recyclerView.setAdapter(adapter);

        //Get the posted and requested rides of user from firebase database
        DatabaseReference postedRidesReference = FirebaseDatabase.getInstance().getReference(Constants.RIDE_POSTED_NODE);
        DatabaseReference requestedRidesReference = FirebaseDatabase.getInstance().getReference(Constants.RIDE_REQUESTED_NODE);

        //Populate the recycler view with the values
        getPostedRidesByUser(userId, postedRidesReference);
        getRequestedRidesByUser(userId, requestedRidesReference);

        //Set behaviour of fab menu
        setFabOnClickListener(fabButton, maskView);

        return view;
    }

    //Called when fab is clicked
    private void setFabOnClickListener(FabSpeedDial fabButton, View maskView) {
        fabButton.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                //Show black masking screen when fab menu is opened
                maskView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //Hide black masking screen when fab menu item is selected
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
                //Hide black masking screen when fab menu is closed
                maskView.setVisibility(View.GONE);
            }
        });
    }

    private void getRequestedRidesByUser(String userId, DatabaseReference requestedRidesReference) {
        requestedRidesReference.orderByKey().startAt(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleAd : dataSnapshot.getChildren()) {
                    RequestedRideContent requestedRideContent = singleAd.getValue(RequestedRideContent.class);
                    Log.d(TAG, "onDataChange: REQUESTED called");
                    if (requestedRideContent != null) {
                        myRidesList.add(requestedRideContent);
                        Log.d(TAG, "onDataChange: REQUESTED: " + requestedRideContent.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Requested ride => " + databaseError.getMessage());
            }
        });
    }

    private void getPostedRidesByUser(String userId, DatabaseReference postedRidesReference) {
        postedRidesReference.orderByKey().startAt(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleAd : dataSnapshot.getChildren()) {
                    RideAdsContent rideAd = singleAd.getValue(RideAdsContent.class);
                    Log.d(TAG, "onDataChange: POSTED called");
                    if (rideAd != null) {
                        myRidesList.add(rideAd);
                        Log.d(TAG, "onDataChange: POSTED: " + rideAd.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Posted ride => " + databaseError.getMessage());
            }
        });
    }
}
