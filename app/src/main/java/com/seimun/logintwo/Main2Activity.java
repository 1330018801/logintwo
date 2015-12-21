package com.seimun.logintwo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.seimun.logintwo.activity.ServicesActivity;

import layout.HealthEducationFragment;
import layout.MyFragment;
import layout.ServicesFragment;

public class Main2Activity extends FragmentActivity {
    private ServicesFragment mServicesFragment;
    private HealthEducationFragment mHealthEducationFragment;
    private MyFragment mMyFragment;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();



    }

    private void initView() {
        mServicesFragment = new ServicesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mServicesFragment).commit();
        mRadioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.id_tab_baogao:
                        mServicesFragment = new ServicesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mServicesFragment).commit();
                        break;
                    case R.id.id_tab_yangsheng:
                        mHealthEducationFragment = new HealthEducationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mHealthEducationFragment).commit();
                        break;
                    case R.id.id_tab_ziliao:
                        mMyFragment = new MyFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mMyFragment).commit();
                        break;
                    default:
                        break;

                }

            }
        });
    }

}
