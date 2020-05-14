package com.example.takemyattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewAttendanceActivity extends AppCompatActivity {

    Bundle bundle;
    Intent intent;
    private String subName;
    private String subCode;
    private String batch;
    private String sem;
    private String stream;
    private String section;
    private ArrayList<StudentBatchDbClass> attendanceList;
    ListView mListView;
    TextView totalClassesTextView;
    private final String TAG = "ViewAttendanceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        mListView = findViewById(R.id.view_attendance_listview);
        totalClassesTextView = findViewById(R.id.totalClasses);
        bundle = getIntent().getExtras();
        subName = bundle.getString("subName");
        subCode = bundle.getString("subCode");
        batch = bundle.getString("batch");
        sem = bundle.getString("sem");
        stream = bundle.getString("stream");
        section = bundle.getString("section");
        Log.e(TAG,subName+subCode+stream+section+batch+sem);
        attendanceList = (ArrayList<StudentBatchDbClass>) HomeActivity.attendanceDatabase.studentDao().loadStudents(subName,subCode,stream,section,batch,sem);
        HomeActivity.attendanceDatabase.close();
        Log.e(TAG,String.valueOf(attendanceList.size()));
        totalClassesTextView.setText("Total Classes : " + attendanceList.get(0).getTotalClasses());
        ViewAttendanceAdapter adapter = new ViewAttendanceAdapter(ViewAttendanceActivity.this, R.layout.view_attendance_card, attendanceList);
        mListView.setAdapter(adapter);


    }
}
