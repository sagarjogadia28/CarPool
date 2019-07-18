package com.example.carpool.LoginAndRegistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.carpool.FirstScreen;
import com.example.carpool.ModelClasses.User;
import com.example.carpool.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class LoginFirebase extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private static final int RC_SIGN_IN = 123;
    private List<User> userList;
    private String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_firebase);

        firebaseAuth = FirebaseAuth.getInstance();

        startSignInAuthentication();

        finish();
    }


    private void startSignInAuthentication(){

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                //new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(true)
                .setAvailableProviders(providers)
                .build(),
                RC_SIGN_IN
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){

            checkRegistration();

            Log.d(TAG, "onActivityResult: check");

        }
    }

    private void checkRegistration(){

        Log.d(TAG, "checkRegistration: check1");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        DatabaseReference mUserData;
        FirebaseDatabase mFirebsaeDatabase;


        mFirebsaeDatabase = FirebaseDatabase.getInstance();
        mUserData = mFirebsaeDatabase.getReference().child("User");

        Query query = FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userid")
                .equalTo(firebaseUser.getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                if (dataSnapshot.exists()){

                    Log.e(TAG, "onDataChange: check2");
                    startActivity(new Intent(LoginFirebase.this, FirstScreen.class));
                    finish();
                }
                else {

                    Log.e(TAG, "onDataChange: check3");
                    startActivity(new Intent(LoginFirebase.this,RegisterActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();

                if (dataSnapshot.exists()){
                    Log.e(TAG, "onDataChange: check4");
                    startActivity(new Intent(LoginFirebase.this, FirstScreen.class));
                    finish();
                }
                else {
                    Log.e(TAG, "onDataChange: check5");
                    startActivity(new Intent(LoginFirebase.this,RegisterActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
