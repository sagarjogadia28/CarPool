package com.example.carpool.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;
import com.example.carpool.modelClasses.RideAdsContent;

import java.util.ArrayList;

public class RidesRecyclerViewAdapter extends RecyclerView.Adapter<RidesRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RidesRecyclerViewAdapte";

    public ArrayList<RideAdsContent> rideAdsList;
    private Context context;

    public RidesRecyclerViewAdapter(ArrayList<RideAdsContent> rideAdsList, Context context) {
        this.rideAdsList = rideAdsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_recycler_view_single_element,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called");

        holder.departureTimeTV.setText(rideAdsList.get(position).getDepartureCity());
        holder.destinationTV.setText(rideAdsList.get(position).getDestinationCity());
        holder.departureTimeTV.setText(rideAdsList.get(position).getDepartureTime().toString());
        holder.departureDateTV.setText(rideAdsList.get(position).getDepartureDate().toString());
        holder.seatsAvailableTV.setText(rideAdsList.get(position).getSeatsAvailable());

        holder.parentCaredView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideAdsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView parentCaredView;
        TextView destinationTV, departureTV, departureTimeTV, departureDateTV, seatsAvailableTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentCaredView = itemView.findViewById(R.id.rides_single_element_parent_view);
            departureTV = itemView.findViewById(R.id.departure_text_view);
            destinationTV = itemView.findViewById(R.id.destination_text_view);
            departureDateTV = itemView.findViewById(R.id.departure_date_tv);
            departureTimeTV = itemView.findViewById(R.id.departure_time_tv);

        }
    }

}
