package com.example.takemyattendance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void addBatch(ArrayList<StudentBatchDbClass> attendanceData);

    @Insert
    void addClass(ClassData classData);

    @Insert
    void addClassForToday(TopicDbClass topicData);

    @Insert
    void addClassToDailyAttendance(ArrayList<DailyAttendanceDbClass> dailyAttendanceDbClass);

    @Query("Select * from ClassData")
    List<ClassData> getClassData();

    @Query("Select * from StudentBatchDbClass where subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    List<StudentBatchDbClass> loadStudents(String subName, String subCode, String stream, String section, String batch, String sem);

    @Query("SELECT date, topic, period FROM TopicDbClass WHERE date <= :endDate and date >= :startDate and subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    List<ExportTopicDb> fetchTopicDb(String subName, String subCode, String stream, String section, String batch, String sem, String startDate, String endDate);

    @Update
    void updateAttendanceData(ArrayList<StudentBatchDbClass> attendanceData);

    @Query("DELETE FROM ClassData WHERE subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    void deleteClassData(String subName, String subCode, String stream, String section, String batch, String sem);

    @Query("DELETE FROM StudentBatchDbClass WHERE subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    void deleteStudentBatchDbClass(String subName, String subCode, String stream, String section, String batch, String sem);

    @Query("DELETE FROM TopicDbClass WHERE subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    void deleteTopicDbClass(String subName, String subCode, String stream, String section, String batch, String sem);

    @Query("SELECT roll,name,phone,email,parent_phone,SUM(attendedPeriods) as attendedPeriods, totalPeriods FROM DailyAttendanceDbClass WHERE date <= :endDate and date >= :startDate and subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem GROUP BY roll")
    List<ExportDataClass> exportClassData(String subName, String subCode, String stream, String section, String batch, String sem, String startDate, String endDate);

    @Query("Select SUM(period) from TopicDbClass WHERE date <= :endDate and date >= :startDate and subName = :subName and subCode = :subCode and stream = :stream and section = :section and batch = :batch and sem = :sem")
    int getTotalClass(String subName, String subCode, String stream, String section, String batch, String sem, String startDate, String endDate);
}
