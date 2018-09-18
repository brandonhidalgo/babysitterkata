package com.brandon.hidalgo.babysitterkata.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brandon.hidalgo.babysitterkata.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static android.view.View.VISIBLE;
import static com.brandon.hidalgo.babysitterkata.Constants.AFTER_MIDNIGHT_RATE;
import static com.brandon.hidalgo.babysitterkata.Constants.BEDTIME_RATE;
import static com.brandon.hidalgo.babysitterkata.Constants.BED_TIME_BUNDLE_KEY;
import static com.brandon.hidalgo.babysitterkata.Constants.END_TIME_BUNDLE_KEY;
import static com.brandon.hidalgo.babysitterkata.Constants.MIDNIGHT;
import static com.brandon.hidalgo.babysitterkata.Constants.START_TIME_BUNDLE_KEY;
import static com.brandon.hidalgo.babysitterkata.Constants.TILL_BEDTIME_RATE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class WageCalculatorFragmentTests {
    private WageCalculatorFragment subject;
    private Button tryAgainButton;
    private TextView resultTextView;

    @Before
    public void setUp() {
        if (subject == null) {
            initializeFragment();
            initializeChildrenViews();
        }
    }

    @Test
    public void whenFragmentStartedStartChildViewsShouldNotBeNull() {
        assertNotNull(tryAgainButton);
        assertNotNull(resultTextView);
    }

    @Test
    public void whenFragmentHasArgumentsSelectedTimesShouldBeInitialized() {
        int testStartTime = 0;
        int testEndTime = 24;
        int testBedTime = 12;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.onStart();
        assertEquals(testStartTime, subject.getSelectedStartTime());
        assertEquals(testEndTime, subject.setSelectedEndTime());
        assertEquals(testBedTime, subject.setSelectedBedTime());
    }

    @Test
    public void whenDisplayNightlyChargeResultTextViewShouldBeVisible() {
        subject.displayNightlyCharge(0);
        assertEquals(VISIBLE, resultTextView.getVisibility());
    }

    @Test
    public void whenDisplayNightlyChargeResultTextViewShouldShowResultText() {
        int testNightlyCharge = 133;
        subject.displayNightlyCharge(testNightlyCharge);
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsBetweenBedTimeAndMidnightChargeShouldOnlyBeAFactorOfBedTimeRate() {
        int testStartTime = 17;
        int testEndTime = 24;
        int testBedTime = 17;
        int testNightlyCharge = (testEndTime - testStartTime) * BEDTIME_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsBetweenEarliestStartTimeAndBedTimeChargeShouldOnlyBeAFactorOfTillBedTimeRate() {
        int testStartTime = 17;
        int testEndTime = 23;
        int testBedTime = 23;
        int testNightlyCharge = (testEndTime - testStartTime) * TILL_BEDTIME_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsBetweenMidnightAndLatestEndTimeChargeShouldOnlyBeAFactorOfAfterMidnightRate() {
        int testStartTime = 24;
        int testEndTime = 28;
        int testBedTime = 17;
        int testNightlyCharge = (testEndTime - testStartTime) * AFTER_MIDNIGHT_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsThroughStartBedAndEndTimeChargeShouldBeAFactorOfAllRates() {
        int testStartTime = 18;
        int testEndTime = 27;
        int testBedTime = 20;
        int testNightlyCharge = (testBedTime - testStartTime) * TILL_BEDTIME_RATE +
                (24 - testBedTime) * BEDTIME_RATE +
                (testEndTime - 24) * AFTER_MIDNIGHT_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsFromBedTimeToBeforeMidnightChargeShouldBeAFactorOfBedTimeRate() {
        int testStartTime = 20;
        int testEndTime = 23;
        int testBedTime = 20;
        int testNightlyCharge = (testEndTime - testStartTime) * BEDTIME_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsFromBedTimeToAfterMidnightChargeShouldBeAFactorOfAllRates() {
        int testStartTime = 23;
        int testBedTime = 22;
        int testEndTime = 25;
        int testNightlyCharge = (MIDNIGHT - testStartTime) * BEDTIME_RATE +
                (testEndTime - MIDNIGHT) * AFTER_MIDNIGHT_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsFromStartThroughBedtimeToBeforeMidnightChargeShouldBeAFactorOfTillBedTimeAndBedTimeRates() {
        int testStartTime = 18;
        int testBedTime = 20;
        int testEndTime = 23;
        int testNightlyCharge = (testBedTime - testStartTime) * TILL_BEDTIME_RATE +
                (testEndTime - testBedTime) * BEDTIME_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterSitsAfterBedtimeToBeforeMidnightChargeShouldBeAFactorOfBedtimeRate() {
        int testStartTime = 21;
        int testBedTime = 20;
        int testEndTime = 23;
        int testNightlyCharge = (testEndTime - testStartTime) * BEDTIME_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    @Test
    public void whenBabySitterStartsAfterMidnightChargeShouldBeAFactorOfAfterMidnightRate() {
        int testStartTime = 25;
        int testBedTime = 20;
        int testEndTime = 28;
        int testNightlyCharge = (testEndTime - testStartTime) * AFTER_MIDNIGHT_RATE;
        setStartEndAndBedtime(testStartTime, testEndTime, testBedTime);
        subject.calculateNightlyCharge();
        assertEquals(String.format("$%s.00", testNightlyCharge), resultTextView.getText().toString());
    }

    private void initializeFragment() {
        subject = WageCalculatorFragment.newInstance();
        SupportFragmentTestUtil.startFragment(subject);
    }

    private void initializeChildrenViews() {
        assertNotNull(subject.getView());
        View fragmentView = subject.getView();
        tryAgainButton = fragmentView.findViewById(R.id.restart_button);
        resultTextView = fragmentView.findViewById(R.id.result_text_view);
    }

    private void setStartEndAndBedtime(int start, int end, int bedtime) {
        Bundle times = new Bundle();
        times.putInt(BED_TIME_BUNDLE_KEY, bedtime);
        times.putInt(END_TIME_BUNDLE_KEY, end);
        times.putInt(START_TIME_BUNDLE_KEY, start);
        subject.setArguments(times);
        subject.onCreate(null);
    }
}
