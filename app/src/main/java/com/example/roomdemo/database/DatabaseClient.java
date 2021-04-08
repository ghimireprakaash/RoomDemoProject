package com.example.roomdemo.database;

import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;

    //Room Database Object
    private TaskRoomDatabase taskRoomDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        taskRoomDatabase = Room.databaseBuilder(context, TaskRoomDatabase.class, "MyToDos").build();
    }

    public static synchronized DatabaseClient getInstance(Context mContext){
        if (mInstance == null){
            mInstance = new DatabaseClient(mContext);
        }
        return mInstance;
    }

    public TaskRoomDatabase getTaskRoomDatabase(){
        return taskRoomDatabase;
    }
}
