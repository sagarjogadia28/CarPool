package com.example.carpool.RideActionActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carpool.R;
import com.example.carpool.modelClasses.RideAdsContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostRide extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private static final String TAG = "PostRide";

    private TextInputEditText departureCity, destinationCity, seatsAvailable;
    private Button departureTimeBtn;
    private DatePicker departureDatePicker;
    private FloatingActionButton submitBtn;

    //Creating variables for date and time
    private String departureTime;
    private String departureDate;

    //Firebase user for id
    private FirebaseUser mFirebaseUser;

    //Firebase database references
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);


        //getting current user
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //getting database refrence
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("RideAd");

        //definging calender class for date picker
        Calendar cal = Calendar.getInstance();

        departureCity = findViewById(R.id.departure_city_IT);
        destinationCity = findViewById(R.id.destination_city_IT);
        seatsAvailable = findViewById(R.id.post_ride_seat_avail_IT);
        departureTimeBtn = findViewById(R.id.departure_time_picker_button);
        departureDatePicker = findViewById(R.id.departure_date_picker);
        submitBtn = findViewById(R.id.post_ride_submitbtn);

        departureTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tp = new TimePickerDialog(PostRide.this,(TimePickerDialog.OnTimeSetListener)PostRide.this,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(PostRide.this));
                tp.show();
            }


        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            departureDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                    departureDate = year + "-" + month+ "-" + day;
                }
            });
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(departureCity.getText())){
                    if (!TextUtils.isEmpty(destinationCity.getText())){
                        if (!TextUtils.isEmpty(departureDate)){
                            if (!TextUtils.isEmpty(departureTime)){
                                if (!TextUtils.isEmpty(seatsAvailable.getText())){

                                    Date c = Calendar.getInstance().getTime();
                                    System.out.println("Current time => " + c);

                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                                    String formattedDate = df.format(c);

                                    RideAdsContent rideAdsContent = new RideAdsContent(departureCity.getText().toString(),destinationCity.getText().toString(),departureTime,departureDate,Integer.parseInt(seatsAvailable.getText().toString()),mFirebaseUser.getUid().toString(),formattedDate);

                                    addtoDatabase(rideAdsContent);

                                }else{
                                    Toast.makeText(PostRide.this, "Available seats cannot be empty", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(PostRide.this, "No Time selected", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(PostRide.this, "No date selected", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(PostRide.this, "Destination cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PostRide.this, "Departure City cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        departureTime = hourOfDay+":" + minute;
        departureTimeBtn.setText(departureTime);

    }
    
    // to add ride post to the database
    private void addtoDatabase(RideAdsContent rideAdsContent){
        databaseReference
                .child(rideAdsContent.getUserID()+rideAdsContent.getPostingDate())
                .setValue(rideAdsContent)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "addToDatabase:success");
                    } else {
                        Log.d(TAG, "addToDatabase:failure", task.getException());
                        Toast.makeText(PostRide.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }

                });
    }
}
