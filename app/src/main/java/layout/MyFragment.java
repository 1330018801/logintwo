package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seimun.logintwo.R;
import com.seimun.logintwo.activity.InfoActivity;
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.activity.ServicesActivity;
import com.seimun.logintwo.helper.SQLiteHandler;
import com.seimun.logintwo.helper.SessionManager;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private TextView txtName;
    private TextView txtMobile;
    private Button btnLogout;
    private Button btnInfo;
    private Button btnServices;

    private SQLiteHandler db;
    private SessionManager session;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,null);
        txtName = (TextView)view.findViewById(R.id.name);
        txtMobile = (TextView)view.findViewById(R.id.mobile);
        btnLogout = (Button)view.findViewById(R.id.btnLogout);
        btnInfo = (Button)view.findViewById(R.id.btnInfo);
        btnServices = (Button)view.findViewById(R.id.btnServices);

        db = new SQLiteHandler(getContext());
        session = new SessionManager(getContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String mobile = user.get("mobile");
        txtName.setText("欢迎系统用户：" + name);
        txtMobile.setText("登陆手机号码：" + mobile);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
               return;
            }
        });

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ServicesActivity.class);
                startActivity(intent);
               return;
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
