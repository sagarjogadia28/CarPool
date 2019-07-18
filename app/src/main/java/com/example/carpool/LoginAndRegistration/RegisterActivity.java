package com.example.carpool.LoginAndRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.carpool.FirstScreen;
import com.example.carpool.ModelClasses.User;
import com.example.carpool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fnameEditText, lnameEditText, emailEditText, phoneEditText, passowrdEditText, confirmPasswordEditText;
    private MaterialButton registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fnameEditText = findViewById(R.id.register_fname);
        lnameEditText = findViewById(R.id.register_lname);
        phoneEditText = findViewById(R.id.register_phone);
        emailEditText = findViewById(R.id.register_email);
        passowrdEditText = findViewById(R.id.register_password);
        confirmPasswordEditText = findViewById(R.id.register_confirm_password);

        registerButton = findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //userDatabase = FirebaseDatabase.getInstance().getReference("User");

        /*if (! TextUtils.isEmpty(firebaseUser.getEmail())){

            emailEditText.setText(firebaseUser.getEmail());

        }*/

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser(){

        final String fname = fnameEditText.getText().toString();
        final String lname = lnameEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final String phone = phoneEditText.getText().toString();
        String password = passowrdEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        final String userId = email;

        if ( ! TextUtils.isEmpty(fname)){
            if(! TextUtils.isEmpty(lname)){
                if(! TextUtils.isEmpty(phone)){
                    if (! TextUtils.isEmpty(password)){
                        if ((! TextUtils.isEmpty(confirmPassword)) && (password.equals(confirmPassword))){


                            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (! task.isSuccessful()){



                                        Toast.makeText(RegisterActivity.this, "SignUp Failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    else {

                                        User user = new User(userId,fname,lname,email,phone);
                                        //userDatabase.child(userId).setValue(user);

                                        startActivity(new Intent(RegisterActivity.this,FirstScreen.class));
                                        finish();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }

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
