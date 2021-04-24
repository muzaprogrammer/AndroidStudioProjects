package com.example.clinicamisalud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.clinicamisalud.Adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentOne());
       // viewPagerAdapter.addFragment(new FragmentTwo());
        viewPagerAdapter.addFragment(new FragmentThree());

        viewPager.setAdapter(viewPagerAdapter);

    }

}
