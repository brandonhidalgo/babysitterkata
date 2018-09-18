package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.brandon.hidalgo.babysitterkata.fragments.WageCalculatorFragment;

import static com.brandon.hidalgo.babysitterkata.Constants.EARLIEST_START_TIME;
import static com.brandon.hidalgo.babysitterkata.Constants.START_TIME_SELECTOR_FRAGMENT_TAG;

public class StartTimeSelectorFragment extends TimeSelectorFragment {
    public static StartTimeSelectorFragment newInstance() {
        return new StartTimeSelectorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuery = "What time will you start?";
    }


    @Override
    public void onStart() {
        super.onStart();
        mMinTime = EARLIEST_START_TIME;
    }

    @Override
    protected Class<? extends Fragment> getNextStep() {
        return WageCalculatorFragment.class;
    }

    @Override
    protected String getFragmentTag() {
        return START_TIME_SELECTOR_FRAGMENT_TAG;
    }
}
