package com.seimun.logintwo.activity;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by Administrator on 2015/12/9.
 */
public class Aftercare24Month extends Aftercare12To30MonthActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aftercare24Month.this, Main2Activity.class);
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
                                    TextView hearing = (TextView) findViewById(R.id.hearing);
                                    hearing.setText(detail.getString("hearing"));
                                    TextView step = (TextView) findViewById(R.id.step);
                                    step.setText(detail.getString("step"));
                                    if (!detail.getString("rickets_sign").equals("null")) {
                                        TextView rickets_sign = (TextView) findViewById(R.id.rickets_sign);
                                        rickets_sign.setText(detail.getString("rickets_sign"));
                                    }
                                    TextView take_vitamin_d = (TextView) findViewById(R.id.take_vitamin_d);
                                    take_vitamin_d.setText(detail.getString("take_vitamin_d"));
                                    TextView growth_evaluate = (TextView) findViewById(R.id.growth_evaluate);
                                    growth_evaluate.setText(detail.getString("growth_evaluate"));

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

        Intent intent = new Intent(Aftercare24Month.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
