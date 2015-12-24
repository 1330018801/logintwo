package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.R;
import com.seimun.logintwo.ZiLiaoActivity;
import com.seimun.logintwo.activity.DetailActivity;
import com.seimun.logintwo.activity.InfoActivity;
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.app.AppConfig;
import com.seimun.logintwo.app.AppController;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private TextView txtName;
    private TextView txtMobile;
    private Button btnLogout;
    private TextView zlxx;



    private SQLiteHandler db;
    private SessionManager session;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my,null);
        txtName = (TextView)view.findViewById(R.id.name);
        txtMobile = (TextView)view.findViewById(R.id.mobile);
        btnLogout = (Button)view.findViewById(R.id.btnLogout);

        zlxx = (TextView) view.findViewById(R.id.zlxx);
        zlxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZiLiaoActivity.class);
                startActivity(intent);
                return;
            }
        });


        TextView shxg = (TextView) view.findViewById(R.id.shxg);
        shxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),InfoActivity.class);
                startActivity(intent);


            }
        });


        db = new SQLiteHandler(getContext());
        session = new SessionManager(getContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        final HashMap<String, String> user = db.getUserDetails();

        StringRequest infoReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        TextView gender = (TextView)view.findViewById(R.id.gender);
                        gender.setText(jObj.getString("gender"));

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("resident_id", user.get("resident_id"));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(infoReq);

//        HashMap<String, String> user = db.getUserDetails();




        String name = user.get("name");
        String mobile = user.get("mobile");
        txtName.setText( name);
        txtMobile.setText("登陆账号：" + mobile);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });




        return view;
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteSummaries();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return;
    }


}
