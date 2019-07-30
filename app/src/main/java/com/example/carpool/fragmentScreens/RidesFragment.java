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

import com.example.carpool.Adapters.RidesRecyclerViewAdapter;
import com.example.carpool.Constants;
import com.example.carpool.R;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RidesFragment extends Fragment {

    private static final String TAG = RidesFragment.class.getName();

    //List contains all the rides which are not posted by the logged-in user
    private ArrayList<RideAdsContent> rideList = new ArrayList<>();
    private RidesRecyclerViewAdapter adapter;
    private DatabaseReference postedAdReference;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate and initialize the recycler view
        View view = inflater.inflate(R.layout.fragment_rides, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rides_recycler_view);

        //Get the reference of posted rides from firebase database
        postedAdReference = FirebaseDatabase.getInstance().getReference(Constants.RIDE_POSTED_NODE);
        userId = FirebaseAuth.getInstance().getUid();

        //Initialize the recycler view and set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        adapter = new RidesRecyclerViewAdapter(rideList);
        recyclerView.setAdapter(adapter);

        //Get all the posted rides from firebase
        fetchData(userId, postedAdReference);

        return view;
    }

    private void fetchData(String usedId, DatabaseReference postedAdReference) {

        postedAdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rideList.clear();
                for (DataSnapshot singleAd : dataSnapshot.getChildren()) {
                    RideAdsContent rideAd = singleAd.getValue(RideAdsContent.class);

                    //If ride is not null and not posted by logged-in user then add to the list
                    if (rideAd != null && !rideAd.getUserID().equals(usedId)) {
                        rideList.add(rideAd);
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
