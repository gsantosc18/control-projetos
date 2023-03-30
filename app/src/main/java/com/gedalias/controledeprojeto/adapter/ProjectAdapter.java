package com.gedalias.controledeprojeto.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.domain.Project;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ProjectAdapter extends BaseAdapter {
    private List<Project> projects;
    private final Context context;

    public ProjectAdapter(Context context, List<Project> projects) {
        this.projects = projects;
        this.context = context;
    }

    public void reloadData(final List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.project_item_view, parent, false);

            holder = new ProjectHolder();

            holder.name = (TextView) convertView.findViewById(R.id.projectItemName);
            holder.description = (TextView) convertView.findViewById(R.id.projectItemDescription);
            holder.duration = (TextView) convertView.findViewById(R.id.projectItemDuration);
            holder.type = (TextView) convertView.findViewById(R.id.projectItemType);

            convertView.setTag(holder);
        } else {
            holder = (ProjectHolder) convertView.getTag();
        }

        Project project = projects.get(position);

        holder.name.setText(project.getName());
        holder.description.setText(project.getDescription());
        holder.duration.setText(String.format("%d(%s)", project.getDuration(),project.getTimeOption().getTimeOption()));
        holder.type.setText(project.getType().getType());

        return convertView;
    }

    private static class ProjectHolder {
        public TextView name;
        public TextView description;
        public TextView duration;
        public TextView type;
    }
}
