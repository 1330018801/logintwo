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

public class LifeStyleActivity extends AppCompatActivity {

    private static final String TAG = LifeStyleActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeStyleActivity.this, Main2Activity.class);
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
                        TextView expose_history = (TextView) findViewById(R.id.expose_history);
                        expose_history.setText(jObj.getString("expose_history"));
                        TextView surroundings_kitchen_exhaust = (TextView) findViewById(R.id.surroundings_kitchen_exhaust);
                        TextView surroundings_fuel_type = (TextView) findViewById(R.id.surroundings_fuel_type);
                        TextView surroundings_water = (TextView) findViewById(R.id.surroundings_water);
                        TextView surroundings_toilet = (TextView) findViewById(R.id.surroundings_toilet);
                        TextView surrounding_livestock_fence = (TextView) findViewById(R.id.surrounding_livestock_fence);
                        surroundings_kitchen_exhaust.setText(jObj.getString("surroundings_kitchen_exhaust"));
                        surroundings_fuel_type.setText(jObj.getString("surroundings_fuel_type"));
                        surroundings_water.setText(jObj.getString("surroundings_water"));
                        surroundings_toilet.setText(jObj.getString("surroundings_toilet"));
                        surrounding_livestock_fence.setText(jObj.getString("surrounding_livestock_fence"));

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

        Intent intent = new Intent(LifeStyleActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}