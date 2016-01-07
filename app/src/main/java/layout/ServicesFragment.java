package layout;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.R;
import com.seimun.logintwo.activity.Aftercare12Month;
import com.seimun.logintwo.activity.Aftercare18Month;
import com.seimun.logintwo.activity.Aftercare1Month;
import com.seimun.logintwo.activity.Aftercare24Month;
import com.seimun.logintwo.activity.Aftercare30Month;
import com.seimun.logintwo.activity.Aftercare3Month;
import com.seimun.logintwo.activity.Aftercare3Year;
import com.seimun.logintwo.activity.Aftercare4To6Year;
import com.seimun.logintwo.activity.Aftercare6Month;
import com.seimun.logintwo.activity.Aftercare8Month;
import com.seimun.logintwo.activity.AftercareActivity;
import com.seimun.logintwo.activity.AntenatalActivity;
import com.seimun.logintwo.activity.BodyExamActivity;
import com.seimun.logintwo.activity.DiabetesAftercareActivity;
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.activity.NewbornFamilyVisitActivity;
import com.seimun.logintwo.activity.OldIdentifyActivity;
import com.seimun.logintwo.activity.Postpartum42ExamActivity;
import com.seimun.logintwo.activity.PostpartumVisitActivity;
import com.seimun.logintwo.activity.PsychiatricAftercareActivity;
import com.seimun.logintwo.activity.TcmAftercareActivity;
import com.seimun.logintwo.activity.VaccinationActivity;
import com.seimun.logintwo.activity.VaccineCardActivity;
import com.seimun.logintwo.activity.hypertension_aftercare;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {
    private static final String TAG = ServicesFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<Summary> summaryList = new ArrayList<>();
    private SummaryListAdapter adapter;

    private SQLiteHandler db;
    private SessionManager session;


    public ServicesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_services,null);
        // Inflate the layout for this fragment
        ListView listView = (ListView) view.findViewById(R.id.services1);
        adapter = new SummaryListAdapter(getActivity(), summaryList);
        listView.setAdapter(adapter);

        db = new SQLiteHandler(getContext());
        session = new SessionManager(getContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        final HashMap<String, String> user = db.getUserDetails();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading ...");
        pDialog.show();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Summary summary = db.getSummaryDetails(position + 1);

                String type_alias = summary.getTypeAlias();
                String item_alias = summary.getItemAlias();
                if (type_alias.equals("pregnant") && item_alias.equals("aftercare_1")) {
                    Intent intent = new Intent(getActivity(), AntenatalActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_visit")) {
                    Intent intent = new Intent(getActivity(), PostpartumVisitActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("hypertension") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4"))) {
                    Intent intent = new Intent(getActivity(), hypertension_aftercare.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("diabetes") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4"))) {
                    Intent intent = new Intent(getActivity(), DiabetesAftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (item_alias.equals("body_exam_table") || item_alias.equals("physical_examination")) {
                    Intent intent = new Intent(getActivity(), BodyExamActivity.class);

                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("pregnant") && (item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4") || item_alias.equals("aftercare_5"))) {
                    Intent intent = new Intent(getActivity(), AftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("pregnant") && item_alias.equals("postpartum_42_day_examination")) {
                    Intent intent = new Intent(getActivity(), Postpartum42ExamActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("vaccine") && item_alias.equals("vaccine_card")) {
                    Intent intent = new Intent(getActivity(), VaccineCardActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (!item_alias.equals("vaccine_card") && type_alias.equals("vaccine")) {
                    Intent intent = new Intent(getActivity(), VaccinationActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("newborn_family_visit")) {
                    Intent intent = new Intent(getActivity(), NewbornFamilyVisitActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("tcm") && (item_alias.equals("aftercare_6_month") || item_alias.equals("aftercare_12_month") || item_alias.equals("aftercare_18_month") || item_alias.equals("aftercare_24_month") || item_alias.equals("aftercare_30_month") || item_alias.equals("aftercare_3_year"))) {
                    Intent intent = new Intent(getActivity(), TcmAftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_1_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare1Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_12_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare12Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_18_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare18Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_24_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare24Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_30_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare30Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && (item_alias.equals("aftercare_4_year") || item_alias.equals("aftercare_5_year") || item_alias.equals("aftercare_6_year"))) {
                    Intent intent = new Intent(getActivity(), Aftercare4To6Year.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_year")) {
                    Intent intent = new Intent(getActivity(), Aftercare3Year.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("psychiatric") && (item_alias.equals("aftercare_1") || item_alias.equals("aftercare_2") || item_alias.equals("aftercare_3") || item_alias.equals("aftercare_4") || item_alias.equals("aftercare_5") || item_alias.equals("aftercare_6") || item_alias.equals("aftercare_7") || item_alias.equals("aftercare_8"))) {
                    Intent intent = new Intent(getActivity(), PsychiatricAftercareActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("tcm") && item_alias.equals("constitution_identification")) {
                    Intent intent = new Intent(getActivity(), OldIdentifyActivity.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_3_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare3Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_6_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare6Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else if (type_alias.equals("child") && item_alias.equals("aftercare_8_month")) {
                    Intent intent = new Intent(getActivity(), Aftercare8Month.class);
                    intent.putExtra("record_id", summary.getRecordId());
                    startActivity(intent);
                    return;
                } else {
                    Toast.makeText(getContext(),
                            type_alias + ":" + item_alias + " not written",
                            Toast.LENGTH_LONG).show();
                }
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
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("resident_id", user.get("resident_id"));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(summaryReq);
        return view;
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteSummaries();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return ;
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


