package com.example.takemyattendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class TopicDialog extends DialogFragment {
    private EditText topicEditText;
    private EditText periodEditText;
    Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        bundle = this.getArguments();
        View DialogView = inflater.inflate(R.layout.topic_dialog, null);
        topicEditText = DialogView.findViewById(R.id.topic);
        periodEditText = DialogView.findViewById(R.id.period);
        builder.setView(DialogView)
                .setTitle("Class Details")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String topic = topicEditText.getText().toString();
                        String period = periodEditText.getText().toString();
                        bundle.putString("topic",topic);
                        bundle.putString("period",period);
                        Intent intent = new Intent(getContext(), TakeAttendanceActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TopicDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
