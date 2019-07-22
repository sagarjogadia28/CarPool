package com.example.carpool.loginAndRegistration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpool.R;
import com.example.carpool.modelClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

//        firebaseAuth = FirebaseAuth.getInstance();
//
//        startSignInAuthentication();
//
//        finish();
    }


//    private void startSignInAuthentication(){
//
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build()
//                //new AuthUI.IdpConfig.PhoneBuilder().build(),
//                //new AuthUI.IdpConfig.GoogleBuilder().build()
//        );
//
//        startActivityForResult(
//                AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setIsSmartLockEnabled(true)
//                .setAvailableProviders(providers)
//                .build(),
//                RC_SIGN_IN
//        );
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN){
//
//            checkRegistration();
//
//            Log.d(TAG, "onActivityResult: check");
//
//        }
//    }
//
//    private void checkRegistration(){
//
//        Log.d(TAG, "checkRegistration: check1");
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//        DatabaseReference mUserData;
//        FirebaseDatabase mFirebsaeDatabase;
//
//
//        mFirebsaeDatabase = FirebaseDatabase.getInstance();
//        mUserData = mFirebsaeDatabase.getReference().child("User");
//
//        Query query = FirebaseDatabase.getInstance().getReference("User")
//                .orderByChild("userid")
//                .equalTo(firebaseUser.getUid());
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                userList.clear();
//
//                if (dataSnapshot.exists()){
//
//                    Log.e(TAG, "onDataChange: check2");
//                    startActivity(new Intent(LoginFirebase.this, FirstScreen.class));
//                    finish();
//                }
//                else {
//
//                    Log.e(TAG, "onDataChange: check3");
//                    startActivity(new Intent(LoginFirebase.this,RegisterActivity.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                userList.clear();
//
//                if (dataSnapshot.exists()){
//                    Log.e(TAG, "onDataChange: check4");
//                    startActivity(new Intent(LoginFirebase.this, FirstScreen.class));
//                    finish();
//                }
//                else {
//                    Log.e(TAG, "onDataChange: check5");
//                    startActivity(new Intent(LoginFirebase.this,RegisterActivity.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
}
