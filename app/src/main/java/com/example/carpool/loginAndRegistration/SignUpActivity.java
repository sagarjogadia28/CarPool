package com.example.carpool.loginAndRegistration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.carpool.FirstScreen;
import com.example.carpool.R;
import com.example.carpool.Utility;
import com.example.carpool.databinding.ActivitySignUpBinding;
import com.example.carpool.modelClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static com.example.carpool.Constants.RC_PHOTO_PICKER;


/**
 * 1. submitDetails() will be called when submit fab is clicked.
 * 2. All the details will be validated first in detailsValid() => (eg. check for empty, minimum length)
 * 3. createAccount() will be called to sign up the user using Firebase authentication
 * 4. On success, addToDatabase() will be called if all the details are valid
 * 5. On success, addImageToStorage will be called to upload user profile image on the Firebase storage
 */
public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private StorageReference userImageReference;
    private DatabaseReference userReference;
    private static final String TAG = SignUpActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set layout of the file using data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.setViewmodel(this);

        // Initialize Firebase Auth, database and storage
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("Users");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        userImageReference = firebaseStorage.getReference("User_profile_images");


        //userPhotoStorageReference = firebaseStorage.getReference().child("user_photo");

        //Set the user profile image to circular
        setCircularImageView(R.drawable.user_placeholder, binding.imgUserProfile);
    }

    //Set the image in the imageView as circular image
    private void setCircularImageView(int image, ImageView imageView) {
        Glide.with(this)
                .load(image)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    private void setCircularImageView(Uri uri, ImageView imageView) {
        Glide.with(this)
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    private void createAccount(String name, String email, String password, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "createUserWithEmail:success");
                        //Add details to the database and go to FirstScreen activity
                        User user = new User(name, email, phone, 0, 0);
                        addToDatabase(user);

                    } else {
                        Log.d(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Registration failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //To add name, email and phone to the database
    private void addToDatabase(User user) {
        userReference
                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "addToDatabase:success");
                        addImageToStorage(binding.imgUserProfile);
                    } else {
                        Log.d(TAG, "addToDatabase:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void addImageToStorage(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        userImageReference
                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .putBytes(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, FirstScreen.class));
                        finish();
                    } else {
                        Log.d(TAG, "addImageToStorage:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Called when user selects the profile image
    public void selectProfileImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent to select image for profile
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            setCircularImageView(selectedImageUri, binding.imgUserProfile);
        }
    }

    //Called when user clicks the submit fab
    public void submitDetails() {
        String name = Objects.requireNonNull(binding.editTextName.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.editTextPassword.getText()).toString().trim();
        String reEnterPassword = Objects.requireNonNull(binding.editTextReenterPassword.getText()).toString().trim();
        String phone = Objects.requireNonNull(binding.editTextPhoneNumber.getText()).toString().trim();

        if (!detailsValid(name, email, password, reEnterPassword, phone))
            return;

        createAccount(name, email, password, phone);

    }

    private boolean detailsValid(String name, String email, String password, String reEnterPassword, String phone) {
        boolean valid = true;

        //Check if name is empty
        if (name.isEmpty()) {
            binding.editTextName.setError("Please enter name");
            valid = false;
        }

        //Check if email is empty
        if (email.isEmpty()) {
            binding.editTextEmail.setError("Please enter email address");
            valid = false;
        } else if (!Utility.isValidEmail(email)) {
            //Check if email is of correct format
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
        } else if (!password.equals(reEnterPassword)) {
            //Check password and reEnterPassword are same
            binding.editTextReenterPassword.setError("Password does not match");
            valid = false;
        }

        //Check the format of the phone number
        if (!Utility.isValidPhone(phone)) {
            binding.editTextPhoneNumber.setError("Phone number should be in valid format");
            valid = false;
        }

        return valid;
    }
}
