package com.example.carpool;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utility {

    //Check the format of the email is valid or not
    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //Check the format of the phone is valid or not
    public static boolean isValidPhone(String number) {
        return (!TextUtils.isEmpty(number) && number.length() >= 10 && Patterns.PHONE.matcher(number).matches());
    }

    //Hide the keyboard in activity
    public static void hideKeyboardInActivity(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
        }
    }

    //Hide the keyboard in fragments
    public static void hideKeyboardInFragment(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
