package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import static com.brandon.hidalgo.babysitterkata.Constants.ATLANTIC_MERIDIAN;
import static com.brandon.hidalgo.babysitterkata.Constants.EARLIEST_START_TIME;
import static com.brandon.hidalgo.babysitterkata.Constants.END_TIME_SELECTOR_FRAGMENT_TAG;
import static com.brandon.hidalgo.babysitterkata.Constants.LATEST_END_TIME;
import static com.brandon.hidalgo.babysitterkata.Constants.MIDNIGHT;
import static com.brandon.hidalgo.babysitterkata.Constants.NOON;
import static com.brandon.hidalgo.babysitterkata.Constants.PACIFIC_MERIDIAN;

public class EndTimeSelectorFragment extends TimeSelectorFragment {
    public static EndTimeSelectorFragment newInstance() {
        return new EndTimeSelectorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuery = "What time will babysitting end?";
    }

    @Override
    public void onStart() {
        super.onStart();
        mMinTime = EARLIEST_START_TIME + 1;
        mMaxTime = LATEST_END_TIME;
    }

    @Override
    protected Class<? extends Fragment> getNextStep() {
        return StartTimeSelectorFragment.class;
    }

    @Override
    String convertTimeToMeridian(int time) {
        String meridian = PACIFIC_MERIDIAN;
        if (time == MIDNIGHT) {
            time = NOON;
            meridian = ATLANTIC_MERIDIAN;
        }
        if (time > MIDNIGHT) {
            time -= MIDNIGHT;
            meridian = ATLANTIC_MERIDIAN;
        } else if (time > NOON) {
            time -= NOON;
        }
        return time + meridian;
    }

    @Override
    protected String getFragmentTag() {
        return END_TIME_SELECTOR_FRAGMENT_TAG;
    }
}
