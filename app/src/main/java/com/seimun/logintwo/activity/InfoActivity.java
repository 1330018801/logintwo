package com.seimun.logintwo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class InfoActivity extends Activity {
    private static final String TAG = InfoActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnMain;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnMain = (Button)findViewById(R.id.btnMain);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        final HashMap<String, String> user = db.getUserDetails();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
                        TextView gender = (TextView)findViewById(R.id.gender);
                        TextView birthday = (TextView)findViewById(R.id.birthday);
                        TextView mobile = (TextView)findViewById(R.id.phone);
                        TextView identity = (TextView)findViewById(R.id.identity);
                        TextView contact_name=(TextView)findViewById(R.id.contact_name);
                        TextView contact_phone=(TextView)findViewById(R.id.contact_phone);
                        TextView residence_type=(TextView)findViewById(R.id.residence_type);
                        TextView nation=(TextView)findViewById(R.id.nation);
                        TextView education=(TextView)findViewById(R.id.education);
                        TextView marriage=(TextView)findViewById(R.id.marriage);
                        TextView occupation=(TextView)findViewById(R.id.occupation);
                        TextView payment_way=(TextView)findViewById(R.id.payment_way);
                        TextView blood_type=(TextView)findViewById(R.id.blood_type);
                        TextView blood_rh=(TextView)findViewById(R.id.blood_rh);
                        TextView allergy_history=(TextView)findViewById(R.id.allergy_history);
                        TextView expose_history=(TextView)findViewById(R.id.expose_history);
                        TextView disease_history=(TextView)findViewById(R.id.disease_history);
                        TextView surgery_history=(TextView)findViewById(R.id.surgery_history);
                        TextView injury_history=(TextView)findViewById(R.id.injury_history);
                        TextView transfusion_history=(TextView)findViewById(R.id.transfusion_history);
                        TextView family_history_father=(TextView)findViewById(R.id.family_history_father);
                        TextView family_history_mother=(TextView)findViewById(R.id.family_history_mother);
                        TextView family_history_sibling=(TextView)findViewById(R.id.family_history_sibling);
                        TextView family_history_children=(TextView)findViewById(R.id.family_history_children);
                        TextView genetic_disease=(TextView)findViewById(R.id.genetic_disease);
                        TextView disability=(TextView)findViewById(R.id.disability);
                        TextView surroundings_kitchen_exhaust=(TextView)findViewById(R.id.surroundings_kitchen_exhaust);
                        TextView surroundings_fuel_type=(TextView)findViewById(R.id.surroundings_fuel_type);
                        TextView surroundings_water=(TextView)findViewById(R.id.surroundings_water);
                        TextView surroundings_toilet=(TextView)findViewById(R.id.surroundings_toilet);
                        TextView surrounding_livestock_fence=(TextView)findViewById(R.id.surrounding_livestock_fence);
                        TextView payment_way_extra = (TextView) findViewById(R.id.payment_way_extra);
                        TextView allergy_history_yes_extra= (TextView) findViewById(R.id.allergy_history_yes_extra);
                        TextView family_history_father_extra = (TextView) findViewById(R.id.family_history_father_extra);
                        TextView family_history_mother_extra = (TextView) findViewById(R.id.family_history_mother_extra);
                        TextView family_history_sibling_extra = (TextView) findViewById(R.id.family_history_sibling_extra);
                        TextView family_history_children_extra = (TextView) findViewById(R.id.family_history_children_extra);
                        TextView genetic_disease_yes = (TextView) findViewById(R.id.genetic_disease_yes);
                        TextView disability_extra = (TextView) findViewById(R.id.disability_extra);
                        if(!jObj.getString("surgery_1_name").equals("null")) {
                            TextView surgery_1_name = (TextView) findViewById(R.id.surgery_1_name);
                            surgery_1_name.setText(jObj.getString("surgery_1_name"));
                        }
                        if(!jObj.getString("surgery_1_date").equals("null")) {
                            TextView surgery_1_date = (TextView) findViewById(R.id.surgery_1_date);
                            surgery_1_date.setText(jObj.getString("surgery_1_date"));
                        }
                        if(!jObj.getString("surgery_2_name").equals("null")) {
                            TextView surgery_2_name = (TextView) findViewById(R.id.surgery_2_name);
                            surgery_2_name.setText(jObj.getString("surgery_2_name"));
                        }
                        if(!jObj.getString("surgery_2_date").equals("null")) {
                            TextView surgery_2_date = (TextView) findViewById(R.id.surgery_2_date);
                            surgery_2_date.setText(jObj.getString("surgery_2_date"));
                        }
                        if(!jObj.getString("injury_1_name").equals("null")) {
                            TextView injury_1_name = (TextView) findViewById(R.id.injury_1_name);
                            injury_1_name.setText(jObj.getString("injury_1_name"));
                        }
                        if(!jObj.getString("injury_1_date").equals("null")) {
                            TextView injury_1_date = (TextView) findViewById(R.id.injury_1_date);
                            injury_1_date.setText(jObj.getString("injury_1_date"));
                        }
                        if(!jObj.getString("injury_2_name").equals("null")) {
                            TextView injury_2_name = (TextView) findViewById(R.id.injury_2_name);
                            injury_2_name.setText(jObj.getString("injury_2_name"));
                        }
                        if(!jObj.getString("injury_2_date").equals("null")) {
                            TextView injury_2_date = (TextView) findViewById(R.id.injury_2_date);
                            injury_2_date.setText(jObj.getString("injury_2_date"));
                        }
                        if(!jObj.getString("transfusion_1_reason").equals("null")) {
                            TextView transfusion_1_reason = (TextView) findViewById(R.id.transfusion_1_reason);
                            transfusion_1_reason.setText(jObj.getString("transfusion_1_reason"));
                        }
                        if(!jObj.getString("transfusion_1_date").equals("null")) {
                            TextView transfusion_1_date = (TextView) findViewById(R.id.transfusion_1_date);
                            transfusion_1_date.setText(jObj.getString("transfusion_1_date"));
                        }
                        if(!jObj.getString("transfusion_2_reason").equals("null")) {
                            TextView transfusion_2_reason = (TextView) findViewById(R.id.transfusion_2_reason);
                            transfusion_2_reason.setText(jObj.getString("transfusion_2_reason"));
                        }
                        if(!jObj.getString("transfusion_2_date").equals("null")) {
                            TextView transfusion_2_date = (TextView) findViewById(R.id.transfusion_2_date);
                            transfusion_2_date.setText(jObj.getString("transfusion_2_date"));
                        }


                        name.setText(user.get("name"));
                        gender.setText(jObj.getString("gender"));
                        birthday.setText(jObj.getString("birthday"));
                        mobile.setText(jObj.getString("phone"));
                        identity.setText(jObj.getString("identity"));
                        contact_name.setText(jObj.getString("contact_name"));
                        contact_phone.setText(jObj.getString("contact_phone"));
                        residence_type.setText(jObj.getString("residence_type"));
                        nation.setText(jObj.getString("nation"));
                        education.setText(jObj.getString("education"));
                        marriage.setText(jObj.getString("marriage"));
                        occupation.setText(jObj.getString("occupation"));
                        payment_way.setText(jObj.getString("payment_way"));
                        blood_type.setText(jObj.getString("blood_type"));
                        blood_rh.setText(jObj.getString("blood_rh"));
                        allergy_history.setText(jObj.getString("allergy_history"));
                        expose_history.setText(jObj.getString("expose_history"));
                        disease_history.setText(jObj.getString("disease_history"));
                        surgery_history.setText(jObj.getString("surgery_history"));
                        injury_history.setText(jObj.getString("injury_history"));
                        transfusion_history.setText(jObj.getString("transfusion_history"));
                        family_history_father.setText(jObj.getString("family_history_father"));
                        family_history_mother.setText(jObj.getString("family_history_mother"));
                        family_history_sibling.setText(jObj.getString("family_history_sibling"));
                        family_history_children.setText(jObj.getString("family_history_children"));
                        genetic_disease.setText(jObj.getString("genetic_disease"));
                        disability.setText(jObj.getString("disability"));
                        surroundings_kitchen_exhaust.setText(jObj.getString("surroundings_kitchen_exhaust"));
                        surroundings_fuel_type.setText(jObj.getString("surroundings_fuel_type"));
                        surroundings_water.setText(jObj.getString("surroundings_water"));
                        surroundings_toilet.setText(jObj.getString("surroundings_toilet"));
                        surrounding_livestock_fence.setText(jObj.getString("surrounding_livestock_fence"));
                        payment_way_extra.setText(jObj.getString("payment_way_extra"));
                        allergy_history_yes_extra.setText(jObj.getString("allergy_history_yes_extra"));
                        family_history_father_extra.setText(jObj.getString("family_history_father_extra"));
                        family_history_mother_extra.setText(jObj.getString("family_history_mother_extra"));
                        family_history_sibling_extra.setText(jObj.getString("family_history_sibling_extra"));
                        family_history_children_extra.setText(jObj.getString("family_history_children_extra"));
                        genetic_disease_yes.setText(jObj.getString("genetic_disease_yes"));
                        disability_extra.setText(jObj.getString("disability_extra"));


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

        Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
