package com.example.timereminder;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.LinearLayout;
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

import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.core.datastructure.ExpressMessage;

public class EditActivity extends AppCompatActivity {
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
        buttom.setVisibility(View.INVISIBLE);

        final EditText issue = (EditText) findViewById(R.id.edittext_title);/*事件标题*/
        final EditText location = (EditText) findViewById(R.id.edittext_location);/*地点*/
        final EditText expcode = (EditText) findViewById(R.id.express_code);/*取货码*/
        final LinearLayout explayout = (LinearLayout) findViewById(R.id.express_layout);/*快递布局*/
        final EditText descrip = (EditText) findViewById(R.id.edittext_description);/*备注*/
        final TextView startdate = (TextView) findViewById(R.id.textview_start_date);/*开始日期*/
        final TextView enddate = (TextView) findViewById(R.id.textview_end_date);/*结束日期*/
        final TextView starttime = (TextView) findViewById(R.id.textview_start_time);/*开始时间*/
        final TextView endtime = (TextView) findViewById(R.id.textview_end_time);/*结束时间*/
        final Switch exp = (Switch) findViewById(R.id.switch_express);/*快递选择按钮*/
        Spinner remind = (Spinner) findViewById(R.id.spinner_reminder);//提醒

        ImageButton exit = (ImageButton) findViewById(R.id.button_exit);/*返回按钮触发*/
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });

        exp.setEnabled(false);

        ((TextView)findViewById(R.id.textview_title)).setText("修改事件");

        ImageButton save = (ImageButton) findViewById(R.id.button_ok);/*保存按钮触发*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this,"提醒事件已保存",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
//                intent.putExtra("item_return","Task add");
                if(!exp.isChecked()){
                    Date date=convertStringToDate(
                            String.format(Locale.getDefault(),"%s %s",
                                    startdate.getText().toString(),
                                    starttime.getText().toString())
                    );
                    Date edate=convertStringToDate(
                            String.format(Locale.getDefault(),"%s %s",
                                    enddate.getText().toString(),
                                    endtime.getText().toString())
                    );
                    if(issue.getText()!=null&&issue.getText().toString().length()!=0)
                        mTask.setName(issue.getText().toString());
                    if(date!=null)
                        mTask.setTime(date);
                    if(edate!=null)
                        mTask.setEndTime(edate);
                    if(location.getText()!=null&&location.getText().toString().length()!=0)
                        mTask.setLocation(location.getText().toString());
                    if(descrip.getText()!=null&&descrip.getText().toString().length()!=0)
                        mTask.setDescription(descrip.getText().toString());
                    mTask.update(mTask.getId());
                    //Toast.makeText(AddActivity.this,"提醒事件已保存",Toast.LENGTH_SHORT).show();
                    Log.d("show time of task",mTask.getTime().toString());
                    intent.putExtra("task_message_return",mTask);
                }
                else{
                    Date date=convertStringToDate(
                            String.format(Locale.getDefault(),"%s %s",
                                    startdate.getText().toString(),
                                    starttime.getText().toString())
                    );
                    Date edate=convertStringToDate(
                            String.format(Locale.getDefault(),"%s %s",
                                    enddate.getText().toString(),
                                    endtime.getText().toString())
                    );
                    if(issue.getText()!=null&&issue.getText().toString().length()!=0)
                        mExpress.setName(issue.getText().toString());
                    if(date!=null)
                        mExpress.setTime(date);
                    if(edate!=null)
                        mExpress.setEndTime(edate);
                    if(location.getText()!=null&&location.getText().toString().length()!=0)
                        mExpress.setLocation(location.getText().toString());
                    if(descrip.getText()!=null&&descrip.getText().toString().length()!=0)
                        mExpress.setDescription(descrip.getText().toString());
                    if(expcode.getText()!=null&&expcode.getText().toString().length()!=0)
                        mExpress.setCode(expcode.getText().toString());
                    mExpress.update(mExpress.getId());
                    intent.putExtra("express_message_return",mExpress);
                    //Log.d("show time of task",mTask.getTime().toString());
                }
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        exp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    changeToExpressMode();
                else
                    changeToTaskMode();
            }
        });


        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG);
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG);
            }
        });

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_TIME_DIALOG);
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_TIME_DIALOG);
            }
        });

        Intent intent= getIntent();
        mTask=(TaskMessage)intent.getParcelableExtra("task_message");
        mExpress=(ExpressMessage)intent.getParcelableExtra("express_message");
        if(null!=mTask){
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
                if (mExpress.getCode()  != null)
                    expcode.setText(mExpress.getCode());
            }
        }
        else {
            mStartTime=(Date)intent.getParcelableExtra("date");
            mEndTime = new Date(mStartTime.getTime() + TimeUnit.HOURS.toMillis(1));
        }

        startDateDisplay();
        startTimeDisplay();
        endDateDisplay();
        endTimeDisplay();
//        startdate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth+1,mDay));
//        enddate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth+1,mDay));
//        starttime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour,mMinute));
//        endtime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour+1,mMinute));

    }

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

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(EditActivity.this);
        normalDialog.setMessage("放弃编辑此活动?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.putExtra("item_return","Hello FirstActivity");
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG:
                return new DatePickerDialog(this, mStartDateListener, mStartTime.getYear()+1900, mStartTime.getMonth(),mStartTime.getDate());
            case START_TIME_DIALOG:
                return new TimePickerDialog(this, mStartTimeListener, mStartTime.getHours(),mStartTime.getMinutes(),true);
            case END_DATE_DIALOG:
                return new DatePickerDialog(this, mEndDateListener, mEndTime.getYear()+1900, mEndTime.getMonth(),mEndTime.getDate());
            case END_TIME_DIALOG:
                return new TimePickerDialog(this, mEndTimeListener, mEndTime.getHours(),mEndTime.getMinutes(),true);
        }
        return null;
    }

    public void startDateDisplay() {
        TextView startdate = (TextView) findViewById(R.id.textview_start_date);
        startdate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mStartTime.getYear()+1900,mStartTime.getMonth()+1,mStartTime.getDate()));
        Log.d("Start time",mStartTime.toString());
    }
    private DatePickerDialog.OnDateSetListener mStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mStartTime.setYear(year-1900);
            mStartTime.setMonth(monthOfYear);
            mStartTime.setDate(dayOfMonth);

            startDateDisplay();
            if(mEndTime.before(mStartTime))
            {
                mEndTime = new Date(mStartTime.getTime() + TimeUnit.HOURS.toMillis(1));
                endDateDisplay();
                endTimeDisplay();
            }
        }
    };

    public void startTimeDisplay() {
        TextView starttime = (TextView) findViewById(R.id.textview_start_time);
        starttime.setText(String.format(Locale.getDefault(),"%02d:%02d",mStartTime.getHours(),mStartTime.getMinutes()));
    }
    private TimePickerDialog.OnTimeSetListener mStartTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public  void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mStartTime.setHours(hourOfDay);
            mStartTime.setMinutes(minute);
            startTimeDisplay();
            if(mEndTime.before(mStartTime))
            {
                mEndTime = new Date(mStartTime.getTime() + TimeUnit.HOURS.toMillis(1));
                endDateDisplay();
                endTimeDisplay();
            }
        }
    };

    public void endDateDisplay() {
        TextView enddate = (TextView) findViewById(R.id.textview_end_date);
        enddate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mEndTime.getYear()+1900,mEndTime.getMonth()+1,mEndTime.getDate()));
        Log.d("End time",mEndTime.toString());
    }
    private DatePickerDialog.OnDateSetListener mEndDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mEndTime.setYear(year-1900);
            mEndTime.setMonth(monthOfYear);
            mEndTime.setDate(dayOfMonth);
            endDateDisplay();
        }
    };

    public void endTimeDisplay() {
        TextView endtime = (TextView) findViewById(R.id.textview_end_time);
        endtime.setText(String.format(Locale.getDefault(),"%02d:%02d",mEndTime.getHours(),mEndTime.getMinutes()));
    }
    private TimePickerDialog.OnTimeSetListener mEndTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public  void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mEndTime.setHours(hourOfDay);
            mEndTime.setMinutes(minute);
            endTimeDisplay();
        }
    };

    private void changeToExpressMode(){
        findViewById(R.id.layout_endtime).setVisibility(View.GONE);
        findViewById(R.id.express_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.sms).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.time_name)).setText(" 取件时间");
        ((Switch) findViewById(R.id.switch_express)).setChecked(true);
    }

    private void changeToTaskMode(){
        findViewById(R.id.layout_endtime).setVisibility(View.VISIBLE);
        findViewById(R.id.express_layout).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.time_name)).setText(" 开始时间");
        ((Switch) findViewById(R.id.switch_express)).setChecked(false);
    }

}