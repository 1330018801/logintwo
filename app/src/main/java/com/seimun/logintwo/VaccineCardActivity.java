package com.seimun.logintwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.activity.DetailActivity;
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.activity.ServicesActivity;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VaccineCardActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnMain = (Button)findViewById(R.id.btnMain);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
         final HashMap<String, String> user = db.getUserDetails();

        Intent intent = getIntent();
        final Integer record_id = intent.getIntExtra("record_id", 0);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VaccineCardActivity.this, ServicesActivity.class);
                startActivity(intent);
                finish();

            }
        });

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
                                    TextView name = (TextView) findViewById(R.id.name);
                                    name.setText(user.get("name"));
                                    TextView gender = (TextView) findViewById(R.id.gender);
                                    gender.setText(detail.getString("gender"));
                                    TextView birth_date = (TextView) findViewById(R.id.birth_date);
                                    birth_date .setText(detail.getString("birth_date"));
                                    TextView guardian_name = (TextView) findViewById(R.id.guardian_name);
                                    guardian_name.setText(detail.getString("guardian_name"));
                                    TextView relation_to_child = (TextView) findViewById(R.id.relation_to_child);
                                    relation_to_child .setText(detail.getString("relation_to_child"));
                                    TextView contact_number = (TextView) findViewById(R.id.contact_number);
                                    contact_number.setText(detail.getString("contact_number"));
                                    TextView home_county = (TextView) findViewById(R.id.home_county);
                                    home_county.setText(detail.getString("home_county"));
                                    TextView home_town = (TextView) findViewById(R.id.home_town);
                                    home_town .setText(detail.getString("home_town"));
                                    if(!detail.getString("register_local").equals("false")) {
                                        TextView register_local = (TextView) findViewById(R.id.register_local);
                                        register_local.setText(detail.getString("register_local"));
                                    }
                                    TextView register_province = (TextView) findViewById(R.id.register_province);
                                    register_province.setText(detail.getString("register_province"));
                                    TextView register_city = (TextView) findViewById(R.id.register_city);
                                    register_city .setText(detail.getString("register_city"));
                                    TextView register_county = (TextView) findViewById(R.id.register_county);
                                    register_county .setText(detail.getString("register_county"));
                                    TextView register_town = (TextView) findViewById(R.id.register_town);
                                    register_town .setText(detail.getString("register_town"));
                                    if(!detail.getString("immigrate_time").equals("null")) {
                                        TextView immigrate_time = (TextView) findViewById(R.id.immigrate_time);
                                        immigrate_time.setText(detail.getString("immigrate_time"));
                                    }
                                    if(!detail.getString("emigrate_time").equals("null")) {
                                        TextView emigrate_time = (TextView) findViewById(R.id.emigrate_time);
                                        emigrate_time.setText(detail.getString("emigrate_time"));
                                    }
                                    TextView emigrate_reason = (TextView) findViewById(R.id.emigrate_reason);
                                    emigrate_reason.setText(detail.getString("emigrate_reason"));
                                    TextView vaccine_abnormal_reaction_history = (TextView) findViewById(R.id.vaccine_abnormal_reaction_history);
                                    vaccine_abnormal_reaction_history .setText(detail.getString("vaccine_abnormal_reaction_history"));
                                    TextView vaccinate_taboo = (TextView) findViewById(R.id.vaccinate_taboo);
                                    vaccinate_taboo .setText(detail.getString("vaccinate_taboo"));
                                    TextView infection_history = (TextView) findViewById(R.id.infection_history);
                                    infection_history .setText(detail.getString("infection_history"));
                                    TextView found_card_date = (TextView) findViewById(R.id.found_card_date);
                                    found_card_date .setText(detail.getString("found_card_date"));
                                    TextView found_card_person = (TextView) findViewById(R.id.found_card_person);
                                    found_card_person .setText(detail.getString("found_card_person"));


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

        Intent intent = new Intent(VaccineCardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
