package com.example.takemyattendance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {

    private static EditText startDate;
    private static EditText endDate;
    private DatePickerDialogFragmentCustom mDatePickerDialogFragment;
    private static Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View DialogView = inflater.inflate(R.layout.date_picker_layout, null);
        bundle = this.getArguments();

        startDate = DialogView.findViewById(R.id.startDate);
        endDate = DialogView.findViewById(R.id.endDate);
        mDatePickerDialogFragment = new DatePickerDialogFragmentCustom();

        startDate.setOnClickListener(v -> {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragmentCustom.FLAG_START_DATE);
            mDatePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        endDate.setOnClickListener(v -> {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragmentCustom.FLAG_END_DATE);
            mDatePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        builder.setView(DialogView)
                .setPositiveButton("OK", (dialog, id) -> {
                    DatePickerDialogFragment.this.getDialog().cancel();
                    if(!startDate.getText().toString().isEmpty() && !endDate.getText().toString().isEmpty()) {
                        Intent intent = new Intent(getContext(), ExportDataActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> DatePickerDialogFragment.this.getDialog().cancel())
                .setTitle("Select Date");
        return builder.create();
    }

    public static class DatePickerDialogFragmentCustom extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            if (flag == FLAG_START_DATE) {
                startDate.setText(format.format(calendar.getTime()));
                String startDate = format.format(calendar.getTime()) + " 00:00";
                bundle.putString("startDate",startDate);
            } else if (flag == FLAG_END_DATE) {
                endDate.setText(format.format(calendar.getTime()));
                String endDate = format.format(calendar.getTime()) + " 23:59";
                bundle.putString("endDate",endDate);
            }
        }
    }
}
