package com.seimun.logintwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.activity.DetailActivity;
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.activity.ServicesActivity;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AftercareActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftercare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnMain = (Button) findViewById(R.id.btnMain);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
            // final HashMap<String, String> user = db.getUserDetails();

            Intent intent = getIntent();
            final Integer record_id = intent.getIntExtra("record_id", 0);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logoutUser();
                }
            });

            btnMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AftercareActivity.this, ServicesActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
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
                                        doctor_signature .setText(detail.getString("doctor_signature"));
                                        TextView gestational_weeks = (TextView) findViewById(R.id.gestational_weeks);
                                        gestational_weeks .setText(detail.getString("gestational_weeks"));
                                        TextView complaint = (TextView) findViewById(R.id.complaint);
                                        complaint.setText(detail.getString("complaint"));
                                        TextView weight = (TextView) findViewById(R.id.weight);
                                        weight .setText(detail.getString("weight"));
                                        TextView sbp = (TextView) findViewById(R.id.sbp);
                                        sbp.setText(detail.getString("sbp"));
                                        TextView dbp = (TextView) findViewById(R.id.dbp);
                                        dbp.setText(detail.getString("dbp"));
                                        TextView hemoglobin = (TextView) findViewById(R.id.hemoglobin);
                                        hemoglobin .setText(detail.getString("hemoglobin"));
                                        TextView urine_protein = (TextView) findViewById(R.id.urine_protein);
                                        urine_protein .setText(detail.getString("urine_protein"));
                                        TextView examination_before_parturition_uteri_bottom_height = (TextView) findViewById(R.id.examination_before_parturition_uteri_bottom_height);
                                        examination_before_parturition_uteri_bottom_height .setText(detail.getString("examination_before_parturition_uteri_bottom_height"));
                                        TextView examination_before_parturition_abdomen_circumference = (TextView) findViewById(R.id.examination_before_parturition_abdomen_circumference);
                                        examination_before_parturition_abdomen_circumference .setText(detail.getString("examination_before_parturition_abdomen_circumference"));
                                        TextView examination_before_parturition_fetus_position = (TextView) findViewById(R.id.examination_before_parturition_fetus_position);
                                        examination_before_parturition_fetus_position.setText(detail.getString("examination_before_parturition_fetus_position"));
                                        TextView examination_before_parturition_fetal_heart_rate = (TextView) findViewById(R.id.examination_before_parturition_fetal_heart_rate);
                                        examination_before_parturition_fetal_heart_rate .setText(detail.getString("examination_before_parturition_fetal_heart_rate"));
                                        TextView extra_auxiliary_examination = (TextView) findViewById(R.id.extra_auxiliary_examination);
                                        extra_auxiliary_examination.setText(detail.getString("extra_auxiliary_examination"));
                                        TextView classification = (TextView) findViewById(R.id.classification);
                                        classification .setText(detail.getString("classification"));
                                        TextView classification_abnormal = (TextView) findViewById(R.id.classification_abnormal);
                                        classification_abnormal.setText(detail.getString("classification_abnormal"));
                                        TextView guide = (TextView) findViewById(R.id.guide);
                                        guide .setText(detail.getString("guide"));
                                        TextView transfer_treatment = (TextView) findViewById(R.id.transfer_treatment);
                                        transfer_treatment.setText(detail.getString("transfer_treatment"));
                                        TextView transfer_treatment_reason = (TextView) findViewById(R.id.transfer_treatment_reason);
                                        transfer_treatment_reason .setText(detail.getString("transfer_treatment_reason"));
                                        TextView transfer_treatment_institution = (TextView) findViewById(R.id.transfer_treatment_institution);
                                        transfer_treatment_institution .setText(detail.getString("transfer_treatment_institution"));
                                        TextView next_visit_date = (TextView) findViewById(R.id.next_visit_date);
                                        next_visit_date .setText(detail.getString("next_visit_date"));

                                    } else {
                                        String errorMsg = obj.getString("error_msg");
                                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
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

        Intent intent = new Intent(AftercareActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
