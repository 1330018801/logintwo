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
import com.seimun.logintwo.activity.EducationDetail;
import com.seimun.logintwo.adapter.EducationListAdapter;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.app.AppData;
import com.seimun.logintwo.model.Education;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HealthEducationFragment extends Fragment {
    private static final String TAG = ServicesFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private EducationListAdapter adapter;

    public HealthEducationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_health_education, container, false);
        ListView listView = (ListView) view.findViewById(R.id.education_list);
        if (AppData.educationList.size() > 0) {
            AppData.educationList.clear();
        }
        adapter = new EducationListAdapter(getActivity(), AppData.educationList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading ...");
        pDialog.show();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EducationDetail.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


        final StringRequest educationReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDUCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidePDialog();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        int length = obj.getInt("length");
                        for (int i = 0; i < length; i++) {
                            JSONObject item = (JSONObject) obj.getJSONArray("list").get(i);
                            Log.e(TAG, "item: " + i + ", " + item.getString("title"));
                            Education education = new Education();
                            education.setItemId(item.getInt("item_id"));
                            education.setTitle(item.getString("title"));
                            education.setContent(item.getString("content"));
                            education.setCreateAt(item.getString("create_at"));
                            education.setCreateBy(item.getString("create_by"));
                            AppData.educationList.add(education);
                            Log.e(TAG, "summary length: " + AppData.educationList.size());
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(educationReq);
        return view;
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
