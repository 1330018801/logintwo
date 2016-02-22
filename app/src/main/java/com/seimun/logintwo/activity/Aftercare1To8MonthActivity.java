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

public class Aftercare1To8MonthActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftercare1_to8_month);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aftercare1To8MonthActivity.this, Main2Activity.class);
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
                                    TextView two_visit_disease = (TextView) findViewById(R.id.two_visit_disease);
                                    two_visit_disease.setText(detail.getString("two_visit_disease"));
                                    TextView growth_evaluate = (TextView) findViewById(R.id.growth_evaluate);
                                    growth_evaluate.setText(detail.getString("growth_evaluate"));
                                    TextView transfer_treatment_suggestion = (TextView) findViewById(R.id.transfer_treatment_suggestion);
                                    transfer_treatment_suggestion.setText(detail.getString("transfer_treatment_suggestion"));
                                    TextView guide = (TextView) findViewById(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView next_visit_date = (TextView) findViewById(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView weight = (TextView) findViewById(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView weight_grade = (TextView) findViewById(R.id.weight_grade);
                                    weight_grade.setText(detail.getString("weight_grade"));
                                    TextView height = (TextView) findViewById(R.id.height);
                                    height.setText(detail.getString("height"));
                                    TextView height_grade = (TextView) findViewById(R.id.height_grade);
                                    height_grade.setText(detail.getString("height_grade"));
                                    TextView head_circumference = (TextView) findViewById(R.id.head_circumference);
                                    head_circumference.setText(detail.getString("head_circumference"));
                                    TextView outdoor_activities = (TextView) findViewById(R.id.outdoor_activities);
                                    outdoor_activities.setText(detail.getString("outdoor_activities"));
                                    TextView take_vitamin_d = (TextView) findViewById(R.id.take_vitamin_d);
                                    take_vitamin_d.setText(detail.getString("take_vitamin_d"));
                                    TextView complexion = (TextView) findViewById(R.id.complexion);
                                    complexion.setText(detail.getString("complexion"));
                                    TextView skin = (TextView) findViewById(R.id.skin);
                                    skin.setText(detail.getString("skin"));
                                    TextView bregma = (TextView) findViewById(R.id.bregma);
                                    bregma.setText(detail.getString("bregma"));
                                    if (!detail.getString("bregma_length").equals("null")) {
                                        TextView bregma_length = (TextView) findViewById(R.id.bregma_length);
                                        bregma_length.setText(detail.getString("bregma_length"));
                                    }
                                    if (!detail.getString("bregma_width").equals("null")) {
                                        TextView bregma_width = (TextView) findViewById(R.id.bregma_width);
                                        bregma_width.setText(detail.getString("bregma_width"));
                                    }

                                    TextView eye_appearance = (TextView) findViewById(R.id.eye_appearance);
                                    eye_appearance.setText(detail.getString("eye_appearance"));
                                    TextView ear_appearance = (TextView) findViewById(R.id.ear_appearance);
                                    ear_appearance.setText(detail.getString("ear_appearance"));
                                    TextView oral_cavity = (TextView) findViewById(R.id.oral_cavity);
                                    oral_cavity.setText(detail.getString("oral_cavity"));
                                    TextView heart_lung = (TextView) findViewById(R.id.heart_lung);
                                    heart_lung.setText(detail.getString("heart_lung"));
                                    TextView abdomen = (TextView) findViewById(R.id.abdomen);
                                    abdomen.setText(detail.getString("abdomen"));
                                    TextView all_fours = (TextView) findViewById(R.id.all_fours);
                                    all_fours.setText(detail.getString("all_fours"));
                                    TextView anus_externalia = (TextView) findViewById(R.id.anus_externalia);
                                    anus_externalia.setText(detail.getString("anus_externalia"));
                                    TextView hemoglobin_value = (TextView) findViewById(R.id.hemoglobin_value);
                                    hemoglobin_value.setText(detail.getString("hemoglobin_value"));
                                    TextView rickets_sign = (TextView) findViewById(R.id.rickets_sign);
                                    rickets_sign.setText(detail.getString("rickets_sign"));
                                    TextView transfer_treatment_suggestion_reason = (TextView) findViewById(R.id.transfer_treatment_suggestion_reason);
                                    transfer_treatment_suggestion_reason.setText(detail.getString("transfer_treatment_suggestion_reason"));
                                    TextView transfer_treatment_suggestion_institution = (TextView) findViewById(R.id.transfer_treatment_suggestion_institution);
                                    transfer_treatment_suggestion_institution.setText(detail.getString("transfer_treatment_suggestion_institution"));


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

        Intent intent = new Intent(Aftercare1To8MonthActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
