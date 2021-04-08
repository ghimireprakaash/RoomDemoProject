package com.example.roomdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdemo.R;
import com.example.roomdemo.activities.UpdateTaskActivity;
import com.example.roomdemo.entity.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> taskList;


    public TaskAdapter(Context mContext, List<Task> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tasks_layout, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task getPosition = taskList.get(position);

        holder.textViewTask.setText(getPosition.getTask());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }



    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTask;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mContext, UpdateTaskActivity.class);
            intent.putExtra("task", task);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }
    }
}
