package com.example.timereminder;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.example.timereminder.base.activity.BaseActivity;
import com.example.timereminder.base.fragment.FragmentAdapter;
import com.example.timereminder.pager.calendarFragment;
import com.example.timereminder.pager.notificationFragment;
import com.example.timereminder.pager.settingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{

    private notificationFragment mNotificationFragment;
    private calendarFragment mCalendarFragment;
    private settingFragment mSettingFragment;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_setting:
                    return true;
                case R.id.navigation_calendar:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };
    @Override
    protected  int getLayoutId(){
        return R.layout.activity_main;
    }

    protected  void initView(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        setContentView(R.layout.activity_main);
        mFragmentList=new ArrayList<>();
        mFragmentList.add(mNotificationFragment=new notificationFragment());
        mFragmentList.add(mCalendarFragment=new calendarFragment());
        mFragmentList.add(mSettingFragment=new settingFragment());
        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager());
        adapter.reset(mFragmentList);

        mViewPager =(ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);

    }

    protected  void initData(){

    }
}
