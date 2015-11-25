package com.seimun.logintwo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AntenatalActivity extends Activity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antenatal);

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
                                    TextView visit_date = (TextView)findViewById(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView weeks = (TextView)findViewById(R.id.weeks);
                                    weeks.setText(detail.getString("weeks"));
                                    TextView age = (TextView)findViewById(R.id.age);
                                    age.setText(detail.getString("age"));
                                    TextView husband_name = (TextView)findViewById(R.id.husband_name);
                                    husband_name.setText(detail.getString("husband_name"));
                                    TextView husband_age = (TextView)findViewById(R.id.husband_age);
                                    husband_age.setText(detail.getString("husband_age"));
                                    TextView husband_phone = (TextView)findViewById(R.id.husband_phone);
                                    husband_phone.setText(detail.getString("husband_phone"));
                                    TextView pregnant_times = (TextView)findViewById(R.id.pregnant_times);
                                    pregnant_times.setText(detail.getString("pregnant_times"));
                                    TextView natural_production = (TextView)findViewById(R.id.natural_production);
                                    natural_production.setText(detail.getString("natural_production"));
                                    TextView surgery_production = (TextView)findViewById(R.id.surgery_production);
                                    surgery_production.setText(detail.getString("surgery_production"));
                                    TextView last_menstruation = (TextView)findViewById(R.id.last_menstruation);
                                    last_menstruation.setText(detail.getString("last_menstruation"));
                                    TextView due_date = (TextView)findViewById(R.id.due_date);
                                    due_date.setText(detail.getString("due_date"));
                                    TextView disease_history = (TextView)findViewById(R.id.disease_history);
                                    disease_history.setText(detail.getString("disease_history"));
                                    TextView family_history = (TextView)findViewById(R.id.family_history);
                                    family_history.setText(detail.getString("family_history"));
                                    TextView personal_history = (TextView)findViewById(R.id.personal_history);
                                    personal_history.setText(detail.getString("family_history"));
                                    TextView gynaecology_surgery_history = (TextView)findViewById(R.id.gynaecology_surgery_history);
                                    gynaecology_surgery_history.setText(detail.getString("gynaecology_surgery_history"));
                                    TextView miscarriage = (TextView)findViewById(R.id.miscarriage);
                                    miscarriage.setText(detail.getString("miscarriage"));
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

        Intent intent = new Intent(AntenatalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
 