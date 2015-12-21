package layout;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.seimun.logintwo.Aftercare3Month;
import com.seimun.logintwo.AftercareActivity;
import com.seimun.logintwo.Main2Activity;
import com.seimun.logintwo.NewbornFamilyVisitActivity;
import com.seimun.logintwo.OldIdentifyActivity;
import com.seimun.logintwo.Postpartum42ExamActivity;
import com.seimun.logintwo.PsychiatricAftercareActivity;
import com.seimun.logintwo.R;
import com.seimun.logintwo.TcmAftercareActivity;
import com.seimun.logintwo.VaccinationActivity;
import com.seimun.logintwo.VaccineCardActivity;
import com.seimun.logintwo.activity.Aftercare12Month;
import com.seimun.logintwo.activity.Aftercare18Month;
import com.seimun.logintwo.activity.Aftercare1Month;
import com.seimun.logintwo.activity.Aftercare24Month;
import com.seimun.logintwo.activity.Aftercare30Month;
import com.seimun.logintwo.activity.Aftercare3Year;
import com.seimun.logintwo.activity.Aftercare4To6Year;
import com.seimun.logintwo.activity.Aftercare6Month;
import com.seimun.logintwo.activity.Aftercare8Month;
import com.seimun.logintwo.activity.AntenatalActivity;
import com.seimun.logintwo.activity.BodyExamActivity;
import com.seimun.logintwo.activity.DiabetesAftercareActivity;
import com.seimun.logintwo.activity.LoginActivity;
import com.seimun.logintwo.activity.MainActivity;
import com.seimun.logintwo.activity.PostpartumVisitActivity;
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


    public ServicesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

}


