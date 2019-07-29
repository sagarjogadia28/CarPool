package com.example.carpool.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;
import com.example.carpool.modelClasses.RequestedRideContent;
import com.example.carpool.modelClasses.RideAdsContent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyRidesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Contains requested and posted rides of the user
    private ArrayList<Object> myRidesList;
    private final int POSTED_RIDE = 0, REQUESTED_RIDE = 1;

    public MyRidesAdapter(ArrayList<Object> myRidesList) {
        this.myRidesList = myRidesList;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //If value is POSTED_RIDE, then inflate posted ride card else requested ride card
        switch (viewType) {
            case POSTED_RIDE:
                View postedView = inflater.inflate(R.layout.rides_card, parent, false);
                viewHolder = new MyPostedRides(postedView);
                break;
            case REQUESTED_RIDE:
                View requestView = inflater.inflate(R.layout.request_rides_card, parent, false);
                viewHolder = new MyRequestedRides(requestView);
                break;
            default:
                return null;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case POSTED_RIDE:
                MyPostedRides myPostedRides = (MyPostedRides) holder;
                configurePostedRides(myPostedRides, position);
                break;
            case REQUESTED_RIDE:
                MyRequestedRides myRequestedRides = (MyRequestedRides) holder;
                configureRequestedRides(myRequestedRides, position);
                break;
        }
    }

    //Set all the values in requested ride card
    @SuppressLint("SetTextI18n")
    private void configureRequestedRides(MyRequestedRides myRequestedRides, int position) {
        RequestedRideContent requestedRide = (RequestedRideContent) myRidesList.get(position);
        if (requestedRide != null) {
            myRequestedRides.departureCity.setText(requestedRide.getDepartureCity());
            myRequestedRides.departureAddress.setText(requestedRide.getDepartureAddress());
            myRequestedRides.destinationCity.setText(requestedRide.getDestinationCity());
            myRequestedRides.destinationAddress.setText(requestedRide.getDestinationAddress());
            myRequestedRides.dateTime.setText(requestedRide.getDepartureDate() + ", " + requestedRide.getDepartureTime());
            myRequestedRides.totalSeats.setText(String.valueOf(requestedRide.getSeatsNeeded()));
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
        else if (myRidesList.get(position) instanceof RequestedRideContent)
            return REQUESTED_RIDE;
        return -1;
    }

    //ViewHolder for posted rides by logged-in user
    class MyPostedRides extends RecyclerView.ViewHolder {

        TextView departureCity, destinationCity, totalSeats, dateTime;

        MyPostedRides(@NonNull View itemView) {
            super(itemView);

            departureCity = itemView.findViewById(R.id.tv_current_location);
            destinationCity = itemView.findViewById(R.id.tv_dest_location);
            totalSeats = itemView.findViewById(R.id.tv_no_of_seats);
            dateTime = itemView.findViewById(R.id.tv_date_time);
        }
    }

    //ViewHolder for requested rides by logged-in user
    class MyRequestedRides extends RecyclerView.ViewHolder {

        TextView departureCity, departureAddress, destinationCity, destinationAddress, totalSeats, dateTime;

        MyRequestedRides(@NonNull View itemView) {
            super(itemView);
            departureAddress = itemView.findViewById(R.id.tv_current_address);
            departureCity = itemView.findViewById(R.id.tv_current_location);
            destinationAddress = itemView.findViewById(R.id.tv_dest_address);
            destinationCity = itemView.findViewById(R.id.tv_dest_location);
            totalSeats = itemView.findViewById(R.id.tv_no_of_seats);
            dateTime = itemView.findViewById(R.id.tv_date_time);
        }
    }
}
