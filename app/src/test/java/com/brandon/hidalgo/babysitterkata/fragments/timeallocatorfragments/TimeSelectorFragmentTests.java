package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.brandon.hidalgo.babysitterkata.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@Ignore
@RunWith(RobolectricTestRunner.class)
public abstract class TimeSelectorFragmentTests<T extends TimeSelectorFragment> {
    T mSubject;

    @Mock
    ViewGroup mTestContainer;

    private NumberPicker mHourPicker;
    private TextView mSelectedTimeTextView;

    final TimeSelectorFragment.TimeSelectorParent mTestParent = new TimeSelectorFragment.TimeSelectorParent() {
        @Override
        public void goToNextStep(@Nullable View view) {
            //
        }
    };

    @Before
    public void setUp() {
        if (mSubject == null) {
            MockitoAnnotations.initMocks(this);
            initializeFragment();
            initializeViews();
        }
    }

    @Test
    public void whenFragmentStartedViewsShouldNotBeNull() {
        assertNotNull(mHourPicker);
        assertNotNull(mSelectedTimeTextView);
    }

    @Test
    public void whenFragmentStartedHourPickerShouldHaveMinOfSpecifiedMinTime() {
        assertEquals(mSubject.getMinTime(), mHourPicker.getMinValue());
    }

    @Test
    public void whenFragmentStartedHourPickerShouldHaveMaxOfSpecifiedMaxTime() {
        assertEquals(mSubject.getMaxTime(), mHourPicker.getMaxValue());
    }

    @Test
    public void whenFragmentStartedSelectedTextViewShouldShowSpecifiedTimeInMeridian() {
        assertEquals(mSubject.convertTimeToMeridian(mHourPicker.getValue()), mSelectedTimeTextView.getText().toString());
    }

    @Test
    public void whenHourPickerValueChangesSelectedTextViewShouldShowSpecifiedValue() {
        mSubject.mHourPickerOnValueChangeListener.onValueChange(mHourPicker, mSubject.getMinTime(), mSubject.getMaxTime());
        assertEquals(mSubject.convertTimeToMeridian(mSubject.getMaxTime()), mSelectedTimeTextView.getText().toString());
    }

    @Test
    public void whenOnlyOneHourIsAvailableToSelectHourPickerIsDisabled() {
        mSubject.mMinTime = 11;
        mSubject.mMaxTime = 11;
        mSubject.onResume();
        assertFalse(mHourPicker.isEnabled());
    }

    protected abstract void initializeFragment();

    private void initializeViews() {
        View fragmentView = mSubject.getView();

        assert fragmentView != null;

        mHourPicker = fragmentView.findViewById(R.id.hour_picker);
        mSelectedTimeTextView = fragmentView.findViewById(R.id.selected_time_tv);
    }
}
