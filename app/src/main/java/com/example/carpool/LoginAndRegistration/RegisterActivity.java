package com.example.carpool.LoginAndRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.carpool.FirstScreen;
import com.example.carpool.ModelClasses.User;
import com.example.carpool.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fnameEditText, lnameEditText, emailEditText, phoneEditText;
    private MaterialButton registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fnameEditText = (TextInputEditText)findViewById(R.id.register_fname);
        lnameEditText = (TextInputEditText)findViewById(R.id.register_lname);
        phoneEditText = (TextInputEditText)findViewById(R.id.register_phone);
        emailEditText = (TextInputEditText)findViewById(R.id.register_email);

        registerButton = (MaterialButton)findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userDatabase = FirebaseDatabase.getInstance().getReference("User");

        if (! TextUtils.isEmpty(firebaseUser.getEmail().toString())){

            emailEditText.setText(firebaseUser.getEmail().toString());

        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser(){

        String fname = fnameEditText.getText().toString();
        String lname = lnameEditText.getText().toString();
        String email = firebaseUser.getEmail().toString();
        String phone = phoneEditText.getText().toString();
        String userId = firebaseUser.getUid().toString();

        if ( ! TextUtils.isEmpty(fname)){
            if(! TextUtils.isEmpty(lname)){
                if(! TextUtils.isEmpty(phone)){
                    User user = new User(userId,fname,lname,email,phone);
                    userDatabase.child(userId).setValue(user);
                    startActivity(new Intent(RegisterActivity.this,FirstScreen.class));
                    finish();
                }else {
                    Toast.makeText(this, "Phone no. cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
        }

    }
}
