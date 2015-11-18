package com.seimun.logintwo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seimun.logintwo.R;
import com.seimun.logintwo.model.Summary;

import java.util.List;

public class SummaryListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Summary> summaryItems;

    public SummaryListAdapter(Activity activity, List<Summary> summaryItems) {
        this.activity = activity;
        this.summaryItems = summaryItems;
    }

    @Override
    public int getCount() {
        return summaryItems.size();
    }

    @Override
    public Object getItem(int location) {
        return summaryItems.get(location);
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
            convertView = inflater.inflate(R.layout.service_summary, null);
        TextView title = (TextView)convertView.findViewById(R.id.service_title);
        TextView clinic = (TextView)convertView.findViewById(R.id.clinic);
        TextView provider = (TextView)convertView.findViewById(R.id.provider);
        TextView service_time = (TextView)convertView.findViewById(R.id.service_time);

        Summary s = summaryItems.get(position);
        title.setText(s.getTitle());
        clinic.setText(s.getClinic());
        provider.setText(s.getProvider());
        service_time.setText("服务时间：" + s.getServiceTime());

        return convertView;
    }

}
