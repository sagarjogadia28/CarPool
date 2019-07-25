package com.example.carpool.fragmentScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Adapters.RidesRecyclerViewAdapter;
import com.example.carpool.R;
import com.example.carpool.modelClasses.RideAdsContent;

import java.util.ArrayList;

public class RidesFragment extends Fragment {

    private ArrayList<RideAdsContent> rideList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rides, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rides_recycler_view);

        RidesRecyclerViewAdapter adapter = new RidesRecyclerViewAdapter(rideList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
