package com.example.carpool;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import com.example.carpool.databinding.DialogReqAdBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class RequestRideDialogFragment extends DialogFragment {

    private static final String TAG = RequestRideDialogFragment.class.getName();
    private DialogReqAdBinding binding;
    private final Calendar calendar = Calendar.getInstance();

    //Open the dialog fragment to fill the details
    public static void display(FragmentManager fragmentManager) {
        RequestRideDialogFragment requestRideDialogFragment = new RequestRideDialogFragment();
        requestRideDialogFragment.show(fragmentManager, TAG);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_req_ad, container, false);
        View view = binding.getRoot();
        binding.setViewmodel(this);

        //Hide the keyboard when clicked on the parent layout
        binding.parent.setOnTouchListener((view1, motionEvent) -> {
            Utility.hideKeyboardInFragment(view1);
            return false;
        });
        return view;
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
        setStyle(RequestRideDialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
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

    public void closeDialog() {
        dismiss();
    }

    //Create the date picker
    public void selectDate() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        new DatePickerDialog(Objects.requireNonNull(getActivity()), date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //Set the format of the date
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.editTextDate.setText(sdf.format(calendar.getTime()));
    }

    //Create the time picker
    public void selectTime() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute1) -> {
                    String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                    hourOfDay = hourOfDay % 13;
                    binding.editTextTime.setText(String.format(Locale.CANADA, "%02d : %02d %s", hourOfDay, minute1, am_pm));
                }, hour, minute, false);
        timePickerDialog.show();
    }

    //Called when request ride button is clicked
    public void requestRide() {

        String currentCity = Objects.requireNonNull(binding.editTextCurrentCity.getText()).toString();
        String currentAddress = Objects.requireNonNull(binding.editTextCurrentAddress.getText()).toString();
        String destinationCity = Objects.requireNonNull(binding.editTextDestCity.getText()).toString();
        String destinationAddress = Objects.requireNonNull(binding.editTextDestAddress.getText()).toString();
        String date = Objects.requireNonNull(binding.editTextDate.getText()).toString();
        String time = Objects.requireNonNull(binding.editTextTime.getText()).toString();
        String totalSeats = Objects.requireNonNull(binding.editTextSeats.getText()).toString();

        if (!detailsValid(currentCity, currentAddress, destinationCity, destinationAddress, date, time, totalSeats))
            return;

        //TODO: Add details to the database
        Toast.makeText(getActivity(), "Details are valid!!", Toast.LENGTH_SHORT).show();
    }

    private boolean detailsValid(String currentCity, String currentAddress, String destinationCity, String destinationAddress, String date, String time, String totalSeats) {
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

        //Check if total seats is empty
        if (totalSeats.isEmpty()) {
            binding.editTextSeats.setError("Please enter total seats available");
            valid = false;
        }
        return valid;
    }
}
