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

public class NewbornFamilyVisitActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newborn_family_visit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewbornFamilyVisitActivity.this,Main2Activity.class);
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

        Intent intent = getIntent();
        final Integer record_id = intent.getIntExtra("record_id", 0);

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
                                    TextView name = (TextView) findViewById(R.id.name);
                                    name.setText(user.get("name"));
                                    TextView gender = (TextView) findViewById(R.id.gender);
                                    gender.setText(detail.getString("gender"));
                                    TextView birthday = (TextView) findViewById(R.id.birthday);
                                    birthday.setText(detail.getString("birthday"));
                                    TextView identity = (TextView) findViewById(R.id.identity);
                                    identity .setText(detail.getString("identity"));
                                    TextView address = (TextView) findViewById(R.id.address);
                                    address .setText(detail.getString("address"));
                                    TextView father_name = (TextView) findViewById(R.id.father_name);
                                    father_name.setText(detail.getString("father_name"));
                                    TextView father_occupation = (TextView) findViewById(R.id.father_occupation);
                                    father_occupation.setText(detail.getString("father_occupation"));
                                    TextView father_contact_number = (TextView) findViewById(R.id.father_contact_number);
                                    father_contact_number .setText(detail.getString("father_contact_number"));
                                    TextView father_birthday = (TextView) findViewById(R.id.father_birthday);
                                    father_birthday.setText(detail.getString("father_birthday"));
                                    TextView mother_name = (TextView) findViewById(R.id.mother_name);
                                    mother_name .setText(detail.getString("mother_name"));
                                    TextView mother_occupation = (TextView) findViewById(R.id.mother_occupation);
                                    mother_occupation .setText(detail.getString("mother_occupation"));
                                    TextView mother_contact_number = (TextView) findViewById(R.id.mother_contact_number);
                                    mother_contact_number .setText(detail.getString("mother_contact_number"));
                                    TextView mother_birthday = (TextView) findViewById(R.id.mother_birthday);
                                    mother_birthday .setText(detail.getString("mother_birthday"));
                                    TextView gestational_weeks = (TextView) findViewById(R.id.gestational_weeks);
                                    gestational_weeks .setText(detail.getString("gestational_weeks"));
                                    TextView mother_gestational_disease = (TextView) findViewById(R.id.mother_gestational_disease);
                                    mother_gestational_disease .setText(detail.getString("mother_gestational_disease"));
                                    TextView mother_gestational_disease_extra = (TextView) findViewById(R.id.mother_gestational_disease_extra);
                                    mother_gestational_disease_extra .setText(detail.getString("mother_gestational_disease_extra"));
                                    TextView deliver_institution = (TextView) findViewById(R.id.deliver_institution);
                                    deliver_institution .setText(detail.getString("deliver_institution"));
                                    TextView birth_situation = (TextView) findViewById(R.id.birth_situation);
                                    birth_situation .setText(detail.getString("birth_situation"));
                                    TextView birth_situation_extra = (TextView) findViewById(R.id.birth_situation_extra);
                                    birth_situation_extra .setText(detail.getString("birth_situation_extra"));
                                    TextView newborn_asphyxia = (TextView) findViewById(R.id.newborn_asphyxia);
                                    newborn_asphyxia .setText(detail.getString("newborn_asphyxia"));
                                    TextView apgar_score = (TextView) findViewById(R.id.apgar_score);
                                    apgar_score .setText(detail.getString("apgar_score"));
                                    TextView  malformation_or_not= (TextView) findViewById(R.id.malformation_or_not);
                                    malformation_or_not .setText(detail.getString("malformation_or_not"));
                                    TextView malformation_extra = (TextView) findViewById(R.id.malformation_extra);
                                    malformation_extra .setText(detail.getString("malformation_extra"));
                                    TextView newborn_hearing_screening = (TextView) findViewById(R.id.newborn_hearing_screening);
                                    newborn_hearing_screening .setText(detail.getString("newborn_hearing_screening"));
                                    TextView newborn_disease_screening = (TextView) findViewById(R.id.newborn_disease_screening);
                                    newborn_disease_screening.setText(detail.getString("newborn_disease_screening"));
                                    TextView newborn_disease_screening_extra = (TextView) findViewById(R.id.newborn_disease_screening_extra);
                                    newborn_disease_screening_extra.setText(detail.getString("newborn_disease_screening_extra"));
                                    TextView newborn_birth_weight = (TextView) findViewById(R.id.newborn_birth_weight);
                                    newborn_birth_weight.setText(detail.getString("newborn_birth_weight"));
                                    TextView now_weight = (TextView) findViewById(R.id.now_weight);
                                    now_weight .setText(detail.getString("now_weight"));
                                    TextView birth_height = (TextView) findViewById(R.id.birth_height);
                                    birth_height .setText(detail.getString("birth_height"));
                                    TextView feed_way = (TextView) findViewById(R.id.feed_way);
                                    feed_way .setText(detail.getString("feed_way"));
                                    if(!detail.getString("drink_milk_volume").equals("null")) {
                                        TextView drink_milk_volume = (TextView) findViewById(R.id.drink_milk_volume);
                                        drink_milk_volume.setText(detail.getString("drink_milk_volume"));
                                    }
                                    if(!detail.getString("drink_milk_times").equals("null")) {
                                        TextView drink_milk_times = (TextView) findViewById(R.id.drink_milk_times);
                                        drink_milk_times.setText(detail.getString("drink_milk_times"));
                                    }
                                    if(!detail.getString("emesis").equals("null")) {
                                        TextView emesis = (TextView) findViewById(R.id.emesis);
                                        emesis.setText(detail.getString("emesis"));
                                    }
                                    if(!detail.getString("shit").equals("null")) {
                                        TextView shit = (TextView) findViewById(R.id.shit);
                                        shit.setText(detail.getString("shit"));
                                    }
                                    if(!detail.getString("shit_times").equals("null")) {
                                        TextView shit_times = (TextView) findViewById(R.id.shit_times);
                                        shit_times.setText(detail.getString("shit_times"));
                                    }
                                    TextView body_temperature = (TextView) findViewById(R.id.body_temperature);
                                    body_temperature .setText(detail.getString("body_temperature"));
                                    TextView pulse = (TextView) findViewById(R.id.pulse);
                                    pulse .setText(detail.getString("pulse"));
                                    TextView breath_frequency = (TextView) findViewById(R.id.breath_frequency);
                                    breath_frequency .setText(detail.getString("breath_frequency"));
                                    TextView complexion = (TextView) findViewById(R.id.complexion);
                                    complexion .setText(detail.getString("complexion"));
                                    TextView complexion_extra = (TextView) findViewById(R.id.complexion_extra);
                                    complexion_extra .setText(detail.getString("complexion_extra"));
                                    TextView icterus_position = (TextView) findViewById(R.id.icterus_position);
                                    icterus_position .setText(detail.getString("icterus_position"));
                                    TextView bregma_x = (TextView) findViewById(R.id.bregma_x);
                                    bregma_x .setText(detail.getString("bregma_x"));
                                    TextView bregma_y = (TextView) findViewById(R.id.bregma_y);
                                    bregma_y .setText(detail.getString("bregma_y"));
                                    TextView bregma_1 = (TextView) findViewById(R.id.bregma_1);
                                    bregma_1 .setText(detail.getString("bregma_1"));
                                    TextView bregma_1_extra = (TextView) findViewById(R.id.bregma_1_extra);
                                    bregma_1_extra .setText(detail.getString("bregma_1_extra"));
                                    TextView eye_appearance = (TextView) findViewById(R.id.eye_appearance);
                                    eye_appearance .setText(detail.getString("eye_appearance"));
                                    TextView eye_appearance_abnormal = (TextView) findViewById(R.id.eye_appearance_abnormal);
                                    eye_appearance_abnormal.setText(detail.getString("eye_appearance_abnormal"));
                                    TextView all_fours_activity = (TextView) findViewById(R.id.all_fours_activity);
                                    all_fours_activity .setText(detail.getString("all_fours_activity"));
                                    TextView all_fours_activity_abnormal = (TextView) findViewById(R.id.all_fours_activity_abnormal);
                                    all_fours_activity_abnormal .setText(detail.getString("all_fours_activity_abnormal"));
                                    TextView ear_appearance = (TextView) findViewById(R.id.ear_appearance);
                                    ear_appearance .setText(detail.getString("ear_appearance"));
                                    TextView ear_appearance_abnormal = (TextView) findViewById(R.id.ear_appearance_abnormal);
                                    ear_appearance_abnormal .setText(detail.getString("ear_appearance_abnormal"));
                                    TextView neck_enclosed_mass = (TextView) findViewById(R.id.neck_enclosed_mass);
                                    neck_enclosed_mass .setText(detail.getString("neck_enclosed_mass"));
                                    TextView neck_enclosed_mass_yes = (TextView) findViewById(R.id.neck_enclosed_mass_yes);
                                    neck_enclosed_mass_yes .setText(detail.getString("neck_enclosed_mass_yes"));
                                    TextView nose = (TextView) findViewById(R.id.nose);
                                    nose .setText(detail.getString("nose"));
                                    TextView nose_abnormal = (TextView) findViewById(R.id.nose_abnormal);
                                    nose_abnormal .setText(detail.getString("nose_abnormal"));
                                    TextView skin = (TextView) findViewById(R.id.skin);
                                    skin.setText(detail.getString("skin"));
                                    TextView skin_extra = (TextView) findViewById(R.id.skin_extra);
                                    skin_extra .setText(detail.getString("skin_extra"));
                                    TextView oral_cavity = (TextView) findViewById(R.id.oral_cavity);
                                    oral_cavity .setText(detail.getString("oral_cavity"));
                                    TextView oral_cavity_abnormal = (TextView) findViewById(R.id.oral_cavity_abnormal);
                                    oral_cavity_abnormal .setText(detail.getString("oral_cavity_abnormal"));
                                    TextView anus = (TextView) findViewById(R.id.anus);
                                    anus .setText(detail.getString("anus"));
                                    TextView anus_abnormal = (TextView) findViewById(R.id.anus_abnormal);
                                    anus_abnormal .setText(detail.getString("anus_abnormal"));
                                    TextView heart_lung_auscultation = (TextView) findViewById(R.id.heart_lung_auscultation);
                                    heart_lung_auscultation .setText(detail.getString("heart_lung_auscultation"));
                                    TextView heart_lung_auscultation_abnormal = (TextView) findViewById(R.id.heart_lung_auscultation_abnormal);
                                    heart_lung_auscultation_abnormal .setText(detail.getString("heart_lung_auscultation_abnormal"));
                                    TextView externalia = (TextView) findViewById(R.id.externalia);
                                    externalia .setText(detail.getString("externalia"));
                                    TextView externalia_abnormal = (TextView) findViewById(R.id.externalia_abnormal);
                                    externalia_abnormal .setText(detail.getString("externalia_abnormal"));
                                    TextView abdomen_palpation = (TextView) findViewById(R.id.abdomen_palpation);
                                    abdomen_palpation .setText(detail.getString("abdomen_palpation"));
                                    TextView abdomen_palpation_abnormal = (TextView) findViewById(R.id.abdomen_palpation_abnormal);
                                    abdomen_palpation_abnormal .setText(detail.getString("abdomen_palpation_abnormal"));
                                    TextView spine = (TextView) findViewById(R.id.spine);
                                    spine.setText(detail.getString("spine"));
                                    TextView spine_abnormal = (TextView) findViewById(R.id.spine_abnormal);
                                    spine_abnormal.setText(detail.getString("spine_abnormal"));
                                    TextView navel = (TextView) findViewById(R.id.navel);
                                    navel .setText(detail.getString("navel"));
                                    TextView navel_extra = (TextView) findViewById(R.id.navel_extra);
                                    navel_extra .setText(detail.getString("navel_extra"));
                                    TextView transfer_treatment_suggestion = (TextView) findViewById(R.id.transfer_treatment_suggestion);
                                    transfer_treatment_suggestion  .setText(detail.getString("transfer_treatment_suggestion"));
                                    TextView transfer_treatment_suggestion_reason = (TextView) findViewById(R.id.transfer_treatment_suggestion_reason);
                                    transfer_treatment_suggestion_reason .setText(detail.getString("transfer_treatment_suggestion_reason"));
                                    TextView transfer_treatment_suggestion_institution = (TextView) findViewById(R.id.transfer_treatment_suggestion_institution);
                                    transfer_treatment_suggestion_institution .setText(detail.getString("transfer_treatment_suggestion_institution"));
                                    TextView guide = (TextView) findViewById(R.id.guide);
                                    guide .setText(detail.getString("guide"));
                                    TextView doctor_signature = (TextView) findViewById(R.id.doctor_signature);
                                    doctor_signature .setText(detail.getString("doctor_signature"));
                                    TextView next_visit_date = (TextView) findViewById(R.id.next_visit_date);
                                    next_visit_date .setText(detail.getString("next_visit_date"));
                                    TextView next_visit_place = (TextView) findViewById(R.id.next_visit_place);
                                    next_visit_place .setText(detail.getString("next_visit_place"));



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

        Intent intent = new Intent(NewbornFamilyVisitActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

