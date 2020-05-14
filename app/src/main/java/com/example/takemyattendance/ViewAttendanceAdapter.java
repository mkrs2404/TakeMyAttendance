package com.example.takemyattendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ViewAttendanceAdapter extends ArrayAdapter<StudentBatchDbClass> {

    private static final String TAG = "ViewAttendanceAdapter";
    private Context mContext;
    private int mResource;

    public ViewAttendanceAdapter(@NonNull Context context, int resource, @NonNull List<StudentBatchDbClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    private static class ViewHolder {
        TextView name;
        TextView roll;
        TextView classesAttended;
        TextView percentageAttended;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the Class information
        String name = getItem(position).getName();
        String roll = getItem(position).getRoll();
        int attendedClasses = getItem(position).getAttendedClasses();
        int totalClasses = getItem(position).getTotalClasses();
        double percentage;
        try {
            percentage = ((double)attendedClasses / totalClasses)*100;
        }
        catch(java.lang.ArithmeticException e){
            percentage = 0;
        }

        try{
            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            ViewAttendanceAdapter.ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new ViewAttendanceAdapter.ViewHolder();
                holder.name = convertView.findViewById(R.id.view_attendance_name);
                holder.roll = convertView.findViewById(R.id.view_attendance_roll);
                holder.classesAttended = convertView.findViewById(R.id.view_attendance_classes);
                holder.percentageAttended = convertView.findViewById(R.id.view_attendance_percentage);

                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (ViewAttendanceAdapter.ViewHolder) convertView.getTag();
                result = convertView;
            }

            holder.name.setText(name);
            holder.roll.setText(roll);
            holder.classesAttended.setText(String.valueOf(attendedClasses));
            holder.percentageAttended.setText(String.format("%.2f", percentage));

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }

    }


}
