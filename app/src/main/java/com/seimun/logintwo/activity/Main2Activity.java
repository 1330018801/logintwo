package com.seimun.logintwo.activity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.seimun.logintwo.R;
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

        RadioButton bradioButton = (RadioButton) findViewById(R.id.id_tab_baogao);
        Drawable baogao2 = getResources().getDrawable(R.drawable.baogao2);
        baogao2.setBounds(0,0,60,80);
        bradioButton.setCompoundDrawables(null,baogao2,null,null);
        RadioButton jradioButton = (RadioButton) findViewById(R.id.id_tab_yangsheng);
        Drawable jiaoyu1 = getResources().getDrawable(R.drawable.jiaoyu1);
        jiaoyu1.setBounds(0,0,80,80);
        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
        RadioButton mradioButton = (RadioButton) findViewById(R.id.id_tab_ziliao);
        Drawable my1 = getResources().getDrawable(R.drawable.my1);
        my1.setBounds(0,0,80,80);
        mradioButton.setCompoundDrawables(null,my1,null,null);

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
        final Drawable my1 = getResources().getDrawable(R.drawable.my1);
        my1.setBounds(0,0,80,80);
        final Drawable my2 = getResources().getDrawable(R.drawable.my2);
        my2.setBounds(0,0,80,80);

        final RadioButton bradioButton = (RadioButton) findViewById(R.id.id_tab_baogao);
        final RadioButton jradioButton = (RadioButton) findViewById(R.id.id_tab_yangsheng);
        final RadioButton mradioButton = (RadioButton) findViewById(R.id.id_tab_ziliao);

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
                        bradioButton.setCompoundDrawables(null,baogao2,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
                        mradioButton.setCompoundDrawables(null,my1,null,null);
                        break;
                    case R.id.id_tab_yangsheng:
                        mHealthEducationFragment = new HealthEducationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mHealthEducationFragment).commit();
                        bradioButton.setCompoundDrawables(null,baogao1,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu2,null,null);
                        mradioButton.setCompoundDrawables(null,my1,null,null);
                        break;
                    case R.id.id_tab_ziliao:
                        mMyFragment = new MyFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,mMyFragment).commit();
                        bradioButton.setCompoundDrawables(null,baogao1,null,null);
                        jradioButton.setCompoundDrawables(null,jiaoyu1,null,null);
                        mradioButton.setCompoundDrawables(null,my2,null,null);
                        break;
                    default:
                        break;

                }

            }
        });
    }

}