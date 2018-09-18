package com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments;

import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static junit.framework.Assert.assertEquals;

public class EndTimeSelectorFragmentTests extends TimeSelectorFragmentTests<EndTimeSelectorFragment> {
    @Test
    public void convertTimeToMeridianShouldReturnMeridianValueOfTimeEqualToTwentyFour() {
        assertEquals("12 A.M.", mSubject.convertTimeToMeridian(24));
    }

    @Test
    public void convertTimeToMeridianShouldReturnMeridianValueOfTimeGreaterThanTwentyFour() {
        assertEquals("2 A.M.", mSubject.convertTimeToMeridian(26));
    }

    @Test
    public void convertTimeToMeridianShouldReturnMeridianValueOfTimeGreaterThanTwelve() {
        assertEquals("10 P.M.", mSubject.convertTimeToMeridian(10));
    }

    @Override
    protected void initializeFragment() {
        mSubject = EndTimeSelectorFragment.newInstance();
        mSubject.setParentFragment(mTestParent);
        SupportFragmentTestUtil.startFragment(mSubject);
        mSubject.setContainer(mTestContainer);
    }
}
