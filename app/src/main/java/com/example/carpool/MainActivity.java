package com.example.carpool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carpool.LoginAndRegistration.Login;
import com.example.carpool.LoginAndRegistration.LoginFirebase;
import com.example.carpool.OnBoarding.CustomIntro;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        SharedPreferences firstTime = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(firstTime.getInt(Constants.COMPLETED_ONBOARDING_PREF,0)==0){

            // Launch onboarding on first launch
            startActivity(new Intent(this, CustomIntro.class));
        }

        //Initialize Firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){

            //Not signed in start registration activity
           startActivity(new Intent(this, Login.class));

            //Not signed in
            //startActivity(new Intent(this, LoginFirebase.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
