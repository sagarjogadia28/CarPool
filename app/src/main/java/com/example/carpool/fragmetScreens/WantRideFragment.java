package com.example.carpool.fragmetScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carpool.R;

public class WantRideFragment extends Fragment {

    public static WantRideFragment newinstance(int pos){

        Bundle args = new Bundle();
        args.putInt("position",pos);
        WantRideFragment fragment = new WantRideFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.want_ride_fragment_layout,container,false);

        return v;
    }
}
