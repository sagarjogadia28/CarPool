package com.example.carpool.dialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.carpool.Constants;
import com.example.carpool.R;
import com.example.carpool.Utility;
import com.example.carpool.databinding.DialogEditConfirmAdBinding;
import com.example.carpool.modelClasses.Passenger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditConfirmedRideDialogFragment extends DialogFragment {

    private static final String TAG = EditConfirmedRideDialogFragment.class.getName();

    private DialogEditConfirmAdBinding binding;
    private long totalSeats = 0;
    private String driverID = "";
    private String departureDate = "";
    private String departureTime = "";
    private String uid = "";
    private Passenger passenger;

    //Firebase database reference
    private DatabaseReference specificRideReference;
    private DatabaseReference seatsAvailableReference;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_confirm_ad, container, false);
        View view = binding.getRoot();
        binding.setViewmodel(this);


        //Get the parcelable bundle
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            passenger = bundle.getParcelable(Constants.CONFIRMED_RIDE_CONTENTS);
            if (passenger != null) {
                departureDate = passenger.getDepartureDate();
                departureTime = passenger.getDepartureTime();
                totalSeats = passenger.getSeatsNeeded();
                driverID = passenger.getDriverID();

                binding.editTextCurrentCity.setText(passenger.getDepartureCity());
                binding.editTextCurrentAddress.setText(passenger.getDepartureAddress());
                binding.editTextDestCity.setText(passenger.getDestinationCity());
                binding.editTextDestAddress.setText(passenger.getDestinationAddress());
                binding.editTextSeats.setText(String.valueOf(totalSeats));
                binding.editTextTime.setText(departureTime);
                binding.editTextDate.setText(departureDate);
            }
        }

        //Hide the keyboard when clicked on the parent layout
        binding.parent.setOnTouchListener((view1, motionEvent) -> {
            Utility.hideKeyboardInFragment(view1);
            return false;
        });

        //Get database reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //Reference to add passenger to a specific ride
        specificRideReference = firebaseDatabase.getReference(Constants.PASSENGERS_NODE)
                .child(driverID + departureDate + departureTime);

        //Reference to ride posted by the driver
        seatsAvailableReference = firebaseDatabase.getReference(Constants.RIDE_POSTED_NODE)
                .child(driverID + departureDate + departureTime)
                .child("seatsAvailable");

        //Get the uid of logged-in user
        uid = FirebaseAuth.getInstance().getUid();

        //Add previously selected seats and currently available seats to get original total available seats
        updateTotalSeatsAvailable(seatsAvailableReference);

        return view;
    }

    private void updateTotalSeatsAvailable(DatabaseReference seatsAvailableReference) {
        seatsAvailableReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            totalSeats += (long) dataSnapshot.getValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    //Called when close(X) is clicked
    public void closeDialog() {
        dismiss();
    }

    //Called when clicked on the confirm ride button
    public void confirmRide() {
        String currentCity = Objects.requireNonNull(binding.editTextCurrentCity.getText()).toString().trim();
        String currentAddress = Objects.requireNonNull(binding.editTextCurrentAddress.getText()).toString().trim();
        String destinationCity = Objects.requireNonNull(binding.editTextDestCity.getText()).toString().trim();
        String destinationAddress = Objects.requireNonNull(binding.editTextDestAddress.getText()).toString().trim();
        String totalSeatsNeeded = Objects.requireNonNull(binding.editTextSeats.getText()).toString().trim();

        if (!detailsValid(currentCity, currentAddress, destinationCity, destinationAddress, totalSeatsNeeded))
            return;

        //Update the passenger object with newly entered details by the user
        passenger.setDepartureCity(currentCity);
        passenger.setDepartureAddress(currentAddress);
        passenger.setDestinationCity(destinationCity);
        passenger.setDestinationAddress(destinationAddress);
        passenger.setSeatsNeeded(Integer.valueOf(totalSeatsNeeded));

        updatePassengersOfRide(passenger);

    }

    //Method to update passenger
    private void updatePassengersOfRide(Passenger passenger) {

        specificRideReference
                .orderByChild("userID")
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            updatePassengerValues(snapshot.getKey(), passenger);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void updatePassengerValues(String key, Passenger passenger) {
        specificRideReference
                .child(key)
                .setValue(passenger)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        changeTotalSeatsAvailable(passenger.getSeatsNeeded());
                    } else
                        Log.d(TAG, "onComplete: " + task.getException());
                });

    }

    //Subtract selected seats from total available seats
    private void changeTotalSeatsAvailable(int seatsNeeded) {
        seatsAvailableReference
                .setValue(totalSeats - seatsNeeded)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        dismiss();
                    else
                        Log.d(TAG, "changeTotalSeatsAvailable: UnSuccessful");
                });
    }

    //Check if all the details entered by user are valid or not
    private boolean detailsValid(String currentCity, String currentAddress, String destinationCity, String destinationAddress, String totalSeatsNeeded) {
        boolean valid = true;

        //Check if current city is empty
        if (currentCity.isEmpty()) {
            binding.editTextCurrentCity.setError("Please enter current City, State");
            valid = false;
        }

        //Check if current address is empty
        if (currentAddress.isEmpty()) {
            binding.editTextCurrentAddress.setError("Please enter current address");
            valid = false;
        }

        //Check if destination city is empty
        if (destinationCity.isEmpty()) {
            binding.editTextDestCity.setError("Please enter destination City, State");
            valid = false;
        }

        //Check if destination address is empty
        if (destinationAddress.isEmpty()) {
            binding.editTextDestAddress.setError("Please enter destination address");
            valid = false;
        }


        //Check if total seats needed is not empty and valid
        if (totalSeatsNeeded.isEmpty()) {
            binding.editTextSeats.setError("Please enter total seats needed");
            valid = false;
        } else if (Integer.valueOf(totalSeatsNeeded) <= 0) {
            binding.editTextSeats.setError("Please enter valid value");
            valid = false;
        } else if (Integer.valueOf(totalSeatsNeeded) > totalSeats) {
            binding.editTextSeats.setError("Please enter value less than " + totalSeats);
            valid = false;
        }

        //Check entered value is same or not
        if (passenger != null) {
            if (currentCity.equals(passenger.getDepartureCity()) &&
                    currentAddress.equals(passenger.getDepartureAddress()) &&
                    destinationCity.equals(passenger.getDestinationCity()) &&
                    destinationAddress.equals(passenger.getDestinationAddress()) &&
                    totalSeatsNeeded.equals(String.valueOf(passenger.getSeatsNeeded()))) {
                Toast.makeText(getActivity(), "Nothing to update!!", Toast.LENGTH_SHORT).show();
                valid = false;
            }

        }

        return valid;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(PostRideDialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(width, height);
                window.setWindowAnimations(R.style.SlideAnimation);
            }

        }
    }
}
