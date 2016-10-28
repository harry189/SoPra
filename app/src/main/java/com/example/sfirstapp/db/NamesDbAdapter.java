package com.example.sfirstapp.db;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sfirstapp.model.NamesContract.RecordingEntry;
import com.example.sfirstapp.model.Recording;

/**
 * Created by Moritz on 21.10.2016.
 */

public class NamesDbAdapter {

    private static final String TAG = "NamesDbAdapter";
    private NamesDbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;

    public NamesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public NamesDbAdapter open() throws SQLException {
        mDbHelper = new NamesDbHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        if(mDb == null) Log.w(TAG, "Db is null");
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createRecording(String contact, String region,
                                String path) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(RecordingEntry.COLUMN_NAME_CONTACT, contact);
        initialValues.put(RecordingEntry.COLUMN_NAME_PATH, path);
        initialValues.put(RecordingEntry.COLUMN_NAME_REGION, region);

        return mDb.insert(RecordingEntry.TABLE_NAME, null, initialValues);
    }

    public boolean deleteAllRecordings() {

        int doneDelete = 0;
        doneDelete = mDb.delete(RecordingEntry.TABLE_NAME, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchRecordingByContact(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        String[] projection = {RecordingEntry._ID,
                RecordingEntry.COLUMN_NAME_CONTACT, RecordingEntry.COLUMN_NAME_REGION,
                RecordingEntry.COLUMN_NAME_PATH};
        if (inputText == null || inputText.length() == 0) {
            mCursor = mDb.query(RecordingEntry.TABLE_NAME,
                    projection, null, null, null, null, null);
        } else {
            mCursor = mDb.query(true, RecordingEntry.TABLE_NAME,
                    projection,
                    RecordingEntry.COLUMN_NAME_CONTACT + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllRecordings() {

        Cursor mCursor = mDb.query(RecordingEntry.TABLE_NAME,
                new String[]{RecordingEntry._ID,
                        RecordingEntry.COLUMN_NAME_CONTACT, RecordingEntry.COLUMN_NAME_REGION,
                        RecordingEntry.COLUMN_NAME_PATH},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeRecordings() {
        String mFileName ="/data/data/com.example.sfirstapp/files/audiorecordtest.3gp";

        createRecording("Regie Miller", "Europe", mFileName);
        createRecording("Thiago Alcantara", "Albania", mFileName);
        createRecording("Method Man", "Algeria", mFileName);
        createRecording("Hans Entertainment", "American Samoa", mFileName);
        createRecording("Hans Peter Bauchredner", "Andorra", mFileName);
        createRecording("Karate Andi", "Angola", mFileName);
        createRecording("Rick James", "Anguilla", mFileName);

    }
}