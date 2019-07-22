package com.example.carpool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
//
//    private FirebaseAuth firebaseAuth;
//    private FirebaseUser firebaseUser;
//    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences firstTime = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        if(firstTime.getInt(Constants.COMPLETED_ONBOARDING_PREF,0)==0){
//
//            // Launch onboarding on first launch
//            startActivity(new Intent(this, CustomIntro.class));
//        }
//
//        //Initialize Firebase variables
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        if(firebaseUser == null){
//
//            //Not signed in start registration activity
//           startActivity(new Intent(this, LoginActivity.class));
//
//            //Not signed in
//            //startActivity(new Intent(this, LoginFirebase.class));
//            finish();
//        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        finish();
//    }
}
