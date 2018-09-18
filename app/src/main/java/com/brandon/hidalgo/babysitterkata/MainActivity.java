package com.brandon.hidalgo.babysitterkata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments.BabySitterTimeAllocationFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initializeFragment();
        }
    }

    private void initializeFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, BabySitterTimeAllocationFragment.newInstance(), BabySitterTimeAllocationFragment.TAG)
                .commit();
    }
}
