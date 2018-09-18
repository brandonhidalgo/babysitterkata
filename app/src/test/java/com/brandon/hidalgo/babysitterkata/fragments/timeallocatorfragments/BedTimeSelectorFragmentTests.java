package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static junit.framework.Assert.assertEquals;

public class BedTimeSelectorFragmentTests extends TimeSelectorFragmentTests<BedTimeSelectorFragment> {
    @Test
    public void convertTimeToMeridianShouldReturnTwelveAMWhenPassedZero() {
        assertEquals("12 A.M.", mSubject.convertTimeToMeridian(0));
    }

    @Test
    public void convertTimeToMeridianShouldReturnMeridianValueOfTimeGreaterThanTwelve() {
        assertEquals("5 P.M.", mSubject.convertTimeToMeridian(17));
    }

    @Test
    public void convertTimeToMeridianShouldReturnMeridianValueOfTimeLessThanTwelve() {
        assertEquals("4 A.M.", mSubject.convertTimeToMeridian(4));
    }

    @Override
    protected void initializeFragment() {
        mSubject = BedTimeSelectorFragment.newInstance();
        mSubject.setParentFragment(mTestParent);
        SupportFragmentTestUtil.startFragment(mSubject);
        mSubject.setContainer(mTestContainer);
    }
}
