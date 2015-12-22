package com.seimun.logintwo.activity;

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
import com.seimun.logintwo.R;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PsychiatricAftercareActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychiatric_aftercare);
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
        // final HashMap<String, String> user = db.getUserDetails();


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
                Intent intent = new Intent(PsychiatricAftercareActivity.this, Main2Activity.class);
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
                                    TextView visit_date = (TextView) findViewById(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView dangerousness = (TextView) findViewById(R.id.dangerousness);
                                    dangerousness .setText(detail.getString("dangerousness"));
                                    TextView insight = (TextView) findViewById(R.id.insight);
                                    insight .setText(detail.getString("insight"));
                                    TextView sleep_situation = (TextView) findViewById(R.id.sleep_situation);
                                    sleep_situation .setText(detail.getString("sleep_situation"));
                                    TextView diet_situation = (TextView) findViewById(R.id.diet_situation);
                                    diet_situation .setText(detail.getString("diet_situation"));
                                    TextView society_function_housework = (TextView) findViewById(R.id.society_function_housework);
                                    society_function_housework .setText(detail.getString("society_function_housework"));
                                    TextView society_function_individual_life_care = (TextView) findViewById(R.id.society_function_individual_life_care);
                                    society_function_individual_life_care .setText(detail.getString("society_function_individual_life_care"));
                                    TextView society_function_productive_work = (TextView) findViewById(R.id.society_function_productive_work);
                                    society_function_productive_work .setText(detail.getString("society_function_productive_work"));
                                    TextView society_function_learn_ability = (TextView) findViewById(R.id.society_function_learn_ability);
                                    society_function_learn_ability.setText(detail.getString("society_function_learn_ability"));
                                    TextView society_function_social_interpersonal = (TextView) findViewById(R.id.society_function_social_interpersonal);
                                    society_function_social_interpersonal .setText(detail.getString("society_function_social_interpersonal"));
                                    TextView disease_family_society_effect_mild_disturbance = (TextView) findViewById(R.id.disease_family_society_effect_mild_disturbance);
                                    disease_family_society_effect_mild_disturbance .setText(detail.getString("disease_family_society_effect_mild_disturbance"));
                                    TextView disease_family_society_effect_disturbance = (TextView) findViewById(R.id.disease_family_society_effect_disturbance);
                                    disease_family_society_effect_disturbance .setText(detail.getString("disease_family_society_effect_disturbance"));
                                    TextView disease_family_society_effect_accident = (TextView) findViewById(R.id.disease_family_society_effect_accident);
                                    disease_family_society_effect_accident .setText(detail.getString("disease_family_society_effect_accident"));
                                    TextView disease_family_society_effect_autolesion = (TextView) findViewById(R.id.disease_family_society_effect_autolesion);
                                    disease_family_society_effect_autolesion .setText(detail.getString("disease_family_society_effect_autolesion"));
                                    TextView disease_family_society_effect_attempted_suicide = (TextView) findViewById(R.id.disease_family_society_effect_attempted_suicide);
                                    disease_family_society_effect_attempted_suicide .setText(detail.getString("disease_family_society_effect_attempted_suicide"));
                                    TextView lock_situation = (TextView) findViewById(R.id.lock_situation);
                                    lock_situation .setText(detail.getString("lock_situation"));
                                    TextView hospitalized_situation = (TextView) findViewById(R.id.hospitalized_situation);
                                    hospitalized_situation .setText(detail.getString("hospitalized_situation"));
                                    if(!detail.getString("last_hospitalized_date").equals("null")) {
                                        TextView last_hospitalized_date = (TextView) findViewById(R.id.last_hospitalized_date);
                                        last_hospitalized_date.setText(detail.getString("last_hospitalized_date"));
                                    }
                                    TextView laboratory_examination = (TextView) findViewById(R.id.laboratory_examination);
                                    laboratory_examination .setText(detail.getString("laboratory_examination"));
                                    TextView medicine_untoward_effect = (TextView) findViewById(R.id.medicine_untoward_effect);
                                    medicine_untoward_effect .setText(detail.getString("medicine_untoward_effect"));
                                    TextView medicine_untoward_effect_yes = (TextView) findViewById(R.id.medicine_untoward_effect_yes);
                                    medicine_untoward_effect_yes .setText(detail.getString("medicine_untoward_effect_yes"));
                                    TextView take_medicine_compliance = (TextView) findViewById(R.id.take_medicine_compliance);
                                    take_medicine_compliance .setText(detail.getString("take_medicine_compliance"));
                                    TextView treatment_effect = (TextView) findViewById(R.id.treatment_effect);
                                    treatment_effect .setText(detail.getString("treatment_effect"));
                                    if(!detail.getString("take_medicine_1").equals("null")) {
                                        TextView take_medicine_1 = (TextView) findViewById(R.id.take_medicine_1);
                                        take_medicine_1.setText(detail.getString("take_medicine_1"));
                                    }
                                    if(!detail.getString("take_medicine_1_per").equals("null")) {
                                        TextView take_medicine_1_per = (TextView) findViewById(R.id.take_medicine_1_per);
                                        take_medicine_1_per.setText(detail.getString("take_medicine_1_per"));
                                    }
                                    if(!detail.getString("take_medicine_1_time").equals("null")) {
                                        TextView take_medicine_1_time = (TextView) findViewById(R.id.take_medicine_1_time);
                                        take_medicine_1_time.setText(detail.getString("take_medicine_1_time"));
                                    }
                                    if(!detail.getString("take_medicine_1_mg").equals("null")) {
                                        TextView take_medicine_1_mg = (TextView) findViewById(R.id.take_medicine_1_mg);
                                        take_medicine_1_mg.setText(detail.getString("take_medicine_1_mg"));
                                    }
                                    if(!detail.getString("take_medicine_2").equals("null")) {
                                        TextView take_medicine_2 = (TextView) findViewById(R.id.take_medicine_2);
                                        take_medicine_2.setText(detail.getString("take_medicine_2"));
                                    }
                                    if(!detail.getString("take_medicine_2_per").equals("null")) {
                                        TextView take_medicine_2_per = (TextView) findViewById(R.id.take_medicine_2_per);
                                        take_medicine_2_per.setText(detail.getString("take_medicine_2_per"));
                                    }
                                    if(!detail.getString("take_medicine_2_time").equals("null")) {
                                        TextView take_medicine_2_time = (TextView) findViewById(R.id.take_medicine_2_time);
                                        take_medicine_2_time.setText(detail.getString("take_medicine_2_time"));
                                    }
                                    if(!detail.getString("take_medicine_2_mg").equals("null")) {
                                        TextView take_medicine_2_mg = (TextView) findViewById(R.id.take_medicine_2_mg);
                                        take_medicine_2_mg.setText(detail.getString("take_medicine_2_mg"));
                                    }
                                    if(!detail.getString("take_medicine_3").equals("null")) {
                                        TextView take_medicine_3 = (TextView) findViewById(R.id.take_medicine_3);
                                        take_medicine_3.setText(detail.getString("take_medicine_3"));
                                    }
                                    if(!detail.getString("take_medicine_3_per").equals("null")) {
                                        TextView take_medicine_3_per = (TextView) findViewById(R.id.take_medicine_3_per);
                                        take_medicine_3_per.setText(detail.getString("take_medicine_3_per"));
                                    }
                                    if(!detail.getString("take_medicine_3_time").equals("null")) {
                                        TextView take_medicine_3_time = (TextView) findViewById(R.id.take_medicine_3_time);
                                        take_medicine_3_time.setText(detail.getString("take_medicine_3_time"));
                                    }
                                    if(!detail.getString("take_medicine_3_mg").equals("null")) {
                                        TextView take_medicine_3_mg = (TextView) findViewById(R.id.take_medicine_3_mg);
                                        take_medicine_3_mg.setText(detail.getString("take_medicine_3_mg"));
                                    }
                                    TextView recovery_measure = (TextView) findViewById(R.id.recovery_measure);
                                    recovery_measure .setText(detail.getString("recovery_measure"));
                                    TextView recovery_measure_extra = (TextView) findViewById(R.id.recovery_measure_extra);
                                    recovery_measure_extra .setText(detail.getString("recovery_measure_extra"));
                                    TextView visit_classification = (TextView) findViewById(R.id.visit_classification);
                                    visit_classification .setText(detail.getString("visit_classification"));
                                    TextView transfer_treatment = (TextView) findViewById(R.id.transfer_treatment);
                                    transfer_treatment .setText(detail.getString("transfer_treatment"));
                                    TextView transfer_treatment_reason = (TextView) findViewById(R.id.transfer_treatment_reason);
                                    transfer_treatment_reason .setText(detail.getString("transfer_treatment_reason"));
                                    TextView transfer_treatment_institution = (TextView) findViewById(R.id.transfer_treatment_institution);
                                    transfer_treatment_institution .setText(detail.getString("transfer_treatment_institution"));
                                    TextView doctor_signature = (TextView) findViewById(R.id.doctor_signature);
                                    doctor_signature .setText(detail.getString("doctor_signature"));
                                    TextView next_visit_date = (TextView) findViewById(R.id.next_visit_date);
                                    next_visit_date .setText(detail.getString("next_visit_date"));



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

        Intent intent = new Intent(PsychiatricAftercareActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
