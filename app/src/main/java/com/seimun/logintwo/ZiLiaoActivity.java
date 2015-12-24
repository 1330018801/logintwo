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

public class ZiLiaoActivity extends AppCompatActivity {
    private static final String TAG = ZiLiaoActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_liao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZiLiaoActivity.this,Main2Activity.class);
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
                        TextView name = (TextView)findViewById(R.id.name);
                        name.setText(user.get("name"));
                        TextView gender = (TextView)findViewById(R.id.gender);
                        gender.setText(jObj.getString("gender"));
                        TextView birthday = (TextView)findViewById(R.id.birthday);
                        birthday  .setText(jObj.getString("birthday"));
                        TextView identity = (TextView)findViewById(R.id.identity);
                        TextView contact_name=(TextView)findViewById(R.id.contact_name);
                        TextView phone = (TextView) findViewById(R.id.phone);
                        phone.setText(jObj.getString("phone"));
                        TextView contact_phone=(TextView)findViewById(R.id.contact_phone);
                        identity.setText(jObj.getString("identity"));
                        contact_name.setText(jObj.getString("contact_name"));
                        contact_phone.setText(jObj.getString("contact_phone"));
                        TextView nation=(TextView)findViewById(R.id.nation);
                        nation.setText(jObj.getString("nation"));
                        TextView education=(TextView)findViewById(R.id.education);
                        education.setText(jObj.getString("education"));
                        TextView occupation=(TextView)findViewById(R.id.occupation);
                        occupation.setText(jObj.getString("occupation"));
                        TextView work_company = (TextView) findViewById(R.id.work_company);
                        work_company.setText(jObj.getString("work_company"));
                        TextView marriage=(TextView)findViewById(R.id.marriage);
                        marriage.setText(jObj.getString("marriage"));
                        TextView blood_type=(TextView)findViewById(R.id.blood_type);
                        blood_type.setText(jObj.getString("blood_type"));
                        TextView payment_way=(TextView)findViewById(R.id.payment_way);
                        payment_way.setText(jObj.getString("payment_way"));



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

        Intent intent = new Intent(ZiLiaoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}