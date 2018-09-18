package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brandon.hidalgo.babysitterkata.Constants;
import com.brandon.hidalgo.babysitterkata.R;
import com.brandon.hidalgo.babysitterkata.databinding.BabySitterTimeAllocationFragmentBinding;

import java.util.LinkedList;
import java.util.List;

import static com.brandon.hidalgo.babysitterkata.Constants.CONTINUE_BUTTON_TRANSITION_DURATION;

public class BabySitterTimeAllocationFragment extends Fragment implements TimeSelectorFragment.TimeSelectorParent {
    public static final String TAG = "BabySitterTimeAllocationFragment";

    private FragmentActivity mActivity;
    private ViewGroup mContainer;

    private TimeSelectorFragment mCurrentFragment;
    private Button mContinueButton;

    /* A list of the selected times of each fragment. */
    private List<Integer> mSelectedTimes;

    private final int SELECTED_END_TIME_INDEX = 1;

    private Fragment mNextFragment;

    private FragmentManager mChildFragmentManager;

    /**
     * The Transition Listener used when transitioning to the WageCalculatorFragment.
     */
    final Transition.TransitionListener mContinueButtonTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {
            /* Once the button is transitioned, add the WageCalculatorFragment to the layout after giving it all the selected times. */
            mNextFragment.setArguments(createSelectedTimesBundle());
            mActivity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_vertical, R.anim.slide_out_vertical)
                    .replace(R.id.fragment_container, mNextFragment)
                    .commit();
        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    /**
     * Returns a new instance of the Fragment.
     *
     * @return BabySitterTimeAllocationFragment
     */
    public static BabySitterTimeAllocationFragment newInstance() {
        return new BabySitterTimeAllocationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mSelectedTimes = new LinkedList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        mChildFragmentManager = getChildFragmentManager();
        initializeFirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BabySitterTimeAllocationFragmentBinding binding = BabySitterTimeAllocationFragmentBinding.inflate(inflater, container, false);
        mContainer = container;
        binding.setFragment(this);
        mContinueButton = binding.nextButton;
        return binding.getRoot();
    }

    /**
     * OnClick method for continuing to the next step.
     *
     * @param view - The view which received the OnClickEvent.
     */
    @Override
    public void goToNextStep(@Nullable View view) {
        continueToNextStep(mCurrentFragment.getNextStep());
    }

    /**
     * Populates the fragment container with the first fragment (BedTimeSelectorFragment).
     */
    void initializeFirstFragment() {
        mCurrentFragment = BedTimeSelectorFragment.newInstance();

        mChildFragmentManager.beginTransaction()
                .add(R.id.baby_sitter_allocation_fragment_container, mCurrentFragment, mCurrentFragment.getFragmentTag())
                .commit();
    }

    /**
     * Determines the next step based on the provided fragment class and
     * executes a fragment transaction to display the next step.
     *
     * @param fragmentClass - The next fragment to display.
     */
    void continueToNextStep(Class<? extends Fragment> fragmentClass) {
        /* Store the selected time from the current fragment inside the list. */
        if (mCurrentFragment != null) {
            mSelectedTimes.add(mCurrentFragment.getSelectedTime());
        }

        try {
            mNextFragment = fragmentClass.newInstance();

            if (fragmentClass == StartTimeSelectorFragment.class) {
                configureStartTimeSelectorFragment();
            }

            if (fragmentClass.getSuperclass() == TimeSelectorFragment.class) {
                showNextTimeSelectorFragment();
            } else {
                transitionToCalculationFragment();
            }
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a transition to the WageCalculatorFragment.
     */
    private void transitionToCalculationFragment() {
        /* Build a nice transition for the WageCalculatorFragment */
        AutoTransition continueButtonTransition = new AutoTransition();
        continueButtonTransition.setDuration(CONTINUE_BUTTON_TRANSITION_DURATION);
        continueButtonTransition.addListener(mContinueButtonTransitionListener);
        /* Fade out the continue button. */
        TransitionManager.beginDelayedTransition(mContainer, continueButtonTransition);
        mContinueButton.setVisibility(View.GONE);
    }

    /**
     * Shows the next TimeSelectorFragment.
     */
    private void showNextTimeSelectorFragment() {
        mCurrentFragment = (TimeSelectorFragment) mNextFragment;
        mChildFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .replace(R.id.baby_sitter_allocation_fragment_container, mCurrentFragment, mCurrentFragment.getFragmentTag())
                .commit();
    }

    /**
     * Sets the StartTimeSelectorFragment max time based on the entered end time
     * and changes the text of the continue button to indicate the next step will be the calculation.
     */
    private void configureStartTimeSelectorFragment() {
        mContinueButton.setText(R.string.calculate_button_text);

        if (mSelectedTimes != null && mSelectedTimes.size() >= 2) {
            /* Only allow the user to input a max time one hour less than their end time. */
            ((StartTimeSelectorFragment) mNextFragment).setPickerMax(mSelectedTimes.get(SELECTED_END_TIME_INDEX) - 1);
        }
    }

    /**
     * Returns a Bundle populated with all the selected times.
     *
     * @return Bundle
     */
    private Bundle createSelectedTimesBundle() {
        int SELECTED_BED_TIME_INDEX = 0;
        int SELECTED_START_TIME_INDEX = 2;
        Bundle result = new Bundle();
        result.putInt(Constants.BED_TIME_BUNDLE_KEY, mSelectedTimes.get(SELECTED_BED_TIME_INDEX));
        result.putInt(Constants.END_TIME_BUNDLE_KEY, mSelectedTimes.get(SELECTED_END_TIME_INDEX)); /* End time index isn't local since we use it in another calculation. */
        result.putInt(Constants.START_TIME_BUNDLE_KEY, mSelectedTimes.get(SELECTED_START_TIME_INDEX));
        return result;
    }

    /* *********************************** */
    /* METHODS FOR TESTING PURPOSES ONLY  */
    /* ********************************** */

    void setActivity(FragmentActivity activity) {
        mActivity = activity;
    }

    void setContainer(ViewGroup container) {
        mContainer = container;
    }

    void setSelectedTimes(List<Integer> selectedTimes) {
        mSelectedTimes = selectedTimes;
    }

    Fragment getNextFragment() {
        return mNextFragment;
    }

    void setChildFragmentManager(FragmentManager fm) {
        mChildFragmentManager = fm;
    }
}
