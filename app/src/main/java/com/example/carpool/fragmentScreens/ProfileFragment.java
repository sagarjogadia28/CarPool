package com.example.carpool.fragmentScreens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.carpool.R;
import com.example.carpool.Utility;
import com.example.carpool.databinding.FragmentProfileBinding;
import com.example.carpool.modelClasses.User;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static com.example.carpool.Constants.RC_CHANGE_PHOTO;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getName();
    private ImageView profileImage;
    private ImageView closeImage;
    private FloatingActionButton editFab;
    private ExtendedFloatingActionButton submitFab;
    private StorageReference userImageReference;
    private FirebaseUser mFirebaseUser;
    private FragmentProfileBinding binding;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Set layout using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();
        binding.setViewmodel(this);

        //Reference to firebase storage
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        userImageReference = firebaseStorage.getReference("User_profile_images");

        //Assigning views using data binding
        closeImage = binding.ivClose;
        profileImage = binding.ivUserProfile;
        editFab = binding.fabEdit;
        submitFab = binding.fabSubmitChanges;

        //Disable click of user profile image
        profileImage.setEnabled(false);

        //Set placeholder of user profile image
        Glide.with(this)
                .load(R.drawable.user_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImage);

        //Database Reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mFirebaseUser != null;

        //Get user id of the logged-in user
        String userId = mFirebaseUser.getUid();
        StorageReference userImageReference = firebaseStorage.getReference("User_profile_images").child(userId);

        //Get the user profile from the firebase storage and set in profile image of the user
        getProfileImageFromStorage(userImageReference);

        //Called if there is any changes in the database
        mDatabaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateUser(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: Fragment");
        // Result returned from launching the Intent to select image for profile
        if (requestCode == RC_CHANGE_PHOTO && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri selectedImageUri = data.getData();
            Glide.with(this)
                    .load(selectedImageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
        }
    }

    //Called when user selects the profile image
    public void selectProfileImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        this.startActivityForResult(intent, RC_CHANGE_PHOTO);
        //this.startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_CHANGE_PHOTO);
    }

    //Called when close(X) is clicked
    public void closeDialog() {
        setViewNonEditable();
    }

    //Called when user clicks the edit button
    public void editDetails() {
        setViewEditable();
    }

    //Called when user submits the details
    public void submitDetails() {
        String phoneNumber = Objects.requireNonNull(binding.editTextPhoneNumber.getText()).toString();

        //Check the format of the phone number
        if (!Utility.isValidPhone(phoneNumber)) {
            binding.editTextPhoneNumber.setError("Phone number should be in valid format");
            return;
        }

        mDatabaseReference.child(mFirebaseUser.getUid()).child("phone").setValue(phoneNumber);
        setViewNonEditable();
        addImageToStorage(profileImage);
    }

    //Download image from firebase storage and set image using Glide
    private void getProfileImageFromStorage(StorageReference userImageReference) {
        userImageReference.getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage));
    }

    //Update the user values
    private void updateUser(@NonNull DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        if (user != null) {
            binding.tvName.setText(user.getName());
            binding.editTextEmail.setText(user.getEmail());
            binding.editTextPhoneNumber.setText(user.getPhone());
            binding.editTextTotalRides.setText(String.valueOf(user.getTotalRides()));
            binding.editTextRating.setText(String.valueOf(user.getRating()));
        }
    }

    //Add profile image in the firebase storage
    private void addImageToStorage(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        userImageReference
                .child(mFirebaseUser.getUid())
                .putBytes(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "addImageToStorage: success");
                    } else {
                        Log.d(TAG, "addImageToStorage:failure", task.getException());

                    }
                });
    }

    //Bring into view submit fab and cross and hide the edit fab
    //Set phone number and image profile to be editable
    private void setViewEditable() {
        startEditableAnimation();
        disableClick(editFab);
        hideView(editFab);
        showView(submitFab, closeImage);
        enableClick(submitFab, closeImage, profileImage, binding.editTextPhoneNumber);
        setDisabledText(binding.editTextEmail, binding.editTextTotalRides, binding.editTextRating);
    }

    //Bring into view edit fab and hide submit fab and cross
    //Set phone number and image profile to be non-editable
    private void setViewNonEditable() {
        startNonEditableAnimation();
        hideView(submitFab, closeImage);
        showView(editFab);
        disableClick(submitFab, profileImage, closeImage, binding.editTextPhoneNumber);
        enableClick(editFab);
        setEnabledText(binding.editTextEmail, binding.editTextTotalRides, binding.editTextRating);
    }

    private void startEditableAnimation() {
        editFab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down));
        submitFab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
        closeImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_left));
    }

    private void startNonEditableAnimation() {
        submitFab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down));
        closeImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_left));
        editFab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
    }

    private void disableClick(View... views) {
        for (View view : views) {
            view.setEnabled(false);
            view.setClickable(false);
        }
    }

    private void enableClick(View... views) {
        for (View view : views) {
            view.setEnabled(true);
            view.setClickable(true);
        }
    }

    private void hideView(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void showView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setDisabledText(EditText... views) {
        for (EditText view : views) {
            view.setTextColor(getResources().getColor(R.color.bottom_bar_color));
        }
    }

    private void setEnabledText(EditText... views) {
        for (EditText view : views) {
            view.setTextColor(getResources().getColor(R.color.outline_box_color));
        }
    }
}
