package com.seimun.logintwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class PastHistoryActivity extends AppCompatActivity {
    private static final String TAG = PagerAdapter.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PastHistoryActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        final HashMap<String, String> user = db.getUserDetails();


        StringRequest infoReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "个人基本信息操作网络通信响应: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        TextView disease_history = (TextView) findViewById(R.id.disease_history);
                        disease_history.setText(jObj.getString("disease_history"));

                        if (jObj.getString("surgery_history").equals("无")) {
                            TextView surgery_history = (TextView) findViewById(R.id.surgery_history);
                            surgery_history.setText(jObj.getString("surgery_history"));
                        }

                        if (!jObj.getString("surgery_1_name").equals("null")) {
                            TextView surgery_1_name = (TextView) findViewById(R.id.surgery_1_name);
                            surgery_1_name.setText(jObj.getString("surgery_1_name"));
                        }
                        if (jObj.getString("injury_history").equals("无")) {
                            TextView injury_history = (TextView) findViewById(R.id.injury_history);
                            injury_history.setText(jObj.getString("injury_history"));
                        }
                        if (!jObj.getString("surgery_1_date").equals("null")) {
                            TextView surgery_1_date = (TextView) findViewById(R.id.surgery_1_date);
                            surgery_1_date.setText(jObj.getString("surgery_1_date"));
                        }
                        if (!jObj.getString("injury_1_name").equals("null")) {
                            TextView injury_1_name = (TextView) findViewById(R.id.injury_1_name);
                            injury_1_name.setText(jObj.getString("injury_1_name"));
                        }
                        if (!jObj.getString("injury_1_date").equals("null")) {
                            TextView injury_1_date = (TextView) findViewById(R.id.injury_1_date);
                            injury_1_date.setText(jObj.getString("injury_1_date"));
                        }
                        if (jObj.getString("transfusion_history").equals("无")) {
                            TextView transfusion_history = (TextView) findViewById(R.id.transfusion_history);
                            transfusion_history.setText(jObj.getString("transfusion_history"));
                        }

                        if (!jObj.getString("transfusion_1_reason").equals("null")) {
                            TextView transfusion_1_reason = (TextView) findViewById(R.id.transfusion_1_reason);
                            transfusion_1_reason.setText(jObj.getString("transfusion_1_reason"));
                        }
                        if (!jObj.getString("transfusion_1_date").equals("null")) {
                            TextView transfusion_1_date = (TextView) findViewById(R.id.transfusion_1_date);
                            transfusion_1_date.setText(jObj.getString("transfusion_1_date"));
                        }
                        TextView disability = (TextView) findViewById(R.id.disability);
                        disability.setText(jObj.getString("disability"));
                        TextView disability_extra = (TextView) findViewById(R.id.disability_extra);
                        disability_extra.setText(jObj.getString("disability_extra"));
                        TextView family_history_father_extra = (TextView) findViewById(R.id.family_history_father_extra);
                        family_history_father_extra.setText(jObj.getString("family_history_father_extra"));
                        TextView family_history_father = (TextView) findViewById(R.id.family_history_father);
                        family_history_father.setText(jObj.getString("family_history_father"));
                        TextView family_history_mother = (TextView) findViewById(R.id.family_history_mother);
                        family_history_mother.setText(jObj.getString("family_history_mother"));
                        TextView family_history_mother_extra = (TextView) findViewById(R.id.family_history_mother_extra);
                        family_history_mother_extra.setText(jObj.getString("family_history_mother_extra"));
                        TextView family_history_sibling = (TextView) findViewById(R.id.family_history_sibling);
                        family_history_sibling.setText(jObj.getString("family_history_sibling"));
                        TextView family_history_sibling_extra = (TextView) findViewById(R.id.family_history_sibling_extra);
                        family_history_sibling_extra.setText(jObj.getString("family_history_sibling_extra"));
                        TextView family_history_children = (TextView) findViewById(R.id.family_history_children);
                        family_history_children.setText(jObj.getString("family_history_children"));
                        TextView family_history_children_extra = (TextView) findViewById(R.id.family_history_children_extra);
                        family_history_children_extra.setText(jObj.getString("family_history_children_extra"));
                        TextView allergy_history = (TextView) findViewById(R.id.allergy_history);
                        allergy_history.setText(jObj.getString("allergy_history"));
                        TextView allergy_history_yes_extra = (TextView) findViewById(R.id.allergy_history_yes_extra);
                        allergy_history_yes_extra.setText(jObj.getString("allergy_history_yes_extra"));
                        TextView genetic_disease = (TextView) findViewById(R.id.genetic_disease);
                        genetic_disease.setText(jObj.getString("genetic_disease"));
                        TextView genetic_disease_yes = (TextView) findViewById(R.id.genetic_disease_yes);
                        genetic_disease_yes.setText(jObj.getString("genetic_disease_yes"));

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("resident_id", user.get("resident_id"));

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(infoReq);
    }


    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteSummaries();

        Intent intent = new Intent(PastHistoryActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}