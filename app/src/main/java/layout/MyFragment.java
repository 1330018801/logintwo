package layout;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.seimun.logintwo.InternalMedicineActivity;
import com.seimun.logintwo.LifeStyleActivity;
import com.seimun.logintwo.PastHistoryActivity;
import com.seimun.logintwo.R;
import com.seimun.logintwo.SurgeryActivity;
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
    private TextView data,pasthistory,surgery,medicine,about;
    private Drawable zlxx,shxg,jwbs,wkqk,neqk,gywm;





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





        data = (TextView) view.findViewById(R.id.data);
        zlxx = getResources().getDrawable(R.drawable.zlxx);
        zlxx.setBounds(0,0,80,60);
        data.setCompoundDrawables(zlxx,null,null,null);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZiLiaoActivity.class);
                startActivity(intent);
            }
        });


        TextView habits = (TextView) view.findViewById(R.id.habits);
        shxg = getResources().getDrawable(R.drawable.shxg);
        shxg.setBounds(0,0,80,60);
        habits.setCompoundDrawables(shxg,null,null,null);
        habits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LifeStyleActivity.class);
                startActivity(intent);
            }
        });

        pasthistory = (TextView) view.findViewById(R.id.pasthistory);
        jwbs = getResources().getDrawable(R.drawable.jwbs);
        jwbs.setBounds(0,0,60,80);
        pasthistory.setCompoundDrawables(jwbs,null,null,null);
        pasthistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PastHistoryActivity.class);
                startActivity(intent);

            }
        });


        about = (TextView) view.findViewById(R.id.about);
        gywm = getResources().getDrawable(R.drawable.gywm);
        gywm.setBounds(0,0,80,80);
        about.setCompoundDrawables(gywm,null,null,null);


        db = new SQLiteHandler(getContext());
        session = new SessionManager(getContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        final HashMap<String, String> user = db.getUserDetails();



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
