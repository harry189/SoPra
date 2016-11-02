package com.example.sfirstapp;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sfirstapp.model.NamesContract.RecordingEntry;


import com.example.sfirstapp.db.NamesDbAdapter;

import static android.os.Build.ID;


public class DbListFragment extends ListFragment {

    private SimpleCursorAdapter dataAdapter;
    private NamesDbAdapter namesDbAdapter;
    private Player mPlayer;
    private int prevItemColor;
    private int prevPosition;
    private static final String TAG = "DBListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        namesDbAdapter = new NamesDbAdapter(getContext());
        namesDbAdapter.open();
        //Clean all data
        //dbHelper.deleteAllRecordings();
        //Add some data
        //dbHelper.insertSomeRecordings();
        Cursor cursor = namesDbAdapter.fetchAllRecordings();
        Log.w(TAG, Integer.toString(cursor.getCount()));

        String[] columns = new String[] {RecordingEntry.COLUMN_NAME_CONTACT};
        int[] to = new int[] {
                R.id.record_title
        };
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.record_item,
                cursor,
                columns,
                to,
                0);
        setListAdapter(dataAdapter);
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        try {
            mPlayer = (Player) ctx;
        } catch (ClassCastException e) {
            throw new ClassCastException(ctx.toString() + " must implement OnRecordingSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        prevPosition = position;
        Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        TextView title = (TextView) v.findViewById(R.id.record_title);
        prevItemColor = title.getCurrentTextColor();
        title.setTextColor(getResources().getColor(R.color.colorAccent));
        c.moveToPosition(position);
        Log.w(TAG, c.getString(3));
        mPlayer.play(c.getString(3));
        //onRecordingSelected(position, c.getString(3));
        //Toast.makeText(getActivity(), c.getString(1) + " selected", Toast.LENGTH_LONG).show();
    }

    public void setColorAtPos(int position) {
        View v = getViewByPosition(position);
        TextView title = (TextView) v.findViewById(R.id.record_title);
        title.setTextColor(prevItemColor);
    }

    public void resetPrevItemText() {
        View v = getViewByPosition(prevPosition);
        TextView title = (TextView) v.findViewById(R.id.record_title);
        title.setTextColor(prevItemColor);
    }

    public View getViewByPosition(int pos) {
        ListView listView = getListView();
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "In onResume...about to reset list");
        Cursor newCursor = namesDbAdapter.fetchAllRecordings();
        dataAdapter.changeCursor(newCursor);
        dataAdapter.notifyDataSetChanged();
    }

    public void onStop() {
        Log.v(TAG, "Onstop db fragment");
        super.onStop();
    }
}
