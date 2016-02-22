package com.seimun.logintwo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seimun.logintwo.R;
import com.seimun.logintwo.model.Education;

import java.util.List;


public class EducationListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Education> educationItems;

    public EducationListAdapter(Activity activity, List<Education> educationItems) {
        this.activity = activity;
        this.educationItems = educationItems;
    }

    @Override
    public int getCount() {
        return educationItems.size();
    }

    @Override
    public Object getItem(int location) {
        return educationItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.education_item, null);
        TextView title = (TextView)convertView.findViewById(R.id.education_title);
        TextView content = (TextView)convertView.findViewById(R.id.education_content);

        Education s = educationItems.get(position);
        title.setText(s.getTitle());
        content.setText(s.getContent());

        return convertView;
    }
}
