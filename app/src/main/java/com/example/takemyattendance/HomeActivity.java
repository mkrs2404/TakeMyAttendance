package com.example.takemyattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements CustomButtonListener{

    private String username;
    private Uri imageUrl;
    private String fullName;
    private String email;
    private static final String TAG = "HomeActivity";
    private ListView mListView;
    public static AttendanceDatabase attendanceDatabase;
    ArrayList<ClassData> classDataArrayList;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = HomeActivity.this.getSharedPreferences("googleData",Context.MODE_PRIVATE);
        mListView = findViewById(R.id.lstview);
        attendanceDatabase = Room.databaseBuilder(getApplicationContext(),AttendanceDatabase.class, "studentdb").allowMainThreadQueries().build();
        classDataArrayList = (ArrayList<ClassData>) attendanceDatabase.studentDao().getClassData();
        try {
            Intent intent = getIntent();
            username = intent.getStringExtra("name");
            imageUrl = (Uri) intent.getExtras().get("image");
            fullName = intent.getStringExtra("fullname");
            email = intent.getStringExtra("email");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("image",imageUrl.toString());
            editor.putString("fullname", fullName);
            editor.putString("email",email);
            editor.commit();

        }
        catch(Exception e){
            Log.e(TAG,String.valueOf(e));
        }
        fab = findViewById(R.id.fab);

        adapter = new CustomListAdapter(this, R.layout.card_layout, classDataArrayList,this);
        mListView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddClassActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        imageUrl = Uri.parse(sharedPreferences.getString("image",""));
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.ic_account_circle_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        menu.getItem(0).setIcon(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                Bundle bundle = new Bundle();
                DialogFragment newFragment = new ProfileDialogFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(),"profileDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onButtonClickListener(String subName, String subCode, String stream, String section, String batch, String sem) {
        Log.e(TAG,subName+subCode+stream+section+batch+sem);
        attendanceDatabase.studentDao().deleteStudentBatchDbClass(subName, subCode, stream, section, batch, sem);
        attendanceDatabase.studentDao().deleteClassData(subName, subCode, stream, section, batch, sem);
        attendanceDatabase.studentDao().deleteTopicDbClass(subName, subCode, stream, section, batch, sem);
        classDataArrayList.clear();
        classDataArrayList = (ArrayList<ClassData>) attendanceDatabase.studentDao().getClassData();
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"Batch Successfully Deleted.",Toast.LENGTH_SHORT).show();
    }
}
