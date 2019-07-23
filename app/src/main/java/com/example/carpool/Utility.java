package com.example.carpool;

import android.text.TextUtils;
import android.util.Patterns;

public class Utility {

    //Check the format of the email is valid or not
    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPhone(String number) {
        return (!TextUtils.isEmpty(number) && number.length() >= 10 && Patterns.PHONE.matcher(number).matches());
    }
}
