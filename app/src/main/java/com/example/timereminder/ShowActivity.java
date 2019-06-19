package com.example.timereminder;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.example.timereminder.alarm.NoticeActivity;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.core.datastructure.ExpressMessage;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

public class ShowActivity extends AppCompatActivity {
    Date mStartTime=new Date();
    Date mEndTime=new Date();
    final int START_DATE_DIALOG = 1;
    final int START_TIME_DIALOG = 2;
    final int END_DATE_DIALOG = 3;
    final int END_TIME_DIALOG = 4;

    TaskMessage mTask;
    ExpressMessage mExpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.hide();
        }

        RelativeLayout buttom = findViewById(R.id.layout_buttom);
        buttom.setVisibility(View.VISIBLE);

        final EditText issue = (EditText) findViewById(R.id.edittext_title);/*事件标题*/
        final EditText location = (EditText) findViewById(R.id.edittext_location);/*地点*/
        final EditText expcode = (EditText) findViewById(R.id.express_code);/*取货码*/
        final EditText descrip = (EditText) findViewById(R.id.edittext_description);/*备注*/
        final TextView startdate = (TextView) findViewById(R.id.textview_start_date);/*开始日期*/
        final TextView enddate = (TextView) findViewById(R.id.textview_end_date);/*结束日期*/
        final TextView starttime = (TextView) findViewById(R.id.textview_start_time);/*开始时间*/
        final TextView endtime = (TextView) findViewById(R.id.textview_end_time);/*结束时间*/
        final Switch exp = (Switch) findViewById(R.id.switch_express);/*快递选择按钮*/
        final Button edit=(Button) findViewById(R.id.button_edit);
        final Button delete=(Button) findViewById(R.id.button_delete);
        Spinner remind = (Spinner) findViewById(R.id.spinner_reminder);//提醒

        issue.setFocusable(false);
        location.setFocusable(false);
        exp.setClickable(false);
        descrip.setFocusable(false);
        startdate.setClickable(false);
        enddate.setClickable(false);
        starttime.setClickable(false);
        endtime.setClickable(false);
        remind.setClickable(false);
        remind.setFocusable(false);
        remind.setEnabled(false);
        exp.setEnabled(false);

        findViewById(R.id.express_code).setFocusable(false);

        ((TextView)findViewById(R.id.textview_title)).setText("查看事件");
//返回按钮事件
        ImageButton exit = (ImageButton) findViewById(R.id.button_exit);/*更改返回按钮图标*/
        exit.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//保存按钮属性
        ImageButton save = (ImageButton) findViewById(R.id.button_ok);/*隐藏保存按钮*/
        save.setVisibility(View.INVISIBLE);
        save.setClickable(false);
//设置界面中的各个文本框
        Intent intent= getIntent();
        mTask=(TaskMessage)intent.getParcelableExtra("task_message");
        mExpress=(ExpressMessage)intent.getParcelableExtra("express_message");
        if(null!=mTask){
            exp.setChecked(false);
            changeToTaskMode();
            if(mTask.getName()!=null)
                issue.setText(mTask.getName());
            if(mTask.getTime()!=null){
                mStartTime=mTask.getTime();
            }
            if(mTask.getEndTime()!=null){
                mEndTime=mTask.getEndTime();
            }
            if(mTask.getLocation()!=null)
                location.setText(mTask.getLocation());
            if(mTask.getDescription()!=null)
                descrip.setText(mTask.getDescription());
        }
        else if(null!=mExpress) {
            exp.setChecked(true);
            changeToExpressMode();
            if (null != mExpress) {
                if (mExpress.getName() != null)
                    issue.setText(mExpress.getName());
                if (mExpress.getTime() != null) {
                    mStartTime = mExpress.getTime();
                }
                if (mExpress.getEndTime() != null) {
                    mEndTime = mExpress.getEndTime();
                }
                if (mExpress.getLocation() != null)
                    location.setText(mExpress.getLocation());
                if(mExpress.getDescription()!=null)
                    descrip.setText(mExpress.getDescription());
                if (mExpress.getCode() != null)
                    expcode.setText(mExpress.getCode());
            }
        }
        //更新时间显示
        startDateDisplay();
        startTimeDisplay();
        endDateDisplay();
        endTimeDisplay();
//        startdate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth+1,mDay));
//        enddate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth+1,mDay));
//        starttime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour,mMinute));
//        endtime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour+1,mMinute));
        //编辑按钮事件
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(ShowActivity.this,EditActivity.class);
                if(null!=mTask)
                    pass.putExtra("task_message",mTask);
                else if(null!=mExpress)
                    pass.putExtra("express_message",mExpress);
                startActivityForResult(pass,5);//5返回数据
            }
        });
        //删除按钮事件
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(exp.isChecked()){
                    intent.putExtra("express_message_return",mExpress);
                    LitePal.delete(ExpressMessage.class,mExpress.getId());
                }
                else{
                    intent.putExtra("task_message_return",mTask);
                    LitePal.delete(TaskMessage.class,mTask.getId());
                }
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
//活动返回时触发事件
    @Override
    public void onActivityResult(int requestCode, int resultCode , Intent data){
        EditText issue = (EditText) findViewById(R.id.edittext_title);/*事件标题*/
        EditText location = (EditText) findViewById(R.id.edittext_location);/*地点*/
        EditText code=(EditText)findViewById(R.id.express_code);
        EditText descrip = (EditText) findViewById(R.id.edittext_description);/*备注*/
        switch (requestCode%(0xffff+1)){
            //编辑活动返回
            case 5:
                if(resultCode==RESULT_OK) {
                    mTask=(TaskMessage)data.getParcelableExtra("task_message_return");
                    mExpress=(ExpressMessage)data.getParcelableExtra("express_message_return");
                    if(null!=mTask){
                        if(mTask.getName()!=null)
                            issue.setText(mTask.getName());
                        if(mTask.getTime()!=null){
                            mStartTime=mTask.getTime();
                        }
                        if(mTask.getEndTime()!=null){
                            mEndTime=mTask.getEndTime();
                        }
                        if(mTask.getLocation()!=null)
                            location.setText(mTask.getLocation());
                        if(mTask.getDescription()!=null)
                            descrip.setText(mTask.getDescription());

                        sentToAlart(data);
                    }
                    else if(null!=mExpress) {
                        if (null != mExpress) {
                            if (mExpress.getName() != null)
                                issue.setText(mExpress.getName());
                            if (mExpress.getTime() != null) {
                                mStartTime = mExpress.getTime();
                            }
                            if (mExpress.getEndTime() != null) {
                                mEndTime = mExpress.getEndTime();
                            }
                            if (mExpress.getLocation() != null)
                                location.setText(mExpress.getLocation());
                            if(mExpress.getDescription()!=null)
                                descrip.setText(mExpress.getDescription());
                            if (mExpress.getCode() != null)
                                code.setText(mExpress.getCode());

                            sentToAlart(data);
                        }
                    }
                    startDateDisplay();
                    startTimeDisplay();
                    endDateDisplay();
                    endTimeDisplay();
                }
                break;
            default:
        }
    }
//用于申请定时启动通知栏
    private void sentToAlart(Intent data) {
        TaskMessage mTask;
        ExpressMessage mExpress;
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mTask = (TaskMessage) data.getParcelableExtra("task_message_return");
        mExpress = (ExpressMessage) data.getParcelableExtra("express_message_return");
        //TODO
        Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
        PendingIntent sender;
        if (mTask != null) {
            intent.putExtra("task_message_id", mTask.getId());
            intent.putExtra("task_message_name", mTask.getName());
            intent.putExtra("task_message_time", mTask.getTime().getTime());
            intent.putExtra("task_message_descrip", mTask.getDescription());
            sender = PendingIntent.getBroadcast(
                    getApplicationContext(), (int) mTask.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(sender);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, mTask.getTime().getTime(), sender);
        } else if (mExpress != null) {
            intent.putExtra("express_message_id", mExpress.getId());
            intent.putExtra("express_message_name", mExpress.getName());
            intent.putExtra("express_message_time", mExpress.getTime().getTime());
            intent.putExtra("express_message_code", mExpress.getCode());
            sender = PendingIntent.getBroadcast(
                    getApplicationContext(), (int) (-mExpress.getId() - 1), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(sender);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, mExpress.getTime().getTime(), sender);
        }
    }
//格式化String到Date
    private Date convertStringToDate(String str){
        Date date=new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public void startDateDisplay() {
        TextView startdate = (TextView) findViewById(R.id.textview_start_date);
        startdate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mStartTime.getYear()+1900,mStartTime.getMonth()+1,mStartTime.getDate()));
        Log.d("Start time",mStartTime.toString());
    }


    public void startTimeDisplay() {
        TextView starttime = (TextView) findViewById(R.id.textview_start_time);
        starttime.setText(String.format(Locale.getDefault(),"%02d:%02d",mStartTime.getHours(),mStartTime.getMinutes()));
    }


    public void endDateDisplay() {
        TextView enddate = (TextView) findViewById(R.id.textview_end_date);
        enddate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mEndTime.getYear()+1900,mEndTime.getMonth()+1,mEndTime.getDate()));
        Log.d("End time",mEndTime.toString());
    }


    public void endTimeDisplay() {
        TextView endtime = (TextView) findViewById(R.id.textview_end_time);
        endtime.setText(String.format(Locale.getDefault(),"%02d:%02d",mEndTime.getHours(),mEndTime.getMinutes()));
    }
//将显示模式改为快递事件
    private void changeToExpressMode(){
    findViewById(R.id.layout_endtime).setVisibility(View.GONE);
    findViewById(R.id.express_layout).setVisibility(View.VISIBLE);
    findViewById(R.id.sms).setVisibility(View.GONE);
    ((TextView)findViewById(R.id.time_name)).setText("取件时间");
    ((Switch) findViewById(R.id.switch_express)).setChecked(true);
}
//讲显示模式改为普通事件
    private void changeToTaskMode(){
        findViewById(R.id.layout_endtime).setVisibility(View.VISIBLE);
        findViewById(R.id.express_layout).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.time_name)).setText("开始时间");
        ((Switch) findViewById(R.id.switch_express)).setChecked(false);
    }
}
