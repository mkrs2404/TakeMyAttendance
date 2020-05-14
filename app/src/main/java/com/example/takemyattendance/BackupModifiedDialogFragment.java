package com.example.takemyattendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class BackupModifiedDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true)
                .setTitle("Backup Modified")
                .setMessage("The backup has been modified on your G-Drive by another device. Please Sync before continuing. Failing to do so will lead to overwriting of the backup.")
                .setPositiveButton("OK",(dialog, id) -> {
                    BackupModifiedDialogFragment.this.getDialog().cancel();
                });

        return builder.create();
    }
}
