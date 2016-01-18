package com.seimun.logintwo.activity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.seimun.logintwo.R;

import java.util.ArrayList;
import java.util.List;

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

        RadioButton bradioButton = (RadioButton) findViewById(R.id.id_tab_baogao);
        Drawable baogao1 = getResources().getDrawable(R.drawable.baogao1);
        baogao1.setBounds(0,0,60,80);
        bradioButton.setCompoundDrawables(null,baogao1,null,null);
        RadioButton jradioButton = (RadioButton) findViewById(R.id.id_tab_yangsheng);
        Drawable jiaoyu1 = getResources().getDrawable(R.drawable.jiaoyu1);
        jiaoyu1.setBounds(0,0,80,80);
        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
        RadioButton yradioButton = (RadioButton) findViewById(R.id.id_tab_yiliao);
        Drawable yiyuan1 = getResources().getDrawable(R.drawable.yiyuan1);
        yiyuan1.setBounds(0,0,80,80);
        yradioButton.setCompoundDrawables(null,yiyuan1,null,null);
        RadioButton mradioButton = (RadioButton) findViewById(R.id.id_tab_ziliao);
        Drawable my2 = getResources().getDrawable(R.drawable.my2);
        my2.setBounds(0,0,80,80);
        mradioButton.setCompoundDrawables(null,my2,null,null);

        initView();

    }





    private void initView() {
        final Drawable baogao1 = getResources().getDrawable(R.drawable.baogao1);
        baogao1.setBounds(0,0,60,80);
        final Drawable baogao2 = getResources().getDrawable(R.drawable.baogao2);
        baogao2.setBounds(0,0,60,80);
        final Drawable jiaoyu1 = getResources().getDrawable(R.drawable.jiaoyu1);
        jiaoyu1.setBounds(0,0,80,80);
        final Drawable jiaoyu2 = getResources().getDrawable(R.drawable.jiaoyu2);
        jiaoyu2.setBounds(0,0,80,80);
        final  Drawable yiyuan1 = getResources().getDrawable(R.drawable.yiyuan1);
        yiyuan1.setBounds(0,0,80,80);
        final  Drawable yiyuan2 = getResources().getDrawable(R.drawable.yiyuan2);
        yiyuan2.setBounds(0,0,80,80);
        final Drawable my1 = getResources().getDrawable(R.drawable.my1);
        my1.setBounds(0,0,80,80);
        final Drawable my2 = getResources().getDrawable(R.drawable.my2);
        my2.setBounds(0,0,80,80);

        final RadioButton bradioButton = (RadioButton) findViewById(R.id.id_tab_baogao);
        final RadioButton jradioButton = (RadioButton) findViewById(R.id.id_tab_yangsheng);
        final RadioButton yradioButton = (RadioButton) findViewById(R.id.id_tab_yiliao);
        final RadioButton mradioButton = (RadioButton) findViewById(R.id.id_tab_ziliao);

        mMyFragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mMyFragment).commit();
        mRadioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.id_tab_baogao:
                        mServicesFragment = new ServicesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mServicesFragment).commit();
                        bradioButton.setCompoundDrawables(null,baogao2,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
                        yradioButton.setCompoundDrawables(null,yiyuan1,null,null);
                        mradioButton.setCompoundDrawables(null,my1,null,null);
                        break;
                    case R.id.id_tab_yangsheng:
                        mHealthEducationFragment = new HealthEducationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mHealthEducationFragment).commit();
                        bradioButton.setCompoundDrawables(null,baogao1,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu2,null,null);
                        yradioButton.setCompoundDrawables(null,yiyuan1,null,null);
                        mradioButton.setCompoundDrawables(null,my1,null,null);
                        break;
                    case R.id.id_tab_yiliao:
                        mHospitalFragment = new HospitalFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mHospitalFragment).commit();
                        bradioButton.setCompoundDrawables(null,baogao1,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
                        yradioButton.setCompoundDrawables(null,yiyuan2,null,null);
                        mradioButton.setCompoundDrawables(null,my1,null,null);
                        break;
                    case R.id.id_tab_ziliao:
                        mMyFragment = new MyFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mMyFragment).commit();
                        bradioButton.setCompoundDrawables(null,baogao1,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
                        yradioButton.setCompoundDrawables(null,yiyuan1,null,null);
                        mradioButton.setCompoundDrawables(null,my2,null,null);
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
