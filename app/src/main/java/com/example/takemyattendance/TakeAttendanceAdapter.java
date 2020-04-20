package com.example.takemyattendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TakeAttendanceAdapter extends ArrayAdapter<StudentBatchDbClass> {

    private static final String TAG = "TakeAttendanceAdapter";
    DataTransferInterface dtInterface;
    boolean [] checkedItems;

    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView roll;
        CheckBox presence;
    }

    public TakeAttendanceAdapter(Context context, int resource, ArrayList<StudentBatchDbClass> objects, DataTransferInterface dtInterface) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.dtInterface = dtInterface;
        checkedItems = new boolean[objects.size()];
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CompoundButton.OnCheckedChangeListener onCheckedListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                int position = (Integer) buttonView
                        .getTag(R.id.take_attendance_presence);

                if (isChecked) {
                    checkedItems[position] = true;
                } else {
                    checkedItems[position] = false;
                }

            }
        };

        //get the Class information
        String name = getItem(position).getName();
        String roll = getItem(position).getRoll();

        try{
            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            TakeAttendanceAdapter.ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new TakeAttendanceAdapter.ViewHolder();
                holder.name = convertView.findViewById(R.id.take_attendance_name);
                holder.roll = convertView.findViewById(R.id.take_attendance_roll);
                holder.presence = convertView.findViewById(R.id.take_attendance_presence);

                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (TakeAttendanceAdapter.ViewHolder) convertView.getTag();
                holder.presence.setOnCheckedChangeListener(null);
                //update the checkbox value from boolean array
                holder.presence.setChecked(checkedItems[position]);
                result = convertView;
            }

            holder.name.setText(name);
            holder.roll.setText(roll);
            holder.presence.setOnCheckedChangeListener(onCheckedListener);
            holder.presence.setTag(R.id.take_attendance_presence, position);

            dtInterface.onSetValues(checkedItems);

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }

    }



}