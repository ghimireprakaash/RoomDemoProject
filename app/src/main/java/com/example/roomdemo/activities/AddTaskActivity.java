package com.example.roomdemo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdemo.R;
import com.example.roomdemo.database.DatabaseClient;
import com.example.roomdemo.entity.Task;
import com.google.android.material.snackbar.Snackbar;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editText;
    private Button saveBtn;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        constraintLayout = findViewById(R.id.addTaskConstraintLayout);

        editText = findViewById(R.id.editText);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        final String sTask = editText.getText().toString();

        if (sTask.isEmpty()){
            Snackbar.make(constraintLayout, "Field is empty...", Snackbar.LENGTH_SHORT).show();
        } else {

            //It is necessary to perform database operation in thread and not in main thread
            //If done is main thread then application will stop responding means it'll crash
            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //Creating a task
                    Task task = new Task();
                    task.setTask(sTask);

                    //adding to database
                    DatabaseClient.getInstance(getApplicationContext()).getTaskRoomDatabase()
                            .taskDao()
                            .insert(task);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    finish();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Saved...", Toast.LENGTH_SHORT).show();
                }
            }

            SaveTask task = new SaveTask();
            task.execute();
        }
    }
}