package com.example.takemyattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity implements CustomButtonListener{

    private String username;
    private Uri imageUrl;
    private String fullName;
    private String email;
    private static final String TAG = "HomeActivity";
    private static boolean backupModified = false;
    private ListView mListView;
    public static AttendanceDatabase attendanceDatabase;
    ArrayList<ClassData> classDataArrayList;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPrefs;
    CustomListAdapter adapter;
    Drive googleDriveService;
    private FileList files;
    private String lastBackupId = "";
    private boolean backupFound = true;
    boolean isConnected;


    class CheckExistenceOfBackup extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            try{
                files = googleDriveService.files().list()
                        .setSpaces("appDataFolder")
                        .setFields("nextPageToken, files(id, name, createdTime)")
                        .setPageSize(10)
                        .execute();

                for(File file: files.getFiles()){
                    Log.e(TAG, file.getName() + " " + file.getId());
                    if(file.getId().equals(lastBackupId))
                        backupFound = false;
                }
                Log.e(TAG,"LastBackupId: "+lastBackupId + " " + "BackupFound: "+ backupFound);
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Log.e(TAG, "Files in AppData Folder: " + files.getFiles().size());
            sharedPreferences.edit().putString("appFolderSize",String.valueOf(files.getFiles().size())).apply();
            if (backupFound && files.getFiles().size() != 0) {
                        DialogFragment newFragment = new BackupModifiedDialogFragment();
                        newFragment.show(getSupportFragmentManager(), "backupDialogModified");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckInternetConnection ck = new CheckInternetConnection(this);
        isConnected = ck.isConnected();
        if(isConnected) {
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(Scopes.DRIVE_APPFOLDER));
            credential.setSelectedAccount(googleSignInAccount.getAccount());
            googleDriveService = new Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    credential)
                    .setApplicationName(getString(R.string.app_name))
                    .build();
            sharedPreferences = getSharedPreferences("backupId", Context.MODE_PRIVATE);
            lastBackupId = sharedPreferences.getString("lastBackupId", "");
            new CheckExistenceOfBackup().execute();
        }

        sharedPrefs = HomeActivity.this.getSharedPreferences("googleData",Context.MODE_PRIVATE);
        mListView = findViewById(R.id.lstview);
        attendanceDatabase = Room.databaseBuilder(getApplicationContext(),AttendanceDatabase.class, "studentdb").allowMainThreadQueries().build();
        classDataArrayList = (ArrayList<ClassData>) attendanceDatabase.studentDao().getClassData();
        attendanceDatabase.close();
        try {
            Intent intent = getIntent();
            username = intent.getStringExtra("name");
            imageUrl = (Uri) intent.getExtras().get("image");
            fullName = intent.getStringExtra("fullname");
            email = intent.getStringExtra("email");

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("image",imageUrl.toString());
            editor.putString("fullname", fullName);
            editor.putString("email",email);
            editor.apply();

        }
        catch(Exception e){
            Log.e(TAG,String.valueOf(e));
        }
        fab = findViewById(R.id.fab);

        adapter = new CustomListAdapter(this, R.layout.card_layout, classDataArrayList,this);
        mListView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddClassActivity.class);
            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        imageUrl = Uri.parse(sharedPrefs.getString("image",""));
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
        attendanceDatabase.studentDao().deleteStudentBatchDbClass(subName, subCode, stream, section, batch, sem);
        attendanceDatabase.studentDao().deleteClassData(subName, subCode, stream, section, batch, sem);
        attendanceDatabase.studentDao().deleteTopicDbClass(subName, subCode, stream, section, batch, sem);
        attendanceDatabase.close();
        classDataArrayList.clear();
        classDataArrayList.addAll(attendanceDatabase.studentDao().getClassData());
        adapter = new CustomListAdapter(this, R.layout.card_layout, classDataArrayList,this);
        mListView.setAdapter(adapter);
        Toast.makeText(this,"Batch Successfully Deleted.",Toast.LENGTH_SHORT).show();
    }
}
