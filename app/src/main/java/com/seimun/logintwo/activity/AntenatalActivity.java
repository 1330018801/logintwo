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

public class AntenatalActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antenatal);
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
               Intent intent = new Intent(AntenatalActivity.this, Main2Activity.class);
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
                                    TextView visit_date = (TextView)findViewById(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView weeks = (TextView)findViewById(R.id.weeks);
                                    weeks.setText(detail.getString("weeks"));
                                    TextView age = (TextView)findViewById(R.id.age);
                                    age.setText(detail.getString("age"));
                                    TextView husband_name = (TextView)findViewById(R.id.husband_name);
                                    husband_name.setText(detail.getString("husband_name"));
                                    TextView husband_age = (TextView)findViewById(R.id.husband_age);
                                    husband_age.setText(detail.getString("husband_age"));
                                    TextView husband_phone = (TextView)findViewById(R.id.husband_phone);
                                    husband_phone.setText(detail.getString("husband_phone"));
                                    TextView pregnant_times = (TextView)findViewById(R.id.pregnant_times);
                                    pregnant_times.setText(detail.getString("pregnant_times"));
                                    TextView natural_production = (TextView)findViewById(R.id.natural_production);
                                    natural_production.setText(detail.getString("natural_production"));
                                    TextView surgery_production = (TextView)findViewById(R.id.surgery_production);
                                    surgery_production.setText(detail.getString("surgery_production"));
                                    TextView last_menstruation = (TextView)findViewById(R.id.last_menstruation);
                                    last_menstruation.setText(detail.getString("last_menstruation"));
                                    TextView due_date = (TextView)findViewById(R.id.due_date);
                                    due_date.setText(detail.getString("due_date"));
                                    TextView disease_history = (TextView)findViewById(R.id.disease_history);
                                    disease_history.setText(detail.getString("disease_history"));
                                    TextView family_history = (TextView)findViewById(R.id.family_history);
                                    family_history.setText(detail.getString("family_history"));
                                    TextView personal_history = (TextView)findViewById(R.id.personal_history);
                                    personal_history.setText(detail.getString("family_history"));
                                    TextView gynaecology_surgery_history = (TextView)findViewById(R.id.gynaecology_surgery_history);
                                    gynaecology_surgery_history.setText(detail.getString("gynaecology_surgery_history"));
                                    TextView miscarriage = (TextView)findViewById(R.id.miscarriage);
                                    miscarriage.setText(detail.getString("miscarriage"));
                                    TextView dead_fetus = (TextView)findViewById(R.id.dead_fetus);
                                    dead_fetus.setText(detail.getString("dead_fetus"));
                                    TextView still_birth = (TextView)findViewById(R.id.still_birth);
                                    still_birth.setText(detail.getString("still_birth"));
                                    TextView newnatal_death = (TextView)findViewById(R.id.newnatal_death);
                                    newnatal_death.setText(detail.getString("newnatal_death"));
                                    TextView birth_defect = (TextView)findViewById(R.id.birth_defect);
                                    birth_defect.setText(detail.getString("birth_defect"));
                                    TextView height = (TextView)findViewById(R.id.height);
                                    height.setText(detail.getString("height"));
                                    TextView weight = (TextView)findViewById(R.id.weight);
                                    weight.setText(detail.getString("weight"));
                                    TextView bmi = (TextView)findViewById(R.id.bmi);
                                    bmi.setText(detail.getString("bmi"));
                                    TextView sbp = (TextView)findViewById(R.id.sbp);
                                    sbp.setText(detail.getString("sbp"));
                                    TextView dbp = (TextView)findViewById(R.id.dbp);
                                    dbp.setText(detail.getString("dbp"));
                                    TextView ausculate_heart = (TextView)findViewById(R.id.ausculate_heart);
                                    ausculate_heart.setText(detail.getString("ausculate_heart"));
                                    TextView ausculate_heart_abnormal = (TextView)findViewById(R.id.ausculate_heart_abnormal);
                                    ausculate_heart_abnormal.setText(detail.getString("ausculate_heart_abnormal"));
                                    TextView ausculate_lung = (TextView)findViewById(R.id.ausculate_lung);
                                    ausculate_lung.setText(detail.getString("ausculate_lung"));
                                    TextView ausculate_lung_abnormal = (TextView)findViewById(R.id.ausculate_lung_abnormal);
                                    ausculate_lung_abnormal.setText(detail.getString("ausculate_lung_abnormal"));
                                    TextView vulva = (TextView)findViewById(R.id.vulva);
                                    vulva.setText(detail.getString("vulva"));
                                    TextView vulva_abnormal = (TextView)findViewById(R.id.vulva_abnormal);
                                    vulva_abnormal.setText(detail.getString("vulva_abnormal"));
                                    TextView vagina = (TextView)findViewById(R.id.vagina);
                                    vagina.setText(detail.getString("vagina"));
                                    TextView vagina_abnormal = (TextView)findViewById(R.id.vagina_abnormal);
                                    vagina_abnormal.setText(detail.getString("vagina_abnormal"));
                                    TextView cervix = (TextView)findViewById(R.id.cervix);
                                    cervix.setText(detail.getString("cervix"));
                                    TextView uteri = (TextView)findViewById(R.id.uteri);
                                    uteri.setText(detail.getString("uteri"));
                                    TextView uteri_abnormal = (TextView)findViewById(R.id.uteri_abnormal);
                                    uteri_abnormal.setText(detail.getString("uteri_abnormal"));
                                    TextView accessory = (TextView)findViewById(R.id.accessory);
                                    accessory.setText(detail.getString("accessory"));
                                    TextView accessory_abnormal = (TextView)findViewById(R.id.accessory_abnormal);
                                    accessory_abnormal.setText(detail.getString("accessory_abnormal"));
                                    if(!detail.getString("hemoglobin").equals("null")) {
                                        TextView hemoglobin = (TextView) findViewById(R.id.hemoglobin);
                                        hemoglobin.setText(detail.getString("hemoglobin"));
                                    }
                                    if(!detail.getString("leukocyte").equals("null")) {
                                        TextView leukocyte = (TextView) findViewById(R.id.leukocyte);
                                        leukocyte.setText(detail.getString("leukocyte"));
                                    }
                                    if(!detail.getString("thrombocyte").equals("null")) {
                                        TextView thrombocyte = (TextView) findViewById(R.id.thrombocyte);
                                        thrombocyte.setText(detail.getString("thrombocyte"));
                                    }
                                    if(!detail.getString("urine_protein").equals("null")) {
                                        TextView urine_protein = (TextView) findViewById(R.id.urine_protein);
                                        urine_protein.setText(detail.getString("urine_protein"));
                                    }
                                    if(!detail.getString("urine_glucose").equals("null")) {
                                        TextView urine_glucose = (TextView) findViewById(R.id.urine_glucose);
                                        urine_glucose.setText(detail.getString("urine_glucose"));
                                    }
                                    if(!detail.getString("urine_ket").equals("null")) {
                                        TextView urine_ket = (TextView) findViewById(R.id.urine_ket);
                                        urine_ket.setText(detail.getString("urine_ket"));
                                    }
                                    if(!detail.getString("urine_ery").equals("null")) {
                                        TextView urine_ery = (TextView) findViewById(R.id.urine_ery);
                                        urine_ery.setText(detail.getString("urine_ery"));
                                    }
                                    if(!detail.getString("blood_type_abo").equals("null")) {
                                        TextView blood_type_abo = (TextView) findViewById(R.id.blood_type_abo);
                                        blood_type_abo.setText(detail.getString("blood_type_abo"));
                                    }
                                    if(!detail.getString("blood_type_rh").equals("null")) {
                                        TextView blood_type_rh = (TextView) findViewById(R.id.blood_type_rh);
                                        blood_type_rh.setText(detail.getString("blood_type_rh"));
                                    }
                                    if(!detail.getString("blood_glucose").equals("null")) {
                                        TextView blood_glucose = (TextView) findViewById(R.id.blood_glucose);
                                        blood_glucose.setText(detail.getString("blood_glucose"));
                                    }
                                    if(!detail.getString("sgpt").equals("null")) {
                                        TextView sgpt = (TextView) findViewById(R.id.sgpt);
                                        sgpt.setText(detail.getString("sgpt"));
                                    }
                                    if(!detail.getString("sgot").equals("null")) {
                                        TextView sgot = (TextView) findViewById(R.id.sgot);
                                        sgot.setText(detail.getString("sgot"));
                                    }
                                    if(!detail.getString("albumin").equals("null")) {
                                        TextView albumin = (TextView) findViewById(R.id.albumin);
                                        albumin.setText(detail.getString("albumin"));
                                    }
                                    if(!detail.getString("tbil").equals("null")) {
                                        TextView tbil = (TextView) findViewById(R.id.tbil);
                                        tbil.setText(detail.getString("tbil"));
                                    }
                                    if(!detail.getString("dbil").equals("null")) {
                                        TextView dbil = (TextView) findViewById(R.id.dbil);
                                        dbil.setText(detail.getString("dbil"));
                                    }
                                    if(!detail.getString("scr").equals("null")) {
                                        TextView scr = (TextView) findViewById(R.id.scr);
                                        scr.setText(detail.getString("scr"));
                                    }
                                    if(!detail.getString("bun").equals("null")) {
                                        TextView bun = (TextView) findViewById(R.id.bun);
                                        bun.setText(detail.getString("bun"));
                                    }

                                    if (!detail.getString("surface_antigen").equals("null")) {
                                        TextView surface_antigen = (TextView)findViewById(R.id.surface_antigen);
                                        surface_antigen.setText(detail.getString("surface_antigen"));
                                    }

                                    if(!detail.getString("surface_antibody").equals("null")){
                                        TextView surface_antibody = (TextView)findViewById(R.id.surface_antibody);
                                        surface_antibody.setText(detail.getString("surface_antibody"));
                                    }
                                    if(!detail.getString("e_antigen").equals("null")) {
                                        TextView e_antigen = (TextView) findViewById(R.id.e_antigen);
                                        e_antigen.setText(detail.getString("e_antigen"));
                                    }
                                    if(!detail.getString("e_antibody").equals("null")) {
                                        TextView e_antibody = (TextView) findViewById(R.id.e_antibody);
                                        e_antibody.setText(detail.getString("e_antibody"));
                                    }
                                    if(!detail.getString("core_antibody").equals("null")) {
                                        TextView core_antibody = (TextView) findViewById(R.id.core_antibody);
                                        core_antibody.setText(detail.getString("core_antibody"));
                                    }
                                    TextView total_evaluation = (TextView)findViewById(R.id.total_evaluation);
                                    total_evaluation.setText(detail.getString("total_evaluation"));
                                    TextView guide = (TextView)findViewById(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    if(!detail.getString("transfer").equals("null")) {
                                        TextView transfer = (TextView) findViewById(R.id.transfer);
                                        transfer.setText(detail.getString("transfer"));
                                    }
                                    if(!detail.getString("transfer_reason").equals("null")) {
                                        TextView transfer_reason = (TextView) findViewById(R.id.transfer_reason);
                                        transfer_reason.setText(detail.getString("transfer_reason"));
                                    }
                                    if(!detail.getString("transfer_hospital").equals("null")) {
                                        TextView transfer_hospital = (TextView) findViewById(R.id.transfer_hospital);
                                        transfer_hospital.setText(detail.getString("transfer_hospital"));
                                    }
                                    TextView next_visit_date = (TextView)findViewById(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView doctor_signature = (TextView)findViewById(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));


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
        db.deleteSummaries();

        Intent intent = new Intent(AntenatalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
 