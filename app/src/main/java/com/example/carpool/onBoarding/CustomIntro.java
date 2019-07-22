package com.example.carpool.onBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import com.example.carpool.Constants;
import com.example.carpool.MainActivity;
import com.example.carpool.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class CustomIntro extends AppIntro {

    SharedPreferences mGetPref;
    SharedPreferences.Editor sharedPrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_custom_intro);

        mGetPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedPrefEdit = mGetPref.edit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTheme(R.style.FullScreenTheme);
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.onboarding1);
        Palette p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.share_ride),getString(R.string.share_ride),R.drawable.onboarding1,p.getVibrantColor(Color.parseColor("#B39DDB"))));

        bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.onboarding2);
        p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.want_ride),getString(R.string.want_ride),R.drawable.onboarding2,p.getVibrantColor(Color.parseColor("#90CAF9"))));

        bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.onboarding3);
        p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.built_chat),getString(R.string.built_chat),R.drawable.onboarding3,p.getVibrantColor(Color.parseColor("#90CAF9"))));

        bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.onboarding5);
        p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.gmaps_nav),getString(R.string.gmaps_nav),R.drawable.onboarding5,p.getVibrantColor(Color.parseColor("#90CAF9"))));

        bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.onboarding4);
        p = Palette.from(bitmap).generate();
        addSlide(AppIntroFragment.newInstance(getString(R.string.save_fuel),getString(R.string.save_fuel),R.drawable.onboarding4,p.getVibrantColor(Color.parseColor("#90CAF9"))));

        setImmersiveMode(true);
        setColorTransitionsEnabled(true);

        showSkipButton(true);
        setProgressButtonEnabled(true);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        finishOnboarding();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        finishOnboarding();
    }

    private void finishOnboarding(){
        sharedPrefEdit.putInt(Constants.COMPLETED_ONBOARDING_PREF,1);
        sharedPrefEdit.apply();
          Intent i = new Intent(this, MainActivity.class);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(i);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
