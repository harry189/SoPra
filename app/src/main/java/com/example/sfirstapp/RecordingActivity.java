package com.example.sfirstapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sfirstapp.customwidget.CircleImageButton;
import com.example.sfirstapp.db.NamesDbAdapter;

import java.io.File;
import java.io.IOException;

public class RecordingActivity extends AppCompatActivity implements SaveDialogFragment.SaveDialogListener {
    private static final String LOG_TAG="RecordingActivity";
    private boolean toStartRecording = true;
    private String newFileName;
    private MediaRecorder mRecorder = null;
    private Chronometer recordingTime;
    private CircleImageButton circle;
    private Drawable drawable, wrappedDrawable;
    private int accent, white;
    final Context context = this;
    private Dialog dialog;
    private NamesDbAdapter namesDbAdapter;
    private String tmpRecording;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        //newFileName = new File(context.getFilesDir(), "tmp.3gp");
        tmpRecording = getFilesDir().getAbsolutePath() + "/tmp.3gp";
        recordingTime = (Chronometer) findViewById(R.id.recording_time);

        circle  = (CircleImageButton) findViewById(R.id.mic_button);
        drawable = circle.getDrawable();
        wrappedDrawable = DrawableCompat.wrap(drawable);

        accent = ContextCompat.getColor(this, R.color.colorAccent);
        white = ContextCompat.getColor(this, android.R.color.white);
        circle.setImageDrawable(wrappedDrawable);

        namesDbAdapter = new NamesDbAdapter(this);
        namesDbAdapter.open();

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord();
                if (toStartRecording) {
                    wrappedDrawable.setTint(accent);
                    circle.setImageDrawable(wrappedDrawable);
                    recordingTime.setBase(SystemClock.elapsedRealtime());
                    recordingTime.start();
                } else {
                    wrappedDrawable.setTint(white);
                    circle.setImageDrawable(wrappedDrawable);
                    FragmentManager manager = getSupportFragmentManager();
                    AppCompatDialogFragment frag = (AppCompatDialogFragment) manager.findFragmentByTag("fragment_save_dialog");
                    if (frag != null) {
                        manager.beginTransaction().remove(frag).commit();
                    }
                    SaveDialogFragment saveDialog = new SaveDialogFragment();
                    saveDialog.show(manager, "fragment_save_dialog");
                    recordingTime.setBase(SystemClock.elapsedRealtime());
                    wrappedDrawable.setTint(white);
                }
                toStartRecording = !toStartRecording;
                }
        });
    }

    @Override
    public void onSaveClick(String title) {
        File file = new File(tmpRecording);
        String newFileName = context.getFilesDir() + "/" + title  + ".3gp";
        file.renameTo(new File(newFileName));
        namesDbAdapter.createRecording(title, "Europe", newFileName);
    }

    private void onRecord() {
        if (toStartRecording) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(tmpRecording);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    private void stopRecording() {
        recordingTime.stop();
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    @Override
    public void onStop() {
        super.onStop();
        if(mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }
}
