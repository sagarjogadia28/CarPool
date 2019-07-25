package com.example.carpool.fragmentScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;

public class MyRidesFragment extends Fragment {

    private RecyclerView ride_recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_rides, container, false);

        ride_recyclerView = v.findViewById(R.id.rides_recycler_view);


        return v;

    }
}
