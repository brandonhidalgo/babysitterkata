package com.brandon.hidalgo.babysitterkata;

import com.brandon.hidalgo.babysitterkata.fragments.timeallocatorfragments.BabySitterTimeAllocationFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTests {
    private MainActivity mSubject;

    @Before
    public void setUp() {
        mSubject = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void whenActivityStartedFragmentContainerShouldBeInitializedWithBabySitterTimeAllocationFragment() {
        assertNotNull(mSubject.getSupportFragmentManager().findFragmentByTag(BabySitterTimeAllocationFragment.TAG));
    }
}
