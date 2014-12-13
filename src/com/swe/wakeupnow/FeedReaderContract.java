package com.swe.wakeupnow;

import android.provider.BaseColumns;

public final class FeedReaderContract {
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "t_alarm";
        public static final String COLUMN_NAME_HOUR = "hh";
        public static final String COLUMN_NAME_MINUTE = "mm";
    }
}
