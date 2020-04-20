package com.example.takemyattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String username;
    private String imageUrl;
    private static final String TAG = "MainActivity";
    private ListView mListView;
    public static AttendanceDatabase attendanceDatabase;
    ArrayList<ClassData> classDataArrayList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.lstview);

        attendanceDatabase = Room.databaseBuilder(getApplicationContext(),AttendanceDatabase.class, "studentdb").allowMainThreadQueries().build();
        classDataArrayList = (ArrayList<ClassData>) attendanceDatabase.studentDao().getClassData();
        try {
            Intent intent = getIntent();
            username = intent.getStringExtra("name");
            imageUrl = intent.getStringExtra("image_url");
        }
        catch(Exception e){
            Log.e(TAG,String.valueOf(e));
        }
        fab = findViewById(R.id.fab);

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.card_layout, classDataArrayList);
        mListView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddClassActivity.class);
                startActivity(intent);
            }
        });
    }

}
