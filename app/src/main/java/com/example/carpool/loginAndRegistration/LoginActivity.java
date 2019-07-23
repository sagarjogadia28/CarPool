package com.example.carpool.loginAndRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.carpool.FirstScreen;
import com.example.carpool.R;
import com.example.carpool.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout of the file using data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewmodel(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    //Called when submit fab is clicked
    public void submitEmailPassword() {

        if (binding.editTextEmail.getText() != null && binding.editTextPassword.getText() != null) {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            //Check whether email and password are not empty and in proper format
            if (!detailsValid(email, password))
                return;

            //Sign in user using firebase authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {

                        //Successful sign-in
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(this, FirstScreen.class));
                        } else {

                            //Sign-in failed
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private boolean detailsValid(String email, String password) {

        boolean valid = true;

        //Check if email is empty
        if (email.isEmpty()) {
            binding.editTextEmail.setError("Please enter email address");
            valid = false;
        }

        //Check if email is of correct format
        if (!isValidEmail(email)) {
            binding.editTextEmail.setError("Please enter email address in proper format");
            valid = false;
        }

        //Check if password is empty
        if (password.isEmpty()) {
            binding.editTextPassword.setError("Please enter password");
            valid = false;
        } else if (password.length() < 6) {
            //Check length of the password
            binding.editTextPassword.setError("Password should be at least 6 characters");
            valid = false;
        }

        return valid;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void forgotPassword() {
        Toast.makeText(this, "Forgot password clicked!!", Toast.LENGTH_SHORT).show();
    }
}

