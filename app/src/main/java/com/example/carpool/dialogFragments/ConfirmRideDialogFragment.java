package com.example.carpool.dialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.carpool.Constants;
import com.example.carpool.R;
import com.example.carpool.Utility;
import com.example.carpool.databinding.DialogConfirmAdBinding;
import com.example.carpool.modelClasses.Passenger;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ConfirmRideDialogFragment extends DialogFragment {

    private static final String TAG = ConfirmRideDialogFragment.class.getName();

    private DialogConfirmAdBinding binding;
    private int totalSeats = 0;
    private String driverID = "";
    private String departureDate = "";
    private String departureTime = "";
    private String uid = "";

    //Firebase database reference
    private DatabaseReference passengerReference;
    private DatabaseReference postedAdReference;
    private DatabaseReference confirmedRidesReference;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm_ad, container, false);
        View view = binding.getRoot();
        binding.setViewmodel(this);

        //Get the parcelable bundle
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            RideAdsContent ride = bundle.getParcelable(Constants.POSTED_RIDE_CONTENTS);
            if (ride != null) {
                departureDate = ride.getDepartureDate();
                departureTime = ride.getDepartureTime();
                driverID = ride.getUserID();
                totalSeats = ride.getSeatsAvailable();
                binding.editTextCurrentCity.setHint(ride.getDepartureCity());
                binding.editTextDestCity.setHint(ride.getDestinationCity());
                binding.editTextDate.setText(departureDate);
                binding.editTextTime.setText(departureTime);
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
        passengerReference = firebaseDatabase.getReference(Constants.PASSENGERS_NODE);

        //Reference to ride posted by the driver
        postedAdReference = firebaseDatabase.getReference(Constants.RIDE_POSTED_NODE);

        //Reference to add ride to logged-in user
        confirmedRidesReference = firebaseDatabase.getReference(Constants.CONFIRMED_RIDES_NODE);

        //Get the uid of logged-in user
        uid = FirebaseAuth.getInstance().getUid();

        return view;
    }

    public void closeDialog() {
        dismiss();
    }

    //Called when clicked on the confirm ride button
    public void confirmRide() {
        String currentCity = Objects.requireNonNull(binding.editTextCurrentCity.getText()).toString();
        String currentAddress = Objects.requireNonNull(binding.editTextCurrentAddress.getText()).toString();
        String destinationCity = Objects.requireNonNull(binding.editTextDestCity.getText()).toString();
        String destinationAddress = Objects.requireNonNull(binding.editTextDestAddress.getText()).toString();
        String totalSeatsNeeded = Objects.requireNonNull(binding.editTextSeats.getText()).toString();

        if (!detailsValid(currentCity, currentAddress, destinationCity, destinationAddress, totalSeatsNeeded))
            return;

        Passenger passenger = new Passenger(
                currentAddress,
                currentCity,
                destinationAddress,
                destinationCity,
                departureDate,
                departureTime,
                Integer.valueOf(totalSeatsNeeded),
                uid,
                driverID
        );
        addPassengersOfRide(passenger);

    }

    //Method to add passenger to a specific ride
    private void addPassengersOfRide(Passenger passenger) {
        passengerReference
                .child(driverID + departureDate + departureTime)
                .push()
                .setValue(passenger)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        changeTotalSeatsAvailable(passenger.getSeatsNeeded());
                    else
                        Log.d(TAG, "onComplete: UnSuccessful");
                });
    }

    //Subtract selected seats from total available seats
    private void changeTotalSeatsAvailable(int seatsNeeded) {
        postedAdReference
                .child(driverID + departureDate + departureTime)
                .child("seatsAvailable")
                .setValue(totalSeats - seatsNeeded)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        mapRideWithPassenger();
                    else
                        Log.d(TAG, "changeTotalSeatsAvailable: UnSuccessful");
                });
    }

    //Add ride to logged-in user
    private void mapRideWithPassenger() {
        confirmedRidesReference
                .child(uid)
                .push()
                .setValue(driverID + departureDate + departureTime)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //TODO: Show notification to the driver
                        dismiss();
                    } else
                        Log.d(TAG, "onComplete: UnSuccessful");
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
            binding.editTextSeats.setError("Please enter value less than total seats");
            valid = false;
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
