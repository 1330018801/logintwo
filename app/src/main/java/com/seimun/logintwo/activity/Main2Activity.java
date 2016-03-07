package com.seimun.logintwo.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.seimun.logintwo.R;

import layout.HealthEducationFragment;
import layout.HospitalFragment;
import layout.MyFragment;
import layout.ServicesFragment;

public class Main2Activity extends AppCompatActivity {
    private ServicesFragment mServicesFragment;
    private HealthEducationFragment mHealthEducationFragment;
    private MyFragment mMyFragment;
    private HospitalFragment mHospitalFragment;
    private RadioGroup mRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initView();

    }


    private void initView() {

        mMyFragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mMyFragment).commit();
        mRadioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_tab_baogao:
                        mServicesFragment = new ServicesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mServicesFragment).commit();
                        break;
                    case R.id.id_tab_jiaoyu:
                        mHealthEducationFragment = new HealthEducationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mHealthEducationFragment).commit();
                        break;
                    case R.id.id_tab_ziliao:
                        mMyFragment = new MyFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mMyFragment).commit();
                        break;
                    default:
                        break;

                }

            }
        });
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
