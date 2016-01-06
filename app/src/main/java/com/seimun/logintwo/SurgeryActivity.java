package com.seimun.logintwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.activity.Main2Activity;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SurgeryActivity extends AppCompatActivity {
    private static final String TAG = SurgeryActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurgeryActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
//         final HashMap<String, String> user = db.getUserDetails();


        Intent intent = getIntent();
        final Integer record_id = intent.getIntExtra("record_id",0 );
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
                                    TextView body_temperature = (TextView) findViewById(R.id.body_temperature);
                                    body_temperature.setText(detail.getString("body_temperature"));
                                    TextView pulse = (TextView) findViewById(R.id.pulse);
                                    pulse.setText(detail.getString("pulse"));
                                    TextView  breath_frequency= (TextView) findViewById(R.id.breath_frequency);
                                    breath_frequency.setText(detail.getString("breath_frequency"));
                                    TextView  blood_pressure_left_sbp= (TextView) findViewById(R.id.blood_pressure_left_sbp);
                                    blood_pressure_left_sbp.setText(detail.getString("blood_pressure_left_sbp"));
                                    TextView blood_pressure_left_dbp = (TextView) findViewById(R.id.blood_pressure_left_dbp);
                                    blood_pressure_left_dbp.setText(detail.getString("blood_pressure_left_dbp"));
                                    TextView blood_pressure_right_sbp = (TextView) findViewById(R.id.blood_pressure_right_sbp);
                                    blood_pressure_right_sbp.setText(detail.getString("blood_pressure_right_sbp"));
                                    TextView blood_pressure_right_dbp = (TextView) findViewById(R.id.blood_pressure_right_dbp);
                                    blood_pressure_right_dbp.setText(detail.getString("blood_pressure_right_dbp"));
                                    TextView height = (TextView) findViewById(R.id.height);
                                    height.setText(detail.getString("height"));
                                    TextView weight = (TextView) findViewById(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView waistline = (TextView) findViewById(R.id.waistline);
                                    waistline.setText(detail.getString("waistline"));
                                    TextView body_mass_index = (TextView) findViewById(R.id.body_mass_index);
                                    body_mass_index.setText(detail.getString("body_mass_index"));
                                    TextView mouth_lip = (TextView) findViewById(R.id.mouth_lip);
                                    mouth_lip.setText(detail.getString("mouth_lip"));
                                    TextView mouth_tooth = (TextView) findViewById(R.id.mouth_tooth);
                                    mouth_tooth.setText(detail.getString("mouth_tooth"));
                                    if(!detail.getString("mouth_tooth_missing_upleft").equals("null")) {
                                        TextView mouth_tooth_missing_upleft = (TextView) findViewById(R.id.mouth_tooth_missing_upleft);
                                        mouth_tooth_missing_upleft.setText(detail.getString("mouth_tooth_missing_upleft"));
                                    }
                                    if(!detail.getString("mouth_tooth_missing_bottomleft").equals("null")) {
                                        TextView mouth_tooth_missing_bottomleft = (TextView) findViewById(R.id.mouth_tooth_missing_bottomleft);
                                        mouth_tooth_missing_bottomleft.setText(detail.getString("mouth_tooth_missing_bottomleft"));
                                    }
                                    if(!detail.getString("mouth_tooth_missing_upright").equals("null")) {
                                        TextView mouth_tooth_missing_upright = (TextView) findViewById(R.id.mouth_tooth_missing_upright);
                                        mouth_tooth_missing_upright.setText(detail.getString("mouth_tooth_missing_upright"));
                                    }
                                    if(!detail.getString("mouth_tooth_missing_bottomright").equals("null")) {
                                        TextView mouth_tooth_missing_bottomright = (TextView) findViewById(R.id.mouth_tooth_missing_bottomright);
                                        mouth_tooth_missing_bottomright.setText(detail.getString("mouth_tooth_missing_bottomright"));
                                    }
                                    if(!detail.getString("mouth_tooth_decayed_upleft").equals("null")) {
                                        TextView mouth_tooth_decayed_upleft = (TextView) findViewById(R.id.mouth_tooth_decayed_upleft);
                                        mouth_tooth_decayed_upleft.setText(detail.getString("mouth_tooth_decayed_upleft"));
                                    }
                                    if(!detail.getString("mouth_tooth_decayed_bottomleft").equals("null")) {
                                        TextView mouth_tooth_decayed_bottomleft = (TextView) findViewById(R.id.mouth_tooth_decayed_bottomleft);
                                        mouth_tooth_decayed_bottomleft.setText(detail.getString("mouth_tooth_decayed_bottomleft"));
                                    }
                                    if(!detail.getString("mouth_tooth_decayed_upright").equals("null")) {
                                        TextView mouth_tooth_decayed_upright = (TextView) findViewById(R.id.mouth_tooth_decayed_upright);
                                        mouth_tooth_decayed_upright.setText(detail.getString("mouth_tooth_decayed_upright"));
                                    }
                                    if(!detail.getString("mouth_tooth_decayed_bottomright").equals("null")) {
                                        TextView mouth_tooth_decayed_bottomright = (TextView) findViewById(R.id.mouth_tooth_decayed_bottomright);
                                        mouth_tooth_decayed_bottomright.setText(detail.getString("mouth_tooth_decayed_bottomright"));
                                    }
                                    if(!detail.getString("mouth_tooth_denture_upleft").equals("null")) {
                                        TextView mouth_tooth_denture_upleft = (TextView) findViewById(R.id.mouth_tooth_denture_upleft);
                                        mouth_tooth_denture_upleft.setText(detail.getString("mouth_tooth_denture_upleft"));
                                    }
                                    if(!detail.getString("mouth_tooth_denture_bottomleft").equals("null")) {
                                        TextView mouth_tooth_denture_bottomleft = (TextView) findViewById(R.id.mouth_tooth_denture_bottomleft);
                                        mouth_tooth_denture_bottomleft.setText(detail.getString("mouth_tooth_denture_bottomleft"));
                                    }
                                    if(!detail.getString("mouth_tooth_denture_upright").equals("null")) {
                                        TextView mouth_tooth_denture_upright = (TextView) findViewById(R.id.mouth_tooth_denture_upright);
                                        mouth_tooth_denture_upright.setText(detail.getString("mouth_tooth_denture_upright"));
                                    }
                                    if(!detail.getString("mouth_tooth_denture_bottomright").equals("null")) {
                                        TextView mouth_tooth_denture_bottomright = (TextView) findViewById(R.id.mouth_tooth_denture_bottomright);
                                        mouth_tooth_denture_bottomright.setText(detail.getString("mouth_tooth_denture_bottomright"));
                                    }
                                    TextView mouth_throat = (TextView) findViewById(R.id.mouth_throat);
                                    mouth_throat.setText(detail.getString("mouth_throat"));
                                    TextView eyesight_left = (TextView) findViewById(R.id.eyesight_left);
                                    eyesight_left.setText(detail.getString("eyesight_left"));
                                    TextView eyesight_right = (TextView) findViewById(R.id.eyesight_right);
                                    eyesight_right.setText(detail.getString("eyesight_right"));
                                    TextView eyesight_left_rectified = (TextView) findViewById(R.id.eyesight_left_rectified);
                                    eyesight_left_rectified.setText(detail.getString("eyesight_left_rectified"));
                                    TextView eyesight_right_rectified = (TextView) findViewById(R.id.eyesight_right_rectified);
                                    eyesight_right_rectified.setText(detail.getString("eyesight_right_rectified"));
                                    TextView hearing = (TextView) findViewById(R.id.hearing);
                                    hearing.setText(detail.getString("hearing"));
                                    TextView movement_function = (TextView) findViewById(R.id.movement_function);
                                    movement_function.setText(detail.getString("movement_function"));
                                    TextView skin = (TextView) findViewById(R.id.skin);
                                    skin.setText(detail.getString("skin"));
                                    TextView skin_extra= (TextView) findViewById(R.id.skin_extra);
                                    skin_extra.setText(detail.getString("skin_extra"));
                                    TextView lymph_node = (TextView) findViewById(R.id.lymph_node);
                                    lymph_node.setText(detail.getString("lymph_node"));
                                    TextView lymph_node_extra = (TextView) findViewById(R.id.lymph_node_extra);
                                    lymph_node_extra.setText(detail.getString("lymph_node_extra"));
                                    TextView stomach_tenderness = (TextView) findViewById(R.id.stomach_tenderness);
                                    stomach_tenderness.setText(detail.getString("stomach_tenderness"));
                                    TextView stomach_tenderness_extra = (TextView) findViewById(R.id.stomach_tenderness_extra);
                                    stomach_tenderness_extra.setText(detail.getString("stomach_tenderness_extra"));
                                    TextView stomach_enclosed_mass = (TextView) findViewById(R.id.stomach_enclosed_mass);
                                    stomach_enclosed_mass.setText(detail.getString("stomach_enclosed_mass"));
                                    TextView stomach_enclosed_mass_extra = (TextView) findViewById(R.id.stomach_enclosed_mass_extra);
                                    stomach_enclosed_mass_extra.setText(detail.getString("stomach_enclosed_mass_extra"));
                                    TextView stomach_hepatomegaly = (TextView) findViewById(R.id.stomach_hepatomegaly);
                                    stomach_hepatomegaly.setText(detail.getString("stomach_hepatomegaly"));
                                    TextView stomach_hepatomegaly_extra = (TextView) findViewById(R.id.stomach_hepatomegaly_extra);
                                    stomach_hepatomegaly_extra.setText(detail.getString("stomach_hepatomegaly_extra"));
                                    TextView stomach_slenauxe = (TextView) findViewById(R.id.stomach_slenauxe);
                                    stomach_slenauxe.setText(detail.getString("stomach_slenauxe"));
                                    TextView stomach_slenauxe_extra = (TextView) findViewById(R.id.stomach_slenauxe_extra);
                                    stomach_slenauxe_extra.setText(detail.getString("stomach_slenauxe_extra"));
                                    TextView stomach_shifting_dullness = (TextView) findViewById(R.id.stomach_shifting_dullness);
                                    stomach_shifting_dullness.setText(detail.getString("stomach_shifting_dullness"));
                                    TextView stomach_shifting_dullness_extra = (TextView) findViewById(R.id.stomach_shifting_dullness_extra);
                                    stomach_shifting_dullness_extra.setText(detail.getString("stomach_shifting_dullness_extra"));


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

        Intent intent = new Intent(SurgeryActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}