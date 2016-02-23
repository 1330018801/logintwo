package com.seimun.logintwo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.seimun.logintwo.R;
import com.seimun.logintwo.app.AppData;
import com.seimun.logintwo.model.Education;

public class EducationDetail extends AppCompatActivity {
    private static final String TAG = EducationDetail.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationDetail.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        final Integer position = intent.getIntExtra("position", 0);
        Education education = AppData.educationList.get(position);


        TextView title = (TextView) findViewById(R.id.edu_detail_title);
        if (title != null) {
            title.setText(education.getTitle());
        } else {
            Log.d("TEST", education.getTitle());
        }

        TextView content = (TextView) findViewById(R.id.edu_detail_content);
        if (content != null) {
            content.setText(education.getContent());
        } else {
            Log.d("TEST", education.getContent());
        }

        TextView create_at = (TextView) findViewById(R.id.edu_detail_create_at);

        if (create_at != null) {
            create_at.setText(education.getCreateAt());
        } else {
            Log.d("TEST", education.getCreateAt());
        }

    }
}
 