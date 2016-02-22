package com.seimun.logintwo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.R;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Aftercare3To6YearActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();


    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftercare3_to6_year);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aftercare3To6YearActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });


        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // final HashMap<String, String> user = db.getUserDetails();


        Intent intent = getIntent();
        final Integer record_id = intent.getIntExtra("record_id", 0);


        if (record_id != 0) {
            Log.e(TAG, "开始从后台获取详情");
            final StringRequest detailReq = new StringRequest(
                    Request.Method.POST,
                    AppConfig.URL_DETAIL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (!obj.getBoolean("error")) {
                                    JSONObject detail = obj.getJSONObject("detail");
                                    // Toast.makeText(getApplicationContext(), detail.getString("visit_date"), Toast.LENGTH_SHORT).show();
                                    TextView visit_date = (TextView) findViewById(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView doctor_signature = (TextView) findViewById(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView pneumonia = (TextView) findViewById(R.id.pneumonia);
                                    pneumonia.setText(detail.getString("pneumonia"));
                                    TextView diarrhea = (TextView) findViewById(R.id.diarrhea);
                                    diarrhea.setText(detail.getString("diarrhea"));
                                    TextView traumatism = (TextView) findViewById(R.id.traumatism);
                                    traumatism.setText(detail.getString("traumatism"));
                                    TextView two_visit_extra = (TextView) findViewById(R.id.two_visit_extra);
                                    two_visit_extra.setText(detail.getString("two_visit_extra"));
                                    TextView guide = (TextView) findViewById(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView guide_extra = (TextView) findViewById(R.id.guide_extra);
                                    guide_extra.setText(detail.getString("guide_extra"));
                                    TextView transfer_treatment_suggestion = (TextView) findViewById(R.id.transfer_treatment_suggestion);
                                    transfer_treatment_suggestion.setText(detail.getString("transfer_treatment_suggestion"));
                                    TextView transfer_treatment_suggestion_reason = (TextView) findViewById(R.id.transfer_treatment_suggestion_reason);
                                    transfer_treatment_suggestion_reason.setText(detail.getString("transfer_treatment_suggestion_reason"));
                                    TextView next_visit_date = (TextView) findViewById(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView transfer_treatment_suggestion_institution = (TextView) findViewById(R.id.transfer_treatment_suggestion_institution);
                                    transfer_treatment_suggestion_institution.setText(detail.getString("transfer_treatment_suggestion_institution"));
                                    TextView weight = (TextView) findViewById(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView weight_grade = (TextView) findViewById(R.id.weight_grade);
                                    weight_grade.setText(detail.getString("weight_grade"));
                                    TextView height = (TextView) findViewById(R.id.height);
                                    height.setText(detail.getString("height"));
                                    TextView height_grade = (TextView) findViewById(R.id.height_grade);
                                    height_grade.setText(detail.getString("height_grade"));
                                    TextView body_growth_evaluate = (TextView) findViewById(R.id.body_growth_evaluate);
                                    body_growth_evaluate.setText(detail.getString("body_growth_evaluate"));
                                    TextView tooth = (TextView) findViewById(R.id.tooth);
                                    tooth.setText(detail.getString("tooth"));
                                    TextView decayed_tooth = (TextView) findViewById(R.id.decayed_tooth);
                                    decayed_tooth.setText(detail.getString("decayed_tooth"));
                                    TextView heart_lung = (TextView) findViewById(R.id.heart_lung);
                                    heart_lung.setText(detail.getString("heart_lung"));
                                    TextView abdomen = (TextView) findViewById(R.id.abdomen);
                                    abdomen.setText(detail.getString("abdomen"));
                                    TextView hemoglobin_value = (TextView) findViewById(R.id.hemoglobin_value);
                                    hemoglobin_value.setText(detail.getString("hemoglobin_value"));
                                    TextView extra = (TextView) findViewById(R.id.extra);
                                    extra.setText(detail.getString("extra"));


                                } else {
                                    String errorMsg = obj.getString("error_msg");
                                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("record_id", Integer.toString(record_id));
                    Log.e(TAG, "to get record_id: " + record_id);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(detailReq);
        } else {
            Toast.makeText(getApplicationContext(), "没有获得记录ID", Toast.LENGTH_LONG).show();
        }
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        Intent intent = new Intent(Aftercare3To6YearActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}

