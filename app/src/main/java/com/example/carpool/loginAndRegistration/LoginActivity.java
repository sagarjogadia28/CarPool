package com.example.carpool.loginAndRegistration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.carpool.R;
import com.example.carpool.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout of the file using data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewmodel(this);
    }

    //Called when submit fab is clicked
    public void submitEmailPassword() {
        String email = binding.editTextEmail.getText().toString();
        String password = binding.editTextPassword.getText().toString();
    }
}

