package com.example.timereminder;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.timereminder.base.activity.BaseActivity;
import com.example.timereminder.base.fragment.FragmentAdapter;
import com.example.timereminder.pager.calendarFragment;
import com.example.timereminder.pager.notificationFragment;
import com.example.timereminder.pager.settingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private notificationFragment mNotificationFragment;
    private calendarFragment mCalendarFragment;
    private settingFragment mSettingFragment;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private OnUpdateNotificationFragment mUpdateNotification;
    private OnUpdateCalendarFragment mUpdateCalendarFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        setContentView(R.layout.activity_main);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
               navigation.getMenu().getItem(position).setChecked(true);
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                        //TODO:setting
                    //case 2:
                        //break;
                }
                Log.e("mViewPager","page"+position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mNotificationFragment = new notificationFragment());
        mFragmentList.add(mCalendarFragment = new calendarFragment());
        //TODO:setting
        //.add(mSettingFragment = new settingFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.reset(mFragmentList);
        mViewPager.setAdapter(adapter);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.e("mNavigation",""+item.getItemId());
                switch (item.getItemId()) {
//             TODO:setting
//                    case R.id.navigation_setting:
//                        mViewPager.setCurrentItem(2);
//                        Log.e("mNavigation","click setting");
//                        return true;
                    case R.id.navigation_calendar:
                        mViewPager.setCurrentItem(1);
                        Log.e("mNavigation","click calendar");
                        return true;
                    case R.id.navigation_notifications:
                        mViewPager.setCurrentItem(0);
                        Log.e("mNavigation","click calendar");
                        return true;
                }
                return false;
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent,1);
            }
        });
        updateAllDateInView();
    }

    @Override
    protected void initData(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode , Intent data){
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK) {
                    String returnedData = data.getStringExtra("item_return");
                    updateAllDateInView();
                    Log.d("show time", returnedData);
                }
                break;
            default:
        }
    }

    public void updateAllDateInView(){
        if(mUpdateNotification!=null)
            mUpdateNotification.onUpdate();
        if(mUpdateCalendarFragment!=null)
            mUpdateCalendarFragment.onUpdate();
    }

    public interface OnUpdateNotificationFragment{
        void onUpdate();
    }
    public interface OnUpdateCalendarFragment{
        void onUpdate();
    }

    public void setOnUpdateNotificationFragment(OnUpdateNotificationFragment in){
        mUpdateNotification=in;
    }

    public void setOnUpdateCalendarFragment(OnUpdateCalendarFragment in){
        mUpdateCalendarFragment=in;
    }
}
