package com.example.timereminder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//数据库相关
import com.example.timereminder.base.activity.BaseActivity;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.TaskMessage;
import org.litepal.LitePal;
//日历相关
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;


public class MainActivity extends BaseActivity {
//数据库相关，考虑移动到基类里
    //TaskDatabaseHelper<TaskMessage> taskbase;

    private TextView mTextMessage;
    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;
    private AlertDialog mMoreDialog;
    private AlertDialog mFuncDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_setting:
                    mTextMessage.setText(R.string.title_setting);
                    return true;
                case R.id.navigation_calendar:
                    mTextMessage.setText(R.string.title_calendar);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();

        }
        //taskbase=new TaskDatabaseHelper<TaskMessage>();
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mTextLunar = (TextView) findViewById(R.id.tv_lunar);

        mRelativeTool = (RelativeLayout) findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
        mTextCurrentDay = (TextView) findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                //mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });

//        findViewById(R.id.testbutton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TaskMessage tmp=new TaskMessage("读书","2019-05-09");
//                TaskDatabaseHelper.addData(tmp);
//                Toast.makeText(MainActivity.this,"create database!",Toast.LENGTH_LONG).show();
//            }
//        });
//        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                TaskMessage tmp=TaskDatabaseHelper.getFirstData(TaskMessage.class);
//                if(tmp==null) return;
//                Toast.makeText(MainActivity.this,"delete data!"+tmp.getId(),Toast.LENGTH_LONG).show();
//                tmp.setLocation("上海");
//                TaskDatabaseHelper.updateData(tmp,tmp.getId());
//            }
//        }
//        );
    }

    protected void  initData(){

    }


}
