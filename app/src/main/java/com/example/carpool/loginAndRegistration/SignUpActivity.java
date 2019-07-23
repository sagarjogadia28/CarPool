package com.example.carpool.loginAndRegistration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.carpool.R;
import com.example.carpool.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout of the file using data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.setViewmodel(this);

        Glide.with(this)
                .load(R.drawable.user_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgUserProfile);

    }

    public void selectProfileImage() {
    }

    public void submitDetails() {
    }
}
