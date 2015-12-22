package com.seimun.logintwo.activity;


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
import com.seimun.logintwo.R;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Aftercare12To30MonthActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftercare12_to30_month);
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
                Intent intent = new Intent(Aftercare12To30MonthActivity.this, Main2Activity.class);
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
                                    TextView two_visit_disease = (TextView) findViewById(R.id.two_visit_disease);
                                    two_visit_disease .setText(detail.getString("two_visit_disease"));
                                    TextView transfer_treatment_suggestion = (TextView) findViewById(R.id.transfer_treatment_suggestion);
                                    transfer_treatment_suggestion.setText(detail.getString("transfer_treatment_suggestion"));
                                    TextView guide = (TextView) findViewById(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView next_visit_date = (TextView) findViewById(R.id.next_visit_date);
                                    next_visit_date .setText(detail.getString("next_visit_date"));
                                    TextView weight = (TextView) findViewById(R.id.weight);
                                    weight .setText(detail.getString("weight"));
                                    TextView weight_grade = (TextView) findViewById(R.id.weight_grade);
                                    weight_grade .setText(detail.getString("weight_grade"));
                                    TextView height = (TextView) findViewById(R.id.height);
                                    height .setText(detail.getString("height"));
                                    TextView height_grade = (TextView) findViewById(R.id.height_grade);
                                    height_grade .setText(detail.getString("height_grade"));
                                    TextView outdoor_activities = (TextView) findViewById(R.id.outdoor_activities);
                                    outdoor_activities .setText(detail.getString("outdoor_activities"));
                                    TextView complexion = (TextView) findViewById(R.id.complexion);
                                    complexion .setText(detail.getString("complexion"));
                                    TextView skin = (TextView) findViewById(R.id.skin);
                                    skin.setText(detail.getString("skin"));
                                    TextView eye_appearance = (TextView) findViewById(R.id.eye_appearance);
                                    eye_appearance .setText(detail.getString("eye_appearance"));
                                    TextView ear_appearance = (TextView) findViewById(R.id.ear_appearance);
                                    ear_appearance .setText(detail.getString("ear_appearance"));
                                    TextView decayed_tooth = (TextView) findViewById(R.id.decayed_tooth);
                                    decayed_tooth .setText(detail.getString("decayed_tooth"));
                                    TextView tooth = (TextView) findViewById(R.id.tooth);
                                    tooth.setText(detail.getString("tooth"));
                                    TextView heart_lung = (TextView) findViewById(R.id.heart_lung);
                                    heart_lung .setText(detail.getString("heart_lung"));
                                    TextView abdomen = (TextView) findViewById(R.id.abdomen);
                                    abdomen .setText(detail.getString("abdomen"));
                                    TextView all_fours = (TextView) findViewById(R.id.all_fours);
                                    all_fours .setText(detail.getString("all_fours"));
                                    TextView transfer_treatment_suggestion_reason = (TextView) findViewById(R.id.transfer_treatment_suggestion_reason);
                                    transfer_treatment_suggestion_reason .setText(detail.getString("transfer_treatment_suggestion_reason"));
                                    TextView transfer_treatment_suggestion_institution = (TextView) findViewById(R.id.transfer_treatment_suggestion_institution);
                                    transfer_treatment_suggestion_institution .setText(detail.getString("transfer_treatment_suggestion_institution"));



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

        Intent intent = new Intent(Aftercare12To30MonthActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}