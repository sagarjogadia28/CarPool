package com.example.carpool.fragmentScreens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Adapters.RidesRecyclerViewAdapter;
import com.example.carpool.R;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RidesFragment extends Fragment {

    private static final String TAG = "RidesFragment";

    private ArrayList<RideAdsContent> rideList;

    // Firebase Database Reference
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rides, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rides_recycler_view);

        rideList = new ArrayList<>();
        rideList.clear();

        databaseReference = FirebaseDatabase.getInstance().getReference("RideAd");

        fetchData();



        RidesRecyclerViewAdapter adapter = new RidesRecyclerViewAdapter(rideList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        fetchData();
    }

    private void fetchData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot rideSnapshot : dataSnapshot.getChildren()){
                    RideAdsContent rideAdsContent = rideSnapshot.getValue(RideAdsContent.class);

                    rideList.add(rideAdsContent);

                    Log.d(TAG, "onDataChange: "+rideList.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
