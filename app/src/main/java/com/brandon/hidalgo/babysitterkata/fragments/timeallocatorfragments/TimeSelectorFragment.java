package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.brandon.hidalgo.babysitterkata.databinding.TimeSelectorFragmentBinding;

import static com.brandon.hidalgo.babysitterkata.Constants.ATLANTIC_MERIDIAN;
import static com.brandon.hidalgo.babysitterkata.Constants.NOON;
import static com.brandon.hidalgo.babysitterkata.Constants.PACIFIC_MERIDIAN;

public abstract class TimeSelectorFragment extends Fragment {
    public String mQuery;

    private TimeSelectorParent mParent;
    private ViewGroup mContainer;

    private NumberPicker mHourPicker;
    private TextView mSelectedTimeTextView;

    int mMinTime;
    int mMaxTime;

    final NumberPicker.OnValueChangeListener mHourPickerOnValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            TransitionManager.beginDelayedTransition(mContainer);
            mSelectedTimeTextView.setText(convertTimeToMeridian(newVal));
        }
    };

    public interface TimeSelectorParent {
        void goToNextStep(@Nullable View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (mParent != null) {
            return;
        }

        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof TimeSelectorParent) {
            mParent = (TimeSelectorParent) parentFragment;
        } else {
            throw new IllegalArgumentException("Parent must implement TimeSelectorParent to ensure proper functionality.");
        }
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TimeSelectorFragmentBinding binding = TimeSelectorFragmentBinding.inflate(inflater, container, false);
        mContainer = container;
        binding.setFragment(this);
        mHourPicker = binding.hourPicker;
        mSelectedTimeTextView = binding.selectedTimeTv;
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHourPicker.setMinValue(mMinTime);
        mHourPicker.setMaxValue(mMaxTime);
        if (mMaxTime - mMinTime == 0) {
            mHourPicker.setEnabled(false);
        }
        mHourPicker.setOnValueChangedListener(mHourPickerOnValueChangeListener);
        mSelectedTimeTextView.setText(convertTimeToMeridian(mHourPicker.getValue()));
    }

    @Override
    public void onPause() {
        super.onPause();
        mHourPicker.setOnValueChangedListener(null);
    }

    public int getMinTime() {
        return mMinTime;
    }

    public int getMaxTime() {
        return mMaxTime;
    }

    String convertTimeToMeridian(int time) {
        String meridian = ATLANTIC_MERIDIAN;
        if (time == 0) {
            time = NOON;
            meridian = ATLANTIC_MERIDIAN;
        } else if (time > NOON) {
            time -= NOON;
            meridian = PACIFIC_MERIDIAN;
        }
        return time + meridian;
    }

    final int getSelectedTime() {
        return mHourPicker.getValue();
    }

    public void setPickerMax(int max) {
        mMaxTime = max;
    }

    protected abstract Class<? extends Fragment> getNextStep();

    protected abstract String getFragmentTag();


    /* *********************************** */
    /* METHODS FOR TESTING PURPOSES ONLY  */
    /* ********************************** */

    void setContainer(ViewGroup container) {
        mContainer = container;
    }

    void setParentFragment(TimeSelectorParent parent) {
        mParent = parent;
    }
}
