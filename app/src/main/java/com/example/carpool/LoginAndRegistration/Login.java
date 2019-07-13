package com.example.carpool.LoginAndRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carpool.FirstScreen;
import com.example.carpool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText inputEmailID, inputPassword;
    private FirebaseAuth firebaseAuth;
    private Button loginButton, registerButton, forgetPasswordButoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        inputEmailID = (EditText)findViewById(R.id.login_email_input);
        inputPassword = (EditText)findViewById(R.id.login_password_input);
        loginButton = (MaterialButton)findViewById(R.id.login_button);
        registerButton = (MaterialButton)findViewById(R.id.register_button);
        forgetPasswordButoon = (MaterialButton)findViewById(R.id.forgetpassword_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmailID.getText().toString();
                final String password = inputPassword.getText().toString();

                try{
                    if (password.length() > 0 && email.length() > 0){
                        firebaseAuth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()){
                                            Toast.makeText(Login.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                                            Log.v("login activity error",task.getResult().toString());
                                        }
                                        else {
                                            startActivity(new Intent(Login.this, FirstScreen.class));
                                            finish();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(Login.this, R.string.credentials_missing, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterActivity.class));
                finish();
            }
        });

    }
}
