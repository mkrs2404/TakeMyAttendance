package com.example.takemyattendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class CustomListAdapter  extends ArrayAdapter<ClassData> {

//    customButtonListener customListener;
//
//    public interface customButtonListener {
//        public void onButtonClickListener(int position,String value);
//    }
//
//    public void setCustomButtonListener(customButtonListener listener) {
//        this.customListener = listener;
//    }

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView subName;
        TextView subCode;
        TextView stream;
        TextView sem;
        TextView batch;
        TextView section;
        Button takeAttendance;
        Button viewAttendance;
    }

    public CustomListAdapter(Context context, int resource, ArrayList<ClassData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //get the Class information
        final String subName = getItem(position).getSubName();
        final String subCode = getItem(position).getSubCode();
        final String stream = getItem(position).getStream();
        final String sem = getItem(position).getSem();
        final String section = getItem(position).getSection();
        final String batch = getItem(position).getBatch();

        try{
            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            final ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new ViewHolder();
                holder.subName = convertView.findViewById(R.id.subName);
                holder.subCode = convertView.findViewById(R.id.subCode);
                holder.stream = convertView.findViewById(R.id.stream);
                holder.sem = convertView.findViewById(R.id.sem);
                holder.section = convertView.findViewById(R.id.section);
                holder.batch = convertView.findViewById(R.id.batch);
                holder.takeAttendance = convertView.findViewById(R.id.takeAttendance);
                holder.viewAttendance = convertView.findViewById(R.id.viewAttendance);

                holder.takeAttendance.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Intent intent = new Intent(mContext,TakeAttendanceActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("subName", subName);
                        bundle.putString("subCode", subCode);
                        bundle.putString("batch", batch);
                        bundle.putString("sem", sem);
                        bundle.putString("stream", stream);
                        bundle.putString("section", section);
                        DialogFragment newFragment = new TopicDialog();
                        FragmentActivity activity = (FragmentActivity)(mContext);
                        FragmentManager fm = activity.getSupportFragmentManager();
                        newFragment.setArguments(bundle);
                        newFragment.show(fm,"topicdialog");
                        //mContext.startActivity(intent);

                    }
                });

                holder.viewAttendance.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("subName", subName);
                        bundle.putString("subCode", subCode);
                        bundle.putString("batch", batch);
                        bundle.putString("sem", sem);
                        bundle.putString("stream", stream);
                        bundle.putString("section", section);
                        Intent intent = new Intent(mContext,ViewAttendanceActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                        //Toast.makeText(getContext(), "View Attendance from row " + position + " was pressed", Toast.LENGTH_LONG).show();
                    }
                });

                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            holder.subName.setText(subName);
            holder.subCode.setText(subCode);
            holder.sem.setText(sem);
            holder.stream.setText(stream);
            holder.section.setText(section);
            holder.batch.setText(batch);

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }

    }

}