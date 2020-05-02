package com.example.takemyattendance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    public void addBatch(ArrayList<StudentBatchDbClass> attendanceData);

    @Insert
    public void addClass(ClassData classData);

    @Insert
    public void addClassForToday(TopicDbClass topicData);

    @Query("Select * from ClassData")
    public List<ClassData> getClassData();

    @Query("Select * from StudentBatchDbClass where subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    public List<StudentBatchDbClass> loadStudents(String subName, String subCode, String stream, String section, String batch, String sem);

    @Update
    public void updateAttendanceData(ArrayList<StudentBatchDbClass> attendanceData);

    @Query("DELETE FROM ClassData WHERE subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    public void deleteClassData(String subName, String subCode, String stream, String section, String batch, String sem);

    @Query("DELETE FROM StudentBatchDbClass WHERE subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    public void deleteStudentBatchDbClass(String subName, String subCode, String stream, String section, String batch, String sem);

    @Query("DELETE FROM TopicDbClass WHERE subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    public void deleteTopicDbClass(String subName, String subCode, String stream, String section, String batch, String sem);


}
