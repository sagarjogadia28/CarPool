package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.carpool.FragmetScreens.GetRideFragment;
import com.example.carpool.FragmetScreens.ProfileFragment;
import com.example.carpool.FragmetScreens.WantRideFragment;

public class FirstScreen extends AppCompatActivity {

    public static int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        ViewPager viewPager = findViewById(R.id.first_screen_pager);

        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


    }

    class viewPagerAdapter extends FragmentPagerAdapter{


        public viewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new WantRideFragment().newinstance(position);
                case 1: return new GetRideFragment().newinstance(position);
                case 2: return new ProfileFragment().newinstance(position);
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
