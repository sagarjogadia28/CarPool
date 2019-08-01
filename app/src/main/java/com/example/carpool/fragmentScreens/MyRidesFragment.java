package com.example.carpool.fragmentScreens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Constants;
import com.example.carpool.R;
import com.example.carpool.adapters.MyRidesAdapter;
import com.example.carpool.dialogFragments.PostRideDialogFragment;
import com.example.carpool.modelClasses.Passenger;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MyRidesFragment extends Fragment {

    public static final String TAG = MyRidesFragment.class.getName();
    private ArrayList<Object> myRidesList = new ArrayList<>();
    private ArrayList<RideAdsContent> postedRidesList = new ArrayList<>();
    private ArrayList<Passenger> confirmedRidesList = new ArrayList<>();
    private MyRidesAdapter adapter;
    private DatabaseReference passengersOfRideReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initialize the views
        View view = inflater.inflate(R.layout.fragment_my_rides, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_post_ride);
        RecyclerView recyclerView = view.findViewById(R.id.rv_my_rides);

        //Get the logged-in user
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        String userId = mFirebaseAuth.getUid();

        //Initialize the recycler view and set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyRidesAdapter(getActivity(), myRidesList);
        recyclerView.setAdapter(adapter);

        //Get the posted and requested rides of user from firebase database
        DatabaseReference postedRidesReference = FirebaseDatabase.getInstance().getReference(Constants.RIDE_POSTED_NODE);
        DatabaseReference confirmedRidesReference = FirebaseDatabase.getInstance().getReference(Constants.CONFIRMED_RIDES_NODE);
        passengersOfRideReference = FirebaseDatabase.getInstance().getReference(Constants.PASSENGERS_NODE);

        //Populate the recycler view with the values
        getPostedRidesByUser(userId, postedRidesReference);
        getConfirmedRidesByUser(userId, confirmedRidesReference);

        fab.setOnClickListener(view1 -> PostRideDialogFragment.display(getFragmentManager()));

        return view;
    }

    //Search for a node in "ConfirmedRides" with value of logged-in user uid = Will give all the rides confirmed by the user
    //For each ride by the user, get all the details by accessing "PassengersOfRide" node
    private void getConfirmedRidesByUser(String userId, DatabaseReference confirmedRidesReference) {
        confirmedRidesReference
                .orderByKey()
                .startAt(userId)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot singleAd : dataSnapshot.getChildren()) {
                            Map<String, String> map = (Map<String, String>) singleAd.getValue();
                            if (map != null) {
                                for (String value : map.values())
                                    getIndividualRideDetails(userId, value);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                    }
                });
    }

    //Get all the details entered by the user
    private void getIndividualRideDetails(String userId, String node) {

        passengersOfRideReference
                .child(node)
                .orderByChild("userID")
                .equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            Passenger passenger = keyNode.getValue(Passenger.class);
                            if (passenger != null) {
                                confirmedRidesList.add(passenger);
                            }
                        }
                        myRidesList.clear();
                        myRidesList.addAll(confirmedRidesList);
                        myRidesList.addAll(postedRidesList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                    }
                });

    }

    private void getPostedRidesByUser(String userId, DatabaseReference postedRidesReference) {
        postedRidesReference.orderByKey().startAt(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postedRidesList.clear();
                for (DataSnapshot singleAd : dataSnapshot.getChildren()) {
                    RideAdsContent rideAd = singleAd.getValue(RideAdsContent.class);
                    Log.d(TAG, "onDataChange: POSTED called");
                    if (rideAd != null) {
                        postedRidesList.add(rideAd);
                        Log.d(TAG, "onDataChange: POSTED: " + rideAd.toString());
                    }
                }
                myRidesList.clear();
                myRidesList.addAll(postedRidesList);
                myRidesList.addAll(confirmedRidesList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Posted ride => " + databaseError.getMessage());
            }
        });
    }
}
