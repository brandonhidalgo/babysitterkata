package com.brandon.hidalgo.babysitterkata;

public class Constants {
    /* Animation Durations */
    public static final int CONTINUE_BUTTON_TRANSITION_DURATION = 200;

    /* Babysitter Time Bounds */
    public static final int EARLIEST_START_TIME = 17; /* 5 P.M. */
    public static final int LATEST_END_TIME = 28; /* 4 A.M. */
    public static final int MIDNIGHT = 24; /* 12 A.M. */
    public static final int NOON = 12;
    public static final String ATLANTIC_MERIDIAN = " A.M.";
    public static final String PACIFIC_MERIDIAN = " P.M.";

    /* Babysitter Rates*/
    public static final int BEDTIME_RATE = 8;
    public static final int TILL_BEDTIME_RATE = 12;
    public static final int AFTER_MIDNIGHT_RATE = 16;

    /* Babysitter Select Time Bundle Keys */
    public static final String START_TIME_BUNDLE_KEY = "StartTime";
    public static final String BED_TIME_BUNDLE_KEY = "BedTime";
    public static final String END_TIME_BUNDLE_KEY = "EndTime";

    /* TimeSelectorFragment Tags */
    public static final String BED_TIME_SELECTOR_FRAGMENT_TAG = "BedTimeSelectorFragment";
    public static final String END_TIME_SELECTOR_FRAGMENT_TAG = "EndTimeSelectorFragment";
    public static final String START_TIME_SELECTOR_FRAGMENT_TAG = "StartTimeSelectorFragment";
}
