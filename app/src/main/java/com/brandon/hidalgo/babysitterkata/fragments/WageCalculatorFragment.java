package com.brandon.hidalgo.babysitterkata.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brandon.hidalgo.babysitterkata.Constants;
import com.brandon.hidalgo.babysitterkata.R;
import com.brandon.hidalgo.babysitterkata.databinding.WageCalculatorFragmentBinding;
import com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments.BabySitterTimeAllocationFragment;

import static com.brandon.hidalgo.babysitterkata.Constants.AFTER_MIDNIGHT_RATE;
import static com.brandon.hidalgo.babysitterkata.Constants.BEDTIME_RATE;
import static com.brandon.hidalgo.babysitterkata.Constants.MIDNIGHT;
import static com.brandon.hidalgo.babysitterkata.Constants.TILL_BEDTIME_RATE;

public class WageCalculatorFragment extends Fragment {
    private FragmentActivity mActivity;

    private TextView resultTextView;

    private int mSelectedStartTime;
    private int mSelectedBedTime;
    private int mSelectedEndTime;

    /**
     * Returns a new instance of the WageCalculatorFragment
     *
     * @return WageCalculatorFragment
     */
    public static WageCalculatorFragment newInstance() {
        return new WageCalculatorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        initializeSelectedTimes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WageCalculatorFragmentBinding binding = WageCalculatorFragmentBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        resultTextView = binding.resultTextView;
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        calculateNightlyCharge();
    }

    /**
     * Sets the user back to the first TimeSelectionFragment.
     *
     * @param view - The view which invoked this method (through OnClickEvent).
     */
    @SuppressWarnings("unused")
    public void restartProcess(@Nullable View view) {
        mActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_vertical, R.anim.slide_out_vertical)
                .replace(R.id.fragment_container, BabySitterTimeAllocationFragment.newInstance())
                .commit();
    }

    /**
     * Calculates the total nightly charge.
     */
    public void calculateNightlyCharge() {
        int nightlyCharge = getStartTimeToBedTimeCharge() + getBedTimeToMidnightCharge() + getMidnightToEndTimeCharge();
        displayNightlyCharge(nightlyCharge);
    }

    /**
     * Returns the selected start time.
     *
     * @return int
     */
    public int getSelectedStartTime() {
        return mSelectedStartTime;
    }

    /**
     * Returns the selected bedtime.
     *
     * @return int
     */
    public int setSelectedBedTime() {
        return mSelectedBedTime;
    }

    /**
     * Returns the selected end time.
     *
     * @return int
     */
    public int setSelectedEndTime() {
        return mSelectedEndTime;
    }

    /**
     * Sets the text of the resultTextView with the provided nightly charge.
     *
     * @param nightlyCharge - The nightly charge to be displayed.
     */
    void displayNightlyCharge(int nightlyCharge) {
        if (resultTextView.getVisibility() == View.GONE) {
            resultTextView.setVisibility(View.VISIBLE);
        }
        resultTextView.setText(String.format("$%s.00", String.valueOf(nightlyCharge)));
    }

    /**
     * Returns the charge from start time to bedtime.
     *
     * @return int
     */
    private int getStartTimeToBedTimeCharge() {
        if (babysitterStartingAtOrAfterBedtime()) {
            return 0;
        }

        if (babysitterEndingAtOrBeforeBedtime()) {
            return (mSelectedEndTime - mSelectedStartTime) * TILL_BEDTIME_RATE;
        }

        /* If the babysitter starts before bedtime*/
        return (mSelectedBedTime - mSelectedStartTime) * TILL_BEDTIME_RATE;
    }

    /**
     * Returns the charge from bedtime to midnight
     *
     * @return int
     */
    private int getBedTimeToMidnightCharge() {
        if (babysitterEndingAtOrBeforeBedtime() || babysitterStartingAtOrAfterMidnight()) {
            return 0;
        }

        if (babysitterEndingBeforeMidnight()) {
            return babysitterStartingAtOrAfterBedtime() ? (mSelectedEndTime - mSelectedStartTime) * BEDTIME_RATE : (mSelectedEndTime - mSelectedBedTime) * BEDTIME_RATE;
        }

        return babysitterStartingAtOrAfterBedtime() ? (MIDNIGHT - mSelectedStartTime) * BEDTIME_RATE : (MIDNIGHT - mSelectedBedTime) * BEDTIME_RATE;
    }

    /**
     * Returns the charge from midnight to end time.
     *
     * @return int
     */
    private int getMidnightToEndTimeCharge() {
        if (babysitterEndingAtOrBeforeMidnight()) {
            return 0;
        }
        return babysitterStartingAtOrAfterMidnight() ? (mSelectedEndTime - mSelectedStartTime) * AFTER_MIDNIGHT_RATE : (mSelectedEndTime - MIDNIGHT) * AFTER_MIDNIGHT_RATE;
    }

    /**
     * Initializes all the selected time variables provided arguments were passed to the Fragment.
     */
    private void initializeSelectedTimes() {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        mSelectedStartTime = args.getInt(Constants.START_TIME_BUNDLE_KEY);
        mSelectedBedTime = args.getInt(Constants.BED_TIME_BUNDLE_KEY);
        mSelectedEndTime = args.getInt(Constants.END_TIME_BUNDLE_KEY);
    }

    /**
     * Returns whether the babysitter finished sitting before or at bedtime.
     *
     * @return boolean
     */
    private boolean babysitterEndingAtOrBeforeBedtime() {
        return mSelectedEndTime <= mSelectedBedTime;
    }

    /**
     * Returns whether the babysitter started sitting at or after bedtime.
     *
     * @return boolean
     */
    private boolean babysitterStartingAtOrAfterBedtime() {
        return mSelectedStartTime >= mSelectedBedTime;
    }

    /**
     * Returns whether the babysitter finished sitting at or before midnight.
     *
     * @return boolean
     */
    private boolean babysitterEndingAtOrBeforeMidnight() {
        return mSelectedEndTime <= MIDNIGHT;
    }

    private boolean babysitterEndingBeforeMidnight() {
        return mSelectedEndTime < MIDNIGHT;
    }

    /**
     * Returns whether the babysitter started sitting at or after midnight.
     *
     * @return boolean
     */
    private boolean babysitterStartingAtOrAfterMidnight() {
        return mSelectedStartTime >= MIDNIGHT;
    }
}
