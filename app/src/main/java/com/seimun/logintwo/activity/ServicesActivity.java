package com.seimun.logintwo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ServicesActivity extends AppCompatActivity {
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

        listView = (ListView) findViewById(R.id.services);
        adapter = new SummaryListAdapter(this, summaryList);
        listView.setAdapter(adapter);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnMain = (Button) findViewById(R.id.btnMain);

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

                final Summary summary = db.getSummaryDetails(position + 1);

                String type_alias = summary.getTypeAlias();
                String item_alias = summary.getItemAlias();
                if (type_alias.equals("pregnant") && item_alias.equals("aftercare_1")) {
                    Intent intent = new Intent(ServicesActivity.this, AntenatalActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_visit")) {
                    Intent intent = new Intent(ServicesActivity.this, PostpartumVisitActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("hypertension") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4"))) {
                    Intent intent = new Intent(ServicesActivity.this, hypertension_aftercare.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("diabetes") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4"))) {
                    Intent intent = new Intent(ServicesActivity.this, DiabetesAftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (item_alias.equals("body_exam_table") || item_alias.equals("physical_examination")) {
                    Intent intent = new Intent(ServicesActivity.this, BodyExamActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("pregnant") && (item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4") || item_alias.equals("aftercare_5"))) {
                    Intent intent = new Intent(ServicesActivity.this, AftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_42_day_examination")) {
                    Intent intent = new Intent(ServicesActivity.this, Postpartum42ExamActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("vaccine") && item_alias.equals("vaccine_card")) {
                    Intent intent = new Intent(ServicesActivity.this, VaccineCardActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (!item_alias.equals("vaccine_card") && type_alias.equals("vaccine")) {
                    Intent intent = new Intent(ServicesActivity.this, VaccinationActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("newborn_family_visit")) {
                    Intent intent = new Intent(ServicesActivity.this, NewbornFamilyVisitActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("tcm") && (item_alias.equals("aftercare_6_month") || item_alias.equals("aftercare_12_month") || item_alias.equals("aftercare_18_month") || item_alias.equals("aftercare_24_month") || item_alias.equals("aftercare_30_month") || item_alias.equals("aftercare_3_year"))) {
                    Intent intent = new Intent(ServicesActivity.this, TcmAftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_1_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare1Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_12_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare12Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_18_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare18Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_24_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare24Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_30_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare30Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && (item_alias.equals("aftercare_4_year") || item_alias.equals("aftercare_5_year") || item_alias.equals("aftercare_6_year"))) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare4To6Year.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_year")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare3Year.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("psychiatric") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4") || item_alias.equals("aftercare_5") || item_alias.equals("aftercare_6") || item_alias.equals("aftercare_7") || item_alias.equals("aftercare_8"))) {
                    Intent intent = new Intent(ServicesActivity.this, PsychiatricAftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("tcm") && item_alias.equals("constitution_identification")) {
                    Intent intent = new Intent(ServicesActivity.this, OldIdentifyActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare3Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_6_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare6Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_8_month")) {
                    Intent intent = new Intent(ServicesActivity.this, Aftercare8Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            type_alias + ":" + item_alias + " not written",
                            Toast.LENGTH_LONG).show();
                }
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
                Intent intent = new Intent(ServicesActivity.this, Main2Activity.class);
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
                            JSONObject item = (JSONObject) obj.getJSONArray("list").get(i);
                            Log.e(TAG, "item: " + i + ", " + item.getString("title"));
                            Summary summary = new Summary();
                            summary.setRecordId(item.getInt("record_id"));
                            summary.setTitle(item.getString("title"));
                            summary.setClinic(item.getString("clinic"));
                            summary.setProvider(item.getString("provider"));
                            summary.setServiceTime(item.getString("service_time"));
                            summary.setTypeAlias(item.getString("type_alias"));
                            summary.setItemAlias(item.getString("item_alias"));
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
        db.deleteSummaries();

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
