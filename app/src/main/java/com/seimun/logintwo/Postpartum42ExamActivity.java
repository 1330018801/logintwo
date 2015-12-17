package com.seimun.logintwo;

import android.content.Intent;
import android.os.Bundle;
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

public class Postpartum42ExamActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpartum42_exam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);


        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnMain = (Button)findViewById(R.id.btnMain);

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

        btnMain.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Postpartum42ExamActivity.this, ServicesActivity.class);
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
                                    TextView visit_date = (TextView)findViewById(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView doctor_signature = (TextView) findViewById(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView general_health_situation = (TextView) findViewById(R.id.general_health_situation);
                                    general_health_situation.setText(detail.getString("general_health_situation"));
                                    TextView general_mentality_situation = (TextView) findViewById(R.id.general_mentality_situation);
                                    general_mentality_situation.setText(detail.getString("general_mentality_situation"));
                                    TextView sbp = (TextView) findViewById(R.id.sbp);
                                    sbp.setText(detail.getString("sbp"));
                                    TextView dbp = (TextView) findViewById(R.id.dbp);
                                    dbp.setText(detail.getString("dbp"));
                                    TextView breast = (TextView) findViewById(R.id.breast);
                                    breast.setText(detail.getString("breast"));
                                    TextView breast_abnormal = (TextView) findViewById(R.id.breast_abnormal);
                                    breast_abnormal.setText(detail.getString("breast_abnormal"));
                                    TextView lochia = (TextView) findViewById(R.id.lochia);
                                    lochia.setText(detail.getString("lochia"));
                                    TextView lochia_abnormal = (TextView) findViewById(R.id.lochia_abnormal);
                                    lochia_abnormal.setText(detail.getString("lochia_abnormal"));
                                    TextView uterus = (TextView) findViewById(R.id.uterus);
                                    uterus.setText(detail.getString("uterus"));
                                    TextView uterus_abnormal = (TextView) findViewById(R.id.uterus_abnormal);
                                    uterus_abnormal.setText(detail.getString("uterus_abnormal"));
                                    TextView wound = (TextView) findViewById(R.id.wound);
                                    wound.setText(detail.getString("wound"));
                                    TextView wound_abnormal = (TextView) findViewById(R.id.wound_abnormal);
                                    wound_abnormal.setText(detail.getString("wound_abnormal"));
                                    TextView extra = (TextView) findViewById(R.id.extra);
                                    extra.setText(detail.getString("extra"));
                                    TextView classification = (TextView) findViewById(R.id.classification);
                                    classification.setText(detail.getString("classification"));
                                    TextView classification_not_recovery = (TextView)findViewById(R.id.classification_not_recovery);
                                    classification_not_recovery.setText(detail.getString("classification_not_recovery"));
                                    TextView guide = (TextView) findViewById(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView guide_extra = (TextView) findViewById(R.id.guide_extra);
                                    guide_extra.setText(detail.getString("guide_extra"));
                                    TextView dispose = (TextView) findViewById(R.id.dispose);
                                    dispose.setText(detail.getString("dispose"));
                                    TextView dispose_transfer_treatment_reason = (TextView) findViewById(R.id.dispose_transfer_treatment_reason);
                                    dispose_transfer_treatment_reason.setText(detail.getString("dispose_transfer_treatment_reason"));
                                    TextView dispose_transfer_treatment_institution = (TextView) findViewById(R.id.dispose_transfer_treatment_institution);
                                    dispose_transfer_treatment_institution.setText(detail.getString("dispose_transfer_treatment_institution"));

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

        Intent intent = new Intent(Postpartum42ExamActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}