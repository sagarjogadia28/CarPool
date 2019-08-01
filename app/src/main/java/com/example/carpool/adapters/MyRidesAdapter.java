package com.example.carpool.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Constants;
import com.example.carpool.R;
import com.example.carpool.dialogFragments.EditConfirmedRideDialogFragment;
import com.example.carpool.dialogFragments.PostRideDialogFragment;
import com.example.carpool.modelClasses.Passenger;
import com.example.carpool.modelClasses.RideAdsContent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyRidesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Contains confirmed and posted rides of the user
    private ArrayList<Object> myRidesList;
    private final int POSTED_RIDE = 0;
    private Context context;
    public static final String TAG = MyRidesAdapter.class.getName();

    public MyRidesAdapter(Context context, ArrayList<Object> myRidesList) {
        this.context = context;
        this.myRidesList = myRidesList;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //If value is POSTED_RIDE, then inflate posted ride card else confirmed ride card
        if (viewType == POSTED_RIDE) {
            View postedView = inflater.inflate(R.layout.rides_card, parent, false);
            viewHolder = new MyPostedRides(postedView);
        } else {
            View requestView = inflater.inflate(R.layout.request_rides_card, parent, false);
            viewHolder = new MyConfirmedRides(requestView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == POSTED_RIDE) {
            MyPostedRides myPostedRides = (MyPostedRides) holder;
            configurePostedRides(myPostedRides, position);
        } else {
            MyConfirmedRides myConfirmedRides = (MyConfirmedRides) holder;
            configureConfirmedRides(myConfirmedRides, position);
        }
    }

    //Set all the values in confirmed ride card
    @SuppressLint("SetTextI18n")
    private void configureConfirmedRides(MyConfirmedRides myConfirmedRides, int position) {
        Passenger passenger = (Passenger) myRidesList.get(position);
        if (passenger != null) {
            myConfirmedRides.departureCity.setText(passenger.getDepartureCity());
            myConfirmedRides.departureAddress.setText(passenger.getDepartureAddress());
            myConfirmedRides.destinationCity.setText(passenger.getDestinationCity());
            myConfirmedRides.destinationAddress.setText(passenger.getDestinationAddress());
            myConfirmedRides.dateTime.setText(passenger.getDepartureDate() + ", " + passenger.getDepartureTime());
            myConfirmedRides.totalSeats.setText(String.valueOf(passenger.getSeatsNeeded()));

            myConfirmedRides.parent.setOnClickListener(view -> {
                EditConfirmedRideDialogFragment editConfirmedRideDialogFragment = new EditConfirmedRideDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.CONFIRMED_RIDE_CONTENTS, passenger);
                editConfirmedRideDialogFragment.setArguments(bundle);
                editConfirmedRideDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG);
            });
        }
    }

    //Set all the values in posted ride card
    @SuppressLint("SetTextI18n")
    private void configurePostedRides(MyPostedRides myPostedRides, int position) {
        RideAdsContent rideAdsContent = (RideAdsContent) myRidesList.get(position);
        if (rideAdsContent != null) {
            myPostedRides.departureCity.setText(rideAdsContent.getDepartureCity());
            myPostedRides.destinationCity.setText(rideAdsContent.getDestinationCity());
            myPostedRides.dateTime.setText(rideAdsContent.getDepartureDate() + ", " + rideAdsContent.getDepartureTime());
            myPostedRides.totalSeats.setText(String.valueOf(rideAdsContent.getSeatsAvailable()));

            myPostedRides.parent.setOnClickListener(view -> {
                PostRideDialogFragment postRideDialogFragment = new PostRideDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.POSTED_RIDE_CONTENTS, rideAdsContent);
                postRideDialogFragment.setArguments(bundle);
                postRideDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (myRidesList == null) ? 0 : myRidesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (myRidesList.get(position) instanceof RideAdsContent)
            return POSTED_RIDE;
        else if (myRidesList.get(position) instanceof Passenger)
            return 1;
        return -1;
    }

    //ViewHolder for posted rides by logged-in user
    class MyPostedRides extends RecyclerView.ViewHolder {

        TextView departureCity, destinationCity, totalSeats, dateTime;
        CardView parent;

        MyPostedRides(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.cv_posted_ride);
            departureCity = itemView.findViewById(R.id.tv_current_location);
            destinationCity = itemView.findViewById(R.id.tv_dest_location);
            totalSeats = itemView.findViewById(R.id.tv_no_of_seats);
            dateTime = itemView.findViewById(R.id.tv_date_time);
        }
    }

    //ViewHolder for confirmed rides by logged-in user
    class MyConfirmedRides extends RecyclerView.ViewHolder {

        TextView departureCity, departureAddress, destinationCity, destinationAddress, totalSeats, dateTime;
        CardView parent;

        MyConfirmedRides(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.cv_confirmed_ride);
            departureAddress = itemView.findViewById(R.id.tv_current_address);
            departureCity = itemView.findViewById(R.id.tv_current_location);
            destinationAddress = itemView.findViewById(R.id.tv_dest_address);
            destinationCity = itemView.findViewById(R.id.tv_dest_location);
            totalSeats = itemView.findViewById(R.id.tv_no_of_seats);
            dateTime = itemView.findViewById(R.id.tv_date_time);
        }
    }
}
