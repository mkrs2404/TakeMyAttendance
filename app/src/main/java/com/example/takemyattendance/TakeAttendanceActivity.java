package com.example.takemyattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TakeAttendanceActivity extends AppCompatActivity implements DataTransferInterface{

    ArrayList<StudentBatchDbClass> studentList;
    ArrayList<StudentBatchDbClass> updatedAttendanceData = new ArrayList<>();
    boolean[] attendanceRecord;
    private ListView mListView;
    Bundle bundle;
    Intent intent;
    private String subName;
    private String subCode;
    private String batch;
    private String sem;
    private String stream;
    private String section;
    private String topic;
    private String period;
    private String currentDateandTime;
    int i = 0;
    int classes = 0;
    int totalClasses;
    int attendedClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        mListView = findViewById(R.id.take_attendance_listview);
        bundle = getIntent().getExtras();
        subName = bundle.getString("subName");
        subCode = bundle.getString("subCode");
        batch = bundle.getString("batch");
        sem = bundle.getString("sem");
        stream = bundle.getString("stream");
        section = bundle.getString("section");
        topic = bundle.getString("topic");
        period = bundle.getString("period");
        studentList = (ArrayList<StudentBatchDbClass>) HomeActivity.attendanceDatabase.studentDao().loadStudents(subName,subCode,stream,section,batch,sem);

        TakeAttendanceAdapter adapter = new TakeAttendanceAdapter(TakeAttendanceActivity.this, R.layout.take_attendance_card, studentList,this);
        mListView.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public void onSetValues(boolean[] al) {
        attendanceRecord = al;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save:
                for(boolean b:attendanceRecord){
                    Log.e("TakeAttendanceActivity", String.valueOf(b));
                }
               // attendedAndTotalClasses = (ArrayList<Integer>) HomeActivity.attendanceDatabase.studentDao().loadTotalAndAttendedClasses(subName, subCode, stream, section, batch, sem);
                for(StudentBatchDbClass student:studentList){
                    classes = 0;
                    if(attendanceRecord[i] == true)
                        classes = Integer.parseInt(period);
                    attendedClasses = student.getAttendedClasses();
                    totalClasses = student.getTotalClasses();
                    updatedAttendanceData.add(new StudentBatchDbClass(subName, subCode, stream, section, batch, sem, student.getRoll(),student.getName(), student.getPhone(),student.getEmail(),student.getParent_phone(),attendedClasses + classes, totalClasses + Integer.parseInt(period)));
                    i++;
                }
                HomeActivity.attendanceDatabase.studentDao().updateAttendanceData(updatedAttendanceData);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                currentDateandTime = sdf.format(new Date());
                HomeActivity.attendanceDatabase.studentDao().addClassForToday(new TopicDbClass(subName, subCode, stream, section, batch, sem, currentDateandTime, topic, period));
                intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(this,"Attendance Record saved.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
