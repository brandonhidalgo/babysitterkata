package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import static com.brandon.hidalgo.babysitterkata.Constants.BED_TIME_SELECTOR_FRAGMENT_TAG;
import static com.brandon.hidalgo.babysitterkata.Constants.EARLIEST_START_TIME;
import static com.brandon.hidalgo.babysitterkata.Constants.MIDNIGHT;

public class BedTimeSelectorFragment extends TimeSelectorFragment {
    public static BedTimeSelectorFragment newInstance() {
        return new BedTimeSelectorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuery = "What time does bedtime start?";
    }

    @Override
    public void onStart() {
        super.onStart();
        mMinTime = EARLIEST_START_TIME;
        mMaxTime = MIDNIGHT - 1;
    }

    @Override
    protected Class<? extends Fragment> getNextStep() {
        return EndTimeSelectorFragment.class;
    }

    @Override
    protected String getFragmentTag() {
        return BED_TIME_SELECTOR_FRAGMENT_TAG;
    }
}
