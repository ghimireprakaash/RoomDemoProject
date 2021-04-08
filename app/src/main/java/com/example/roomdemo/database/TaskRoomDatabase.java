package com.example.roomdemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.roomdemo.dao.TaskDao;
import com.example.roomdemo.entity.Task;

@Database(entities = Task.class, version = 1)
public abstract class TaskRoomDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
