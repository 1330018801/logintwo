package com.seimun.logintwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class InternalMedicineActivity extends AppCompatActivity {
    private static final String TAG = InternalMedicineActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InternalMedicineActivity.this, Main2Activity.class);
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

        StringRequest detailReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    if(!obj.getBoolean("error")){
                        JSONObject detail = obj.getJSONObject("detail");

                        TextView lung_barrel_chested = (TextView) findViewById(R.id.lung_barrel_chested);
                        lung_barrel_chested.setText(detail.getString("lung_barrel_chested"));
                        TextView lung_breath_sound = (TextView) findViewById(R.id.lung_breath_sound);
                        TextView lung_breath_sound_extra = (TextView) findViewById(R.id.lung_breath_sound_extra);
                        lung_breath_sound_extra.setText(detail.getString("lung_breath_sound_extra"));
                        lung_breath_sound.setText(detail.getString("lung_breath_sound"));
                        TextView lung_rale = (TextView) findViewById(R.id.lung_rale);
                        lung_rale.setText(detail.getString("lung_rale"));
                        TextView lung_rale_extra= (TextView) findViewById(R.id.lung_rale_extra);
                        lung_rale_extra.setText(detail.getString("lung_rale_extra"));
                        TextView heart_rate = (TextView) findViewById(R.id.heart_rate);
                        heart_rate.setText(detail.getString("heart_rate"));
                        TextView heart_rhythm = (TextView) findViewById(R.id.heart_rhythm);
                        heart_rhythm.setText(detail.getString("heart_rhythm"));
                        TextView heart_noise = (TextView) findViewById(R.id.heart_noise);
                        heart_noise.setText(detail.getString("heart_noise"));
                        TextView heart_noise_extra = (TextView) findViewById(R.id.heart_noise_extra);
                        heart_noise_extra.setText(detail.getString("heart_noise_extra"));

                    }else {
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
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("resident_id", user.get("resident_id"));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(detailReq);


    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        Intent intent = new Intent(InternalMedicineActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

}
