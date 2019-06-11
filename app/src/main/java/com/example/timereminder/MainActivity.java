package com.example.timereminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.timereminder.alarm.AlarmHelper;
import com.example.timereminder.alarm.NoticeActivity;
import com.example.timereminder.base.activity.BaseActivity;
import com.example.timereminder.base.fragment.FragmentAdapter;
import com.example.timereminder.core.datastructure.ExpressMessage;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.pager.calendarFragment;
import com.example.timereminder.pager.notificationFragment;
import com.example.timereminder.pager.settingFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity {

    private notificationFragment mNotificationFragment;
    private calendarFragment mCalendarFragment;
    private settingFragment mSettingFragment;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private OnUpdateNotificationFragment mUpdateNotification;
    private OnUpdateCalendarFragment mUpdateCalendarFragment;
    //private NotificationManager mNotificationManager;
    private AlarmManager mAlarmManager;

    private final String CHANNEL_ID="notification";
//    NotificationChannel mChannel;
//    private AlarmManager mAlarmManager;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        setContentView(R.layout.activity_main);

        //mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mAlarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
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
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                        //TODO:setting
                    case 2:
                        findViewById(R.id.fab).setVisibility(View.GONE);
                        break;
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
        mFragmentList.add(mSettingFragment = new settingFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.reset(mFragmentList);
        mViewPager.setAdapter(adapter);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.e("mNavigation",""+item.getItemId());
                switch (item.getItemId()) {
             //TODO:setting
                    case R.id.navigation_setting:
                        mViewPager.setCurrentItem(2);
                        Log.e("mNavigation","click setting");
                        return true;
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
                intent.putExtra("date",getSettingTime());
                startActivityForResult(intent,1);
//                // 注册AlarmManager的定时服务
//                Intent intent=new Intent();// Constants.ACITON_REMIND是自定义的一个action
//
//                // 使用Reminder实体的ID作为PendingIntent的requestCode可以保证PendingIntent的唯一性
//                PendingIntent pi=PendingIntent.getBroadcast(MainActivity.this, 0, intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//                // 设定的时间是Reminder实体中封装的时间
//                Notification notification=new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
//                        .setContentTitle("ceshi")
//                        .setContentText("ceshineirong")
//                        .setWhen(System.currentTimeMillis()+1000)
//                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
//                        .setContentIntent(pi)
//                        .setAutoCancel(true)
//                        .build();
//                mNotificationManager.notify(0,notification);
            }
        });
        updateAllDateInView();
    }

    @Override
    protected void initData(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode , Intent data){
        switch (requestCode%(0xffff+1)){
            //添加数据
            case 1:
                if(resultCode==RESULT_OK) {
                    //String returnedData = data.getStringExtra("item_return");
                    updateAllDateInView();
                    sentToAlart(data);
                    //startActivity(intent);
                    //Log.d("show time", returnedData);
                }
                break;
             //修改信息
            case 2:
                    updateAllDateInView();
                if(resultCode==RESULT_OK) {
                    deleteAlart(data);
                }
                break;
            default:
        }
    }

    private void sentToAlart(Intent data){
        TaskMessage mTask;
        ExpressMessage mExpress;
        mTask=(TaskMessage)data.getParcelableExtra("task_message_return");
        mExpress=(ExpressMessage)data.getParcelableExtra("express_message_return");
        //TODO
        Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
        PendingIntent sender;
        if(mTask!=null) {
            intent.putExtra("task_message_id",mTask.getId());
            intent.putExtra("task_message_name",mTask.getName());
            intent.putExtra("task_message_time",mTask.getTime().getTime());
            intent.putExtra("task_message_location",mTask.getName());
            intent.putExtra("task_message_descrip",mTask.getDescription());
            sender=PendingIntent.getBroadcast(
                    getApplicationContext(),(int)mTask.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, mTask.getTime().getTime(), sender);
        }
        else if(mExpress!=null){
            intent.putExtra("express_message_id",mExpress.getId());
            intent.putExtra("express_message_name",mExpress.getName());
            intent.putExtra("express_message_time",mExpress.getTime().getTime());
            intent.putExtra("express_message_location",mExpress.getLocation());
            intent.putExtra("express_message_code",mExpress.getCode());
            sender=PendingIntent.getBroadcast(
                    getApplicationContext(),(int)(-mExpress.getId()-1), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, mExpress.getTime().getTime(), sender);
        }
    }

    private void deleteAlart(Intent data){
        TaskMessage mTask;
        ExpressMessage mExpress;
        mTask=(TaskMessage)data.getParcelableExtra("task_message_return");
        mExpress=(ExpressMessage)data.getParcelableExtra("express_message_return");
        //TODO
        Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
        PendingIntent sender;
        if(mTask!=null) {
            intent.putExtra("task_message_id",mTask.getId());
            sender=PendingIntent.getBroadcast(
                    getApplicationContext(),(int)mTask.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(sender);
        }
        else if(mExpress!=null){
            intent.putExtra("express_message_id",mExpress.getId());
            sender=PendingIntent.getBroadcast(
                    getApplicationContext(),(int)(-mExpress.getId()-1), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(sender);
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

    public Date getSettingTime(){
        Date date=new Date();
        switch(mViewPager.getCurrentItem()){
            case 1:
                date = mCalendarFragment.getDate();
                break;
                default:
        }
        return date;
    }
}
