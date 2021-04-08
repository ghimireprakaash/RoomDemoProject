package com.example.roomdemo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
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

public class UpdateTaskActivity extends AppCompatActivity {
    ConstraintLayout updateConstraintLayout;
    private EditText editText;
    private Button updateBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        updateConstraintLayout = findViewById(R.id.updateConstraintLayout);
        editText = findViewById(R.id.updateText);

        final Task task = (Task) getIntent().getSerializableExtra("task");

        loadTask(task);

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(v -> updateTask(task));

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?");
            builder.setPositiveButton("Yes", (dialog, which) -> deleteBtn(task));

            builder.setNegativeButton("No", (dialog, which) -> {

            });

            builder.create().show();

        });
    }

    private void loadTask(Task task) {
        editText.setText(task.getTask());
    }

    private void updateTask(Task task) {
        final String updateText = editText.getText().toString();

        if (updateText.isEmpty()){
            Snackbar.make(updateConstraintLayout, "Field can't be left empty.", Snackbar.LENGTH_SHORT).show();
        } else {

            class UpdateTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    task.setTask(updateText);

                    //Updating task in database...
                    DatabaseClient.getInstance(getApplicationContext())
                            .getTaskRoomDatabase()
                            .taskDao()
                            .update(task);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_SHORT).show();

                    finish();

                    startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
                }
            }

            UpdateTask updateTask = new UpdateTask();
            updateTask.execute();
        }
    }


    private void deleteBtn(Task task) {
        class DeleteTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext())
                        .getTaskRoomDatabase()
                        .taskDao()
                        .delete(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                finish();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute();
    }
}