package com.example.takemyattendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class NoConnectionDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true)
                .setTitle("No Connection")
                .setMessage("Could not connect to the Internet.\nPlease check your Internet connectivity.")
                .setPositiveButton("OK",(dialog, id) -> {
                    NoConnectionDialogFragment.this.getDialog().cancel();
                });

        return builder.create();
    }
}
