package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brandon.hidalgo.babysitterkata.MainActivity;
import com.brandon.hidalgo.babysitterkata.R;
import com.brandon.hidalgo.babysitterkata.fragments.WageCalculatorFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.LinkedList;
import java.util.List;

import static com.brandon.hidalgo.babysitterkata.Constants.BED_TIME_BUNDLE_KEY;
import static com.brandon.hidalgo.babysitterkata.Constants.BED_TIME_SELECTOR_FRAGMENT_TAG;
import static com.brandon.hidalgo.babysitterkata.Constants.END_TIME_BUNDLE_KEY;
import static com.brandon.hidalgo.babysitterkata.Constants.END_TIME_SELECTOR_FRAGMENT_TAG;
import static com.brandon.hidalgo.babysitterkata.Constants.START_TIME_BUNDLE_KEY;
import static com.brandon.hidalgo.babysitterkata.Constants.START_TIME_SELECTOR_FRAGMENT_TAG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BabySitterTimeAllocationFragmentTest {
    private BabySitterTimeAllocationFragment mSubject;

    private FragmentActivity mTestActivity;

    @Mock
    private ViewGroup mTestContainer;

    @Mock
    private FragmentManager mTestFragmentManager;

    @Mock
    private FragmentTransaction mTestFragmentTransaction;

    private Button continueButton;

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
        assertNotNull(continueButton);
    }

    @Test
    public void whenFragmentStartedFragmentContainerShouldContainBedTimeSelectorFragment() {
        mSubject.initializeFirstFragment();
        verify(mTestFragmentTransaction).add(ArgumentMatchers.eq(R.id.baby_sitter_allocation_fragment_container), any(BedTimeSelectorFragment.class), eq(BED_TIME_SELECTOR_FRAGMENT_TAG));
    }

    @Test
    public void whenContinueToEndTimeSelectorStepFragmentContainerShouldOnlyContainEndTimeSelectorFragment() {
        mSubject.continueToNextStep(EndTimeSelectorFragment.class);
        verify(mTestFragmentTransaction).replace(eq(R.id.baby_sitter_allocation_fragment_container), any(EndTimeSelectorFragment.class), eq(END_TIME_SELECTOR_FRAGMENT_TAG));
    }

    @Test
    public void whenContinueToStartTimeSelectorStepFragmentContainerShouldOnlyContainStartTimeSelectorFragment() {
        mSubject.continueToNextStep(StartTimeSelectorFragment.class);
        verify(mTestFragmentTransaction).replace(eq(R.id.baby_sitter_allocation_fragment_container), any(StartTimeSelectorFragment.class), eq(START_TIME_SELECTOR_FRAGMENT_TAG));
    }

    @Test
    public void whenContinueToCalculationStepActivityShouldOnlyContainsWageCalculatorFragment() {
        buildSelectedTimesList();

        mSubject.continueToNextStep(WageCalculatorFragment.class);

        mSubject.mContinueButtonTransitionListener.onTransitionEnd(mock(Transition.class));

        verify(mTestFragmentTransaction).replace(eq(R.id.fragment_container), any(WageCalculatorFragment.class));
    }

    @Test
    public void whenContinueToCalculationStepWageCalculatorFragmentShouldHavePassedSelectedTimes() {
        buildSelectedTimesList();

        mSubject.continueToNextStep(WageCalculatorFragment.class);

        mSubject.mContinueButtonTransitionListener.onTransitionEnd(mock(Transition.class));

        Bundle receivedSelectedTimes = mSubject.getNextFragment().getArguments();

        assert receivedSelectedTimes != null;
        assertEquals(1, receivedSelectedTimes.getInt(BED_TIME_BUNDLE_KEY));
        assertEquals(2, receivedSelectedTimes.getInt(END_TIME_BUNDLE_KEY));
        assertEquals(3, receivedSelectedTimes.getInt(START_TIME_BUNDLE_KEY));
    }

    private void initializeFragment() {
        mSubject = BabySitterTimeAllocationFragment.newInstance();
        SupportFragmentTestUtil.startFragment(mSubject);
        stubFragmentTransactions();
        mSubject.setActivity(mTestActivity);
        mSubject.setContainer(mTestContainer);
        mSubject.setChildFragmentManager(mTestFragmentManager);
    }

    private void stubFragmentTransactions() {
        mTestActivity = spy(Robolectric.setupActivity(MainActivity.class));
        when(mTestFragmentTransaction.setCustomAnimations(anyInt(), anyInt())).thenReturn(mTestFragmentTransaction);
        when(mTestFragmentTransaction.replace(anyInt(), any(Fragment.class))).thenReturn(mTestFragmentTransaction);
        when(mTestFragmentTransaction.replace(anyInt(), any(Fragment.class), anyString())).thenReturn(mTestFragmentTransaction);
        when(mTestFragmentTransaction.add(anyInt(), any(Fragment.class), anyString())).thenReturn(mTestFragmentTransaction);
        when(mTestFragmentManager.beginTransaction()).thenReturn(mTestFragmentTransaction);
        doReturn(mTestFragmentManager).when(mTestActivity).getSupportFragmentManager();
    }

    private void initializeViews() {
        View fragmentView = mSubject.getView();
        assert fragmentView != null;
        continueButton = fragmentView.findViewById(R.id.next_button);
    }

    private void buildSelectedTimesList() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        mSubject.setSelectedTimes(list);
    }
}
