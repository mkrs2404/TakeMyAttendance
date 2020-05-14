package com.example.takemyattendance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StudentBatchDbClass.class, ClassData.class, TopicDbClass.class, DailyAttendanceDbClass.class}, version = 1)
public abstract class AttendanceDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
}
