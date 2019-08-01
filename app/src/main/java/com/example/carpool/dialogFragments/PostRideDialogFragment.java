package com.example.carpool.dialogFragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import androidx.fragment.app.FragmentManager;

import com.example.carpool.Constants;
import com.example.carpool.R;
import com.example.carpool.Utility;
import com.example.carpool.databinding.DialogPostAdBinding;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PostRideDialogFragment extends DialogFragment {

    private static final String TAG = PostRideDialogFragment.class.getName();
    private DialogPostAdBinding binding;
    private final Calendar calendar = Calendar.getInstance();
    private FirebaseUser mFirebaseUser;

    //Format of date and time
    private static final String dateFormat = "dd-MM-yyyy";
    private static final String timeFormat = "hh:mm a";

    //Firebase database reference
    private DatabaseReference databaseReference;

    //Open the dialog fragment to fill the details
    public static void display(FragmentManager fragmentManager) {
        PostRideDialogFragment postRideDialogFragment = new PostRideDialogFragment();
        postRideDialogFragment.show(fragmentManager, TAG);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_post_ad, container, false);
        View view = binding.getRoot();
        binding.setViewmodel(this);

        //Hide the keyboard when clicked on the parent layout
        binding.parent.setOnTouchListener((view1, motionEvent) -> {
            Utility.hideKeyboardInFragment(view1);
            return false;
        });

        //Get current logged-in user
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Get database reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.RIDE_POSTED_NODE);
        return view;
    }

    //Called when post ride button is clicked
    public void postRide() {

        //Get all the details entered by the user
        String currentCity = Objects.requireNonNull(binding.editTextCurrentCity.getText()).toString();
        String destinationCity = Objects.requireNonNull(binding.editTextDestCity.getText()).toString();
        String date = Objects.requireNonNull(binding.editTextDate.getText()).toString();
        String time = Objects.requireNonNull(binding.editTextTime.getText()).toString();
        String totalSeats = Objects.requireNonNull(binding.editTextSeats.getText()).toString();

        //If all the details are not valid, then return
        if (!detailsValid(currentCity, destinationCity, date, time, totalSeats))
            return;

        //Get current date and add to firebase database
        String postingDate = new SimpleDateFormat(dateFormat, Locale.getDefault()).format(new Date());
        RideAdsContent rideAdsContent = new RideAdsContent(
                currentCity,
                destinationCity,
                time,
                date,
                Integer.valueOf(totalSeats),
                mFirebaseUser.getUid(),
                postingDate
        );

        addToDatabase(rideAdsContent);
    }

    private void addToDatabase(RideAdsContent rideAdsContent) {
        databaseReference
                .child(rideAdsContent.getUserID() + rideAdsContent.getDepartureDate() + rideAdsContent.getDepartureTime())
                .setValue(rideAdsContent)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "addToDatabase:success");
                        dismiss();
                    } else {
                        Log.d(TAG, "addToDatabase:failure", task.getException());
                        Toast.makeText(getActivity(), "Add cannot be posted.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean detailsValid(String currentCity, String destinationCity, String date, String time, String totalSeats) {
        boolean valid = true;

        //Check if current city is empty
        if (currentCity.isEmpty()) {
            binding.editTextCurrentCity.setError("Please enter current City, State");
            valid = false;
        }

        //Check if destination city is empty
        if (destinationCity.isEmpty()) {
            binding.editTextDestCity.setError("Please enter destination City, State");
            valid = false;
        }

        //Check if date is empty
        if (date.isEmpty()) {
            binding.editTextDate.setError("Please enter date");
            valid = false;
        }

        //Check if time is empty
        if (time.isEmpty()) {
            binding.editTextTime.setError("Please enter time");
            valid = false;
        }

        //Check if total seats is not empty and greater than zero
        if (totalSeats.isEmpty()) {
            binding.editTextSeats.setError("Please enter total seats available");
            valid = false;
        } else if (Integer.valueOf(totalSeats) <= 0) {
            binding.editTextSeats.setError("Please enter valid total seats available");
            valid = false;
        }
        return valid;
    }

    //Close the dialog fragment
    public void closeDialog() {
        dismiss();
    }

    //Create the time picker
    public void selectTime() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute1) -> {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat, Locale.CANADA);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute1);
                    String formattedTime = simpleDateFormat.format(calendar.getTime());
                    String time = formattedTime.replace("a.m.", "AM").replace("p.m.", "PM");
                    binding.editTextTime.setText(time);
                }, hour, minute, false);
        timePickerDialog.show();
    }

    //Create the date picker
    public void selectDate() {
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        new DatePickerDialog(Objects.requireNonNull(getActivity()),
                dateSetListener,
                currentYear,
                currentMonth,
                currentDay
        ).show();
    }

    //Set the format of the date
    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        binding.editTextDate.setText(sdf.format(calendar.getTime()));
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
