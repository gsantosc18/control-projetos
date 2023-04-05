package com.gedalias.controledeprojeto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.domain.Task;

import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private List<Task> tasks;
    private final Context context;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_item_view, parent, false);

            holder = new TaskHolder();
            holder.name = (TextView) convertView.findViewById(R.id.taskName);
            holder.status = (TextView) convertView.findViewById(R.id.taskStatus);
            convertView.setTag(holder);
        } else {
            holder = (TaskHolder) convertView.getTag();
        }

        Task task = tasks.get(position);
        holder.name.setText(task.getName());
        holder.status.setText(task.getStatus().value);

        return convertView;
    }

    public static class TaskHolder {
        public TextView name;
        public TextView status;
    }
}
