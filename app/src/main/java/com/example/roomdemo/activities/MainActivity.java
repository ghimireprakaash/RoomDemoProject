package com.example.roomdemo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.example.roomdemo.R;
import com.example.roomdemo.adapter.TaskAdapter;
import com.example.roomdemo.database.DatabaseClient;
import com.example.roomdemo.entity.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fabBtn);
        fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddTaskActivity.class)));

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        getTask();
    }

    private void getTask() {
        class GetTask extends AsyncTask<Void, Void, List<Task>>{

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> listTask = DatabaseClient.getInstance(getApplicationContext())
                        .getTaskRoomDatabase()
                        .taskDao()
                        .getAll();

                return listTask;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                TaskAdapter adapter = new TaskAdapter(getApplicationContext(), tasks);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTask getTask = new GetTask();
        getTask.execute();
    }
}