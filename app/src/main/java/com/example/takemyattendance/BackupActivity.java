package com.example.takemyattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class BackupActivity extends AppCompatActivity {

    private GoogleSignInAccount googleSignInAccount;
    SharedPreferences sharedPreferences;
    private static Drive googleDriveService;
    private static String  lastBackupId = "";
    private static String  lastBackupIdShm = "";
    private static String  lastBackupIdWal = "";
    private String dbPath = "/data/data/com.example.takemyattendance/databases/studentdb";
    private String dbPathShm = "/data/data/com.example.takemyattendance/databases/studentdb-shm";
    private String dbPathWal = "/data/data/com.example.takemyattendance/databases/studentdb-wal";
    private static boolean backupModified = false;
    private static String appFolderSize;
    private static boolean backupFound = true;

    private static String TAG= "BACKUPACTIVITY";

    class UploadBackup extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... arg0)
        {
            upload();
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Toast.makeText(BackupActivity.this,"Uploaded Successfully",Toast.LENGTH_SHORT).show();
            if(backupModified)
                new Download().execute();
            else{
                Intent intent = new Intent(BackupActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }
    }

    class Download extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... arg0)
        {
            Download();
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Toast.makeText(BackupActivity.this,"Downloaded Successfully",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BackupActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        this.setFinishOnTouchOutside(false);

        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(Scopes.DRIVE_FILE));
        credential.setSelectedAccount(googleSignInAccount.getAccount());
        googleDriveService = new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                credential)
                .setApplicationName(getString(R.string.app_name))
                .build();
        try{
            sharedPreferences = getSharedPreferences("backupId", Context.MODE_PRIVATE);
            lastBackupId = sharedPreferences.getString("lastBackupId","");
            lastBackupIdShm = sharedPreferences.getString("lastBackupIdShm","");
            lastBackupIdWal = sharedPreferences.getString("lastBackupIdWal","");
            appFolderSize = sharedPreferences.getString("appFolderSize","0");
            Log.e(TAG,"App Folder Size: " + appFolderSize);


        }catch(Exception e){
            Log.e(TAG,"First Run. Shared Pref not found");
        }

        new UploadBackup().execute();

    }

    public static boolean isBackupModified(){
        try{
            FileList fileList = googleDriveService.files().list()
                    .setSpaces("appDataFolder")
                    .setFields("nextPageToken, files(id, name, createdTime)")
                    .setPageSize(10)
                    .execute();
            Log.e(TAG,"LastBackupId: "+lastBackupId);
            for(File file: fileList.getFiles()){
                Log.e(TAG, file.getName() + " " + file.getId());
                if(file.getId().equals(lastBackupId))
                    backupFound = false;
            }
            Log.e("BACKUPACTIVITY","BackupFound: " + backupFound);
            if(backupFound){
                return !lastBackupId.equals("") || !appFolderSize.equals("0");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    private void upload(){
        File storageFile = new File();
        storageFile.setParents(Collections.singletonList("appDataFolder"));
        storageFile.setName("studentdb");

        File storageFileShm = new File();
        storageFileShm.setParents(Collections.singletonList("appDataFolder"));
        storageFileShm.setName("studentdb-shm");

        File storageFileWal = new File();
        storageFileWal.setParents(Collections.singletonList("appDataFolder"));
        storageFileWal.setName("studentdb-wal");

        java.io.File filePath = new java.io.File(dbPath);
        java.io.File filePathShm = new java.io.File(dbPathShm);
        java.io.File filePathWal = new java.io.File(dbPathWal);
        FileContent mediaContent = new FileContent("",filePath);
        FileContent mediaContentShm = new FileContent("",filePathShm);
        FileContent mediaContentWal = new FileContent("",filePathWal);
        try {
            /** To check if backup was modified from other device **/
            backupModified = isBackupModified();
            Log.e(TAG,"BackupModified" + backupModified);
            /** To remove previous backup **/
            if(!backupModified) {
                Log.e(TAG,"Inside Upload");
                if (!lastBackupId.equals("")) {
                    googleDriveService.files().delete(lastBackupId).execute();
                }
                if (!lastBackupIdShm.equals(""))
                    googleDriveService.files().delete(lastBackupIdShm).execute();
                if (!lastBackupIdWal.equals(""))
                    googleDriveService.files().delete(lastBackupIdWal).execute();

                File file = googleDriveService.files().create(storageFile, mediaContent)
                        //                    .setFields("nextPageToken, files(id, name)")
                        .execute();
                System.out.printf("Filename: %s File ID: %s \n", file.getName(), file.getId());

                File fileShm = googleDriveService.files().create(storageFileShm, mediaContentShm)
                        //                    .setFields("nextPageToken, files(id, name)")
                        .execute();
                System.out.printf("Filename: %s File ID: %s \n", fileShm.getName(), fileShm.getId());

                File fileWal = googleDriveService.files().create(storageFileWal, mediaContentWal)
                        //                    .setFields("nextPageToken, files(id, name)")
                        .execute();
                System.out.printf("Filename: %s File ID: %s \n", fileWal.getName(), fileWal.getId());
                //
                sharedPreferences.edit().putString("lastBackupId", file.getId()).apply();
                sharedPreferences.edit().putString("lastBackupIdShm", fileShm.getId()).apply();
                sharedPreferences.edit().putString("lastBackupIdWal", fileWal.getId()).apply();
            }
        }
        catch(UserRecoverableAuthIOException e){
            startActivityForResult(e.getIntent(), 1);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void Download(){
        try {
            HomeActivity.attendanceDatabase.close();
            java.io.File dir = new java.io.File("/data/data/com.example.takemyattendance/databases");
            if (dir.isDirectory())
            {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new java.io.File(dir, children[i]).delete();
                }
            }

            FileList files = googleDriveService.files().list()
                    .setSpaces("appDataFolder")
                    .setFields("nextPageToken, files(id, name, createdTime)")
                    .setPageSize(10)
                    .execute();
            if(files.getFiles().size() == 0)
                Log.e(TAG,"No DB file exists in Drive");
            for (File file : files.getFiles()) {
                System.out.printf("Found file: %s (%s) %s\n",
                        file.getName(), file.getId(), file.getCreatedTime());
                if(file.getName().equals("studentdb")){
                    OutputStream outputStream = new FileOutputStream(dbPath);
                    googleDriveService.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
                    sharedPreferences.edit().putString("lastBackupId", file.getId()).apply();
                }
                else if(file.getName().equals("studentdb-shm")){
                    OutputStream outputStream = new FileOutputStream(dbPathShm);
                    googleDriveService.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
                    sharedPreferences.edit().putString("lastBackupIdShm", file.getId()).apply();
                }
                else if(file.getName().equals("studentdb-wal")){
                    OutputStream outputStream = new FileOutputStream(dbPathWal);
                    googleDriveService.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
                    sharedPreferences.edit().putString("lastBackupIdWal", file.getId()).apply();
                }
            }
//            OutputStream outputStream = new FileOutputStream(dbPath);
//            googleDriveService.files().get(newFileId).executeMediaAndDownloadTo(outputStream);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
