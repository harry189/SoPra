package com.example.sfirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SaveDialogFragment extends AppCompatDialogFragment {

    public interface SaveDialogListener {
        public void onSaveClick(String title);
    }
    private EditText mEditText;
    private SaveDialogListener dialogListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_stop_recording, container);
        mEditText = (EditText) view.findViewById(R.id.edit_title);

        Button discardButton = (Button) view.findViewById(R.id.dialogButtonDiscard);
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button saveButton = (Button) view.findViewById(R.id.dialogButtonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onSaveClick(mEditText.getText().toString());
                getDialog().dismiss();
            }
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Aufnahme sichern");
        return view;
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        try {
            dialogListener = (SaveDialogListener) ctx;
        } catch (ClassCastException e) {
            throw new ClassCastException(ctx.toString() + " must implement OnArticleSelectedListener");
        }
    }
}
