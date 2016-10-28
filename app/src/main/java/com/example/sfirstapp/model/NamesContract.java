package com.example.sfirstapp.model;

import android.provider.BaseColumns;

/**
 * Created by Moritz on 20.10.2016.
 */

public class NamesContract {

    private NamesContract() {}

    public static class RecordingEntry implements BaseColumns {
        public static final String TABLE_NAME = "recording";
        public static final String COLUMN_NAME_CONTACT = "contact";
        public static final String COLUMN_NAME_REGION = "region";
        public static final String COLUMN_NAME_PATH = "path";

    }
}
