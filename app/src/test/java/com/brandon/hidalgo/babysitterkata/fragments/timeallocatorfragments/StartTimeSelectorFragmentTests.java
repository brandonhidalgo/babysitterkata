package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

public class StartTimeSelectorFragmentTests extends TimeSelectorFragmentTests<StartTimeSelectorFragment> {
    @Override
    protected void initializeFragment() {
        mSubject = StartTimeSelectorFragment.newInstance();
        mSubject.setPickerMax(18);
        mSubject.setParentFragment(mTestParent);
        SupportFragmentTestUtil.startFragment(mSubject);
        mSubject.setContainer(mTestContainer);
    }
}
