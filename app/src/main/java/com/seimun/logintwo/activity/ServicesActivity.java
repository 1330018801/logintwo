package com.seimun.logintwo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.R;
import com.seimun.logintwo.adapter.SummaryListAdapter;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;
import com.seimun.logintwo.model.Summary;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        final HashMap<String, String> user = db.getUserDetails();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.show();

        /*
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));
        */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text or do whatever you need.
                // TextView title = (TextView)view.findViewById(R.id.service_title);
                // Toast.makeText(getApplicationContext(), title.getText(), Toast.LENGTH_SHORT).show();
                final Summary summary = db.getSummaryDetails(position + 1);
                Log.e(TAG, "Successfully get the summary instance: " + summary.getTitle());
                //Toast.makeText(getApplicationContext(), summary.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ServicesActivity.this, AntenatalActivity.class);
                intent.putExtra("record_id", summary.getRecordId());
                startActivity(intent);
                finish();

                /*
                // Get the service detail information by volley
                final StringRequest detailReq = new StringRequest(
                        Request.Method.POST,
                        AppConfig.URL_DETAIL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, response);
                                hidePDialog();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (!obj.getBoolean("error")) {
                                        JSONObject detail = obj.getJSONObject("detail");
                                        Toast.makeText(getApplicationContext(), detail.getString("visit_date"), Toast.LENGTH_SHORT).show();
                                        // something goes here
                                        // according to the service type, convert to suitable activity

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
                                hidePDialog();
                            }

                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("record_id", Integer.toString(summary.getRecordId()));
                        Log.e(TAG, "to get record_id: " + summary.getRecordId());
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(detailReq);
                */
            }
        });

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

        final StringRequest summaryReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SUMMARYS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        hidePDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                int length = obj.getInt("length");
                                for (int i = 0; i < length; i++) {
                                    JSONObject item = (JSONObject)obj.getJSONArray("list").get(i);
                                    Log.e(TAG, "item: " + i + ", " + item.getString("title"));
                                    Summary summary = new Summary();
                                    summary.setRecordId(item.getInt("resident_id"));
                                    summary.setTitle(item.getString("title"));
                                    summary.setClinic(item.getString("clinic"));
                                    summary.setProvider(item.getString("provider"));
                                    summary.setServiceTime(item.getString("service_time"));
                                    summaryList.add(summary);
                                    db.addSummary(summary);
                                    Log.e(TAG, "summary length: " + summaryList.size());
                                }
                            } else {
                                String errorMsg = obj.getString("error_msg");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();
                    }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("resident_id", user.get("resident_id"));

                return params;
            }
        };
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
