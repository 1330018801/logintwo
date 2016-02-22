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

public class OldIdentifyActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();


    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_identify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OldIdentifyActivity.this, Main2Activity.class);
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
                                    TextView fill_table_date = (TextView) findViewById(R.id.fill_table_date);
                                    fill_table_date.setText(detail.getString("fill_table_date"));
                                    TextView points_pinghe = (TextView) findViewById(R.id.points_pinghe);
                                    points_pinghe.setText(detail.getString("points_pinghe"));
                                    if (!detail.getString("yes_trend_pinghe").equals("null")) {
                                        TextView yes_trend_pinghe = (TextView) findViewById(R.id.yes_trend_pinghe);
                                        yes_trend_pinghe.setText(detail.getString("yes_trend_pinghe"));
                                    }
                                    TextView health_care_guide_pinghe = (TextView) findViewById(R.id.health_care_guide_pinghe);
                                    health_care_guide_pinghe.setText(detail.getString("health_care_guide_pinghe"));
                                    TextView health_care_guide_extra_pinghe = (TextView) findViewById(R.id.health_care_guide_extra_pinghe);
                                    health_care_guide_extra_pinghe.setText(detail.getString("health_care_guide_extra_pinghe"));
                                    TextView points_qixu = (TextView) findViewById(R.id.points_qixu);
                                    points_qixu.setText(detail.getString("points_qixu"));
                                    if (!detail.getString("yes_trend_qixu").equals("null")) {
                                        TextView yes_trend_qixu = (TextView) findViewById(R.id.yes_trend_qixu);
                                        yes_trend_qixu.setText(detail.getString("yes_trend_qixu"));
                                    }
                                    TextView health_care_guide_qixu = (TextView) findViewById(R.id.health_care_guide_qixu);
                                    health_care_guide_qixu.setText(detail.getString("health_care_guide_qixu"));
                                    TextView health_care_guide_extra_qixu = (TextView) findViewById(R.id.health_care_guide_extra_qixu);
                                    health_care_guide_extra_qixu.setText(detail.getString("health_care_guide_extra_qixu"));
                                    TextView points_yangxu = (TextView) findViewById(R.id.points_yangxu);
                                    points_yangxu.setText(detail.getString("points_yangxu"));

                                    if (!detail.getString("yes_trend_yangxu").equals("null")) {
                                        TextView yes_trend_yangxu = (TextView) findViewById(R.id.yes_trend_yangxu);
                                        yes_trend_yangxu.setText(detail.getString("yes_trend_yangxu"));
                                    }
                                    TextView health_care_guide_yangxu = (TextView) findViewById(R.id.health_care_guide_yangxu);
                                    health_care_guide_yangxu.setText(detail.getString("health_care_guide_yangxu"));
                                    TextView health_care_guide_extra_yangxu = (TextView) findViewById(R.id.health_care_guide_extra_yangxu);
                                    health_care_guide_extra_yangxu.setText(detail.getString("health_care_guide_extra_yangxu"));
                                    TextView points_yinxu = (TextView) findViewById(R.id.points_yinxu);
                                    points_yinxu.setText(detail.getString("points_yinxu"));
                                    if (!detail.getString("yes_trend_yinxu").equals("null")) {
                                        TextView yes_trend_yinxu = (TextView) findViewById(R.id.yes_trend_yinxu);
                                        yes_trend_yinxu.setText(detail.getString("yes_trend_yinxu"));
                                    }
                                    TextView health_care_guide_yinxu = (TextView) findViewById(R.id.health_care_guide_yinxu);
                                    health_care_guide_yinxu.setText(detail.getString("health_care_guide_yinxu"));
                                    TextView health_care_guide_extra_yinxu = (TextView) findViewById(R.id.health_care_guide_extra_yinxu);
                                    health_care_guide_extra_yinxu.setText(detail.getString("health_care_guide_extra_yinxu"));
                                    TextView points_tanshi = (TextView) findViewById(R.id.points_tanshi);
                                    points_tanshi.setText(detail.getString("points_tanshi"));
                                    if (!detail.getString("yes_trend_tanshi").equals("null")) {
                                        TextView yes_trend_tanshi = (TextView) findViewById(R.id.yes_trend_tanshi);
                                        yes_trend_tanshi.setText(detail.getString("yes_trend_tanshi"));
                                    }
                                    TextView health_care_guide_tanshi = (TextView) findViewById(R.id.health_care_guide_tanshi);
                                    health_care_guide_tanshi.setText(detail.getString("health_care_guide_tanshi"));
                                    TextView health_care_guide_extra_tanshi = (TextView) findViewById(R.id.health_care_guide_extra_tanshi);
                                    health_care_guide_extra_tanshi.setText(detail.getString("health_care_guide_extra_tanshi"));
                                    TextView points_shire = (TextView) findViewById(R.id.points_shire);
                                    points_shire.setText(detail.getString("points_shire"));
                                    if (!detail.getString("yes_trend_shire").equals("null")) {
                                        TextView yes_trend_shire = (TextView) findViewById(R.id.yes_trend_shire);
                                        yes_trend_shire.setText(detail.getString("yes_trend_shire"));
                                    }
                                    TextView health_care_guide_shire = (TextView) findViewById(R.id.health_care_guide_shire);
                                    health_care_guide_shire.setText(detail.getString("health_care_guide_shire"));
                                    TextView health_care_guide_extra_shire = (TextView) findViewById(R.id.health_care_guide_extra_shire);
                                    health_care_guide_extra_shire.setText(detail.getString("health_care_guide_extra_shire"));
                                    TextView points_xueyu = (TextView) findViewById(R.id.points_xueyu);
                                    points_xueyu.setText(detail.getString("points_xueyu"));
                                    if (!detail.getString("yes_trend_xueyu").equals("null")) {
                                        TextView yes_trend_xueyu = (TextView) findViewById(R.id.yes_trend_xueyu);
                                        yes_trend_xueyu.setText(detail.getString("yes_trend_xueyu"));
                                    }
                                    TextView health_care_guide_xueyu = (TextView) findViewById(R.id.health_care_guide_xueyu);
                                    health_care_guide_xueyu.setText(detail.getString("health_care_guide_xueyu"));
                                    TextView health_care_guide_extra_xueyu = (TextView) findViewById(R.id.health_care_guide_extra_xueyu);
                                    health_care_guide_extra_xueyu.setText(detail.getString("health_care_guide_extra_xueyu"));
                                    TextView points_qiyu = (TextView) findViewById(R.id.points_qiyu);
                                    points_qiyu.setText(detail.getString("points_qiyu"));
                                    if (!detail.getString("yes_trend_qiyu").equals("null")) {
                                        TextView yes_trend_qiyu = (TextView) findViewById(R.id.yes_trend_qiyu);
                                        yes_trend_qiyu.setText(detail.getString("yes_trend_qiyu"));
                                    }
                                    TextView health_care_guide_qiyu = (TextView) findViewById(R.id.health_care_guide_qiyu);
                                    health_care_guide_qiyu.setText(detail.getString("health_care_guide_qiyu"));
                                    TextView health_care_guide_extra_qiyu = (TextView) findViewById(R.id.health_care_guide_extra_qiyu);
                                    health_care_guide_extra_qiyu.setText(detail.getString("health_care_guide_extra_qiyu"));
                                    TextView points_tebing = (TextView) findViewById(R.id.points_tebing);
                                    points_tebing.setText(detail.getString("points_tebing"));
                                    if (!detail.getString("yes_trend_tebing").equals("null")) {
                                        TextView yes_trend_tebing = (TextView) findViewById(R.id.yes_trend_tebing);
                                        yes_trend_tebing.setText(detail.getString("yes_trend_tebing"));
                                    }
                                    TextView health_care_guide_tebing = (TextView) findViewById(R.id.health_care_guide_tebing);
                                    health_care_guide_tebing.setText(detail.getString("health_care_guide_tebing"));
                                    TextView health_care_guide_extra_tebing = (TextView) findViewById(R.id.health_care_guide_extra_tebing);
                                    health_care_guide_extra_tebing.setText(detail.getString("health_care_guide_extra_tebing"));
                                    TextView doctor_signature = (TextView) findViewById(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));


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

        Intent intent = new Intent(OldIdentifyActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}


