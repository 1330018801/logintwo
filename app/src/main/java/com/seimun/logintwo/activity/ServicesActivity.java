package com.seimun.logintwo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.seimun.logintwo.R;
import com.seimun.logintwo.adapter.SummaryListAdapter;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;
import com.seimun.logintwo.model.Summary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends Activity {
    private static final String TAG = ServicesActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<Summary> summaryList = new ArrayList<>();
    private ListView listView;
    private SummaryListAdapter adapter;

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        listView = (ListView)findViewById(R.id.services);
        adapter = new SummaryListAdapter(this, summaryList);
        listView.setAdapter(adapter);

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnMain = (Button)findViewById(R.id.btnMain);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // HashMap<String, String> user = db.getUserDetails();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.show();

        /*
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));
        */
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServicesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        JsonArrayRequest summaryReq = new JsonArrayRequest(AppConfig.URL_SUMMARYS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Summary summary = new Summary();
                                summary.setTitle(obj.getString("title"));
                                summary.setClinic(obj.getString("clinic"));
                                summary.setProvider(obj.getString("provider"));
                                summary.setServiceTime(obj.getString("service_time"));
                                summaryList.add(summary);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(summaryReq);
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        Intent intent = new Intent(ServicesActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
