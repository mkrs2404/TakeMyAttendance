package com.example.takemyattendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ExportDataActivity extends AppCompatActivity {

    private Intent intent;
    private Bundle bundle;
    private String subName;
    private String subCode;
    private String stream;
    private String section;
    private String batch;
    private String sem;
    private String startDate;
    private String endDate;
    private int totalClasses;
    ArrayList<ExportDataClass> exportDataClasses;
    ArrayList<ExportTopicDb> exportTopicDb;
    private double percentage;
    private static final String TAG = "ExportDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);

        intent = getIntent();
        bundle = intent.getExtras();
        subName = bundle.getString("subName");
        subCode = bundle.getString("subCode");
        stream = bundle.getString("stream");
        section = bundle.getString("section");
        batch = bundle.getString("batch");
        sem = bundle.getString("sem");
        startDate = bundle.getString("startDate");
        endDate = bundle.getString("endDate");

        startDate = startDate.replace('-' ,'.');
        endDate = endDate.replace('-' ,'.');

        Log.e(TAG,"Before fetching data " + startDate + " " + endDate + " " + subName + " " + subCode + " " + stream + " "+ section+ " " + sem+" "+ batch);
        exportDataClasses = (ArrayList<ExportDataClass>) HomeActivity.attendanceDatabase.studentDao().exportClassData(subName,subCode,stream,section,batch,sem,startDate,endDate);
        totalClasses = HomeActivity.attendanceDatabase.studentDao().getTotalClass(subName,subCode,stream,section,batch,sem,startDate,endDate);
        exportTopicDb = (ArrayList<ExportTopicDb>) HomeActivity.attendanceDatabase.studentDao().fetchTopicDb(subName,subCode,stream,section,batch,sem,startDate,endDate);
        HomeActivity.attendanceDatabase.close();
        Log.e(TAG,"After fetching data  " + exportDataClasses.size());

        for(ExportDataClass e: exportDataClasses){
            e.setTotalPeriods(totalClasses);

            try {
                percentage = ((double)e.getAttendedPeriods() / totalClasses)*100;
            }
            catch(java.lang.ArithmeticException exception){
                percentage = 0;
            }

            e.setPercentageAttended(String.format("%.2f", percentage));
        }

        for(ExportDataClass e: exportDataClasses){
            Log.e("ExportData", e.toString());
        }
        writeExcel();
    }

    public void writeExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Attendance Details");
            int rownum = 0;
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;

            Cell cell = row.createCell(cellnum++);
            cell.setCellValue("Roll No.");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Name");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Phone No.");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Email-ID");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Parent's Phone No.");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Attended Periods");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Total Periods");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Percentage");

            for (ExportDataClass exportDataClass : exportDataClasses) {
                row = sheet.createRow(rownum++);
                cellnum = 0;
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getRoll());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getName());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getPhone());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getEmail());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getParent_phone());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getAttendedPeriods());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getTotalPeriods());
                cell = row.createCell(cellnum++);
                cell.setCellValue(exportDataClass.getPercentageAttended());
            }

            sheet = workbook.createSheet("Topic Details");
            rownum = 0;
            row = sheet.createRow(rownum++);
            cellnum = 0;
            cell = row.createCell(cellnum++);
            cell.setCellValue("Date");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Topic");
            cell = row.createCell(cellnum++);
            cell.setCellValue("Class Period(s)");

            for(ExportTopicDb e:exportTopicDb){
                cellnum = 0;
                row = sheet.createRow(rownum++);
                cell = row.createCell(cellnum++);
                cell.setCellValue(e.getDate());
                cell = row.createCell(cellnum++);
                cell.setCellValue(e.getTopic());
                cell = row.createCell(cellnum++);
                cell.setCellValue(e.getPeriod());
            }


            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TakeMyAttendance");
            if(!folder.mkdirs() && !folder.exists()){
                Log.e(TAG,"Failed to create Folder.");
                Toast.makeText(this, "Failed to create Folder",Toast.LENGTH_LONG).show();
            }
            String extStorageDirectory = folder.toString();
            FileOutputStream out = new FileOutputStream(new File(extStorageDirectory, "Attendance Data.xlsx"));
            workbook.write(out);
            out.close();
            Log.e(TAG, "Attendance Data.xlsx written successfully on disk.");
            String text = "Excel file exported to TakeMyAttendance folder";
            Toast.makeText(this, text,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ExportDataActivity.this, HomeActivity.class);
            startActivity(intent);
    } catch(Exception e){
            e.printStackTrace();
        }
    }
}
