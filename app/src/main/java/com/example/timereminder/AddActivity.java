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
import java.util.concurrent.TimeoutException;

import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.core.datastructure.ExpressMessage;

public class AddActivity extends AppCompatActivity {
    int mYear,mMonth,mDay;
    int mHour,mMinute;
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
        buttom.setVisibility(View.GONE);

        final EditText issue = (EditText) findViewById(R.id.edittext_title);/*事件标题*/
        final EditText location = (EditText) findViewById(R.id.edittext_location);/*地点*/
        final EditText expcode = (EditText) findViewById(R.id.edittext_express_code);/*取货码*/
        final EditText descrip = (EditText) findViewById(R.id.edittext_description);/*备注*/
        final TextView startdate = (TextView) findViewById(R.id.textview_start_date);/*开始日期*/
        final TextView enddate = (TextView) findViewById(R.id.textview_end_date);/*结束日期*/
        final TextView starttime = (TextView) findViewById(R.id.textview_start_time);/*开始时间*/
        final TextView endtime = (TextView) findViewById(R.id.textview_end_time);/*结束时间*/
        final Switch exp = (Switch) findViewById(R.id.switch_express);/*快递选择按钮*/
        Spinner remind = (Spinner) findViewById(R.id.spinner_reminder);//提醒
        mTask=new TaskMessage();
        mExpress=new ExpressMessage();

        ImageButton exit = (ImageButton) findViewById(R.id.button_exit);/*返回按钮触发*/
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showNormalDialog();
            }
        });

        ImageButton save = (ImageButton) findViewById(R.id.button_ok);/*保存按钮触发*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this,"提醒事件已保存",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
//                intent.putExtra("item_return","Task add");
                setResult(RESULT_OK,intent);
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
                    if(issue.getText()!=null)
                        mTask.setName(issue.getText().toString());
                    if(date!=null)
                        mTask.setTime(date);
                    if(edate!=null)
                        mTask.setEndTime(edate);
                    if(location.getText()!=null)
                        mTask.setLocation(location.getText().toString());
                    if(descrip.getText()!=null)
                        mTask.setDescription(descrip.getText().toString());
                    mTask.save();
                    //Toast.makeText(AddActivity.this,"提醒事件已保存",Toast.LENGTH_SHORT).show();
                    intent.putExtra("item_return",mTask.getTime().toString());
                    Log.d("show time of task",mTask.getTime().toString());
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
                    if(issue.getText()!=null)
                        mExpress.setName(issue.getText().toString());
                    if(date!=null)
                        mExpress.setTime(date);
                    if(edate!=null)
                        mExpress.setEndTime(edate);
                    if(location.getText()!=null)
                        mExpress.setLocation(location.getText().toString());
                    if(descrip.getText()!=null)
                        mExpress.setDescription(descrip.getText().toString());
                    mExpress.save();
                    intent.putExtra("item_return",mTask.getTime().toString());
                    Log.d("show time of task",mTask.getTime().toString());
                }
                finish();
            }
        });


        exp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    expcode.setVisibility(View.VISIBLE);
                else
                    expcode.setVisibility(View.GONE);
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

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH)+1;
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour = ca.get(Calendar.HOUR_OF_DAY);
        mMinute = ca.get(Calendar.MINUTE);

        startdate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth,mDay));
        enddate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth,mDay));
        starttime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour,mMinute));
        endtime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour+1,mMinute));

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
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(AddActivity.this);
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
                return new DatePickerDialog(this, mStartDateListener, mYear, mMonth, mDay);
            case START_TIME_DIALOG:
                return new TimePickerDialog(this, mStartTimeListener, mHour,mMinute,true);
            case END_DATE_DIALOG:
                return new DatePickerDialog(this, mEndDateListener, mYear, mMonth, mDay);
            case END_TIME_DIALOG:
                return new TimePickerDialog(this, mEndTimeListener, mHour,mMinute,true);
        }
        return null;
    }

    public void startDateDisplay() {
        TextView startdate = (TextView) findViewById(R.id.textview_start_date);
        startdate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth,mDay));
    }
    private DatePickerDialog.OnDateSetListener mStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            startDateDisplay();
        }
    };

    public void startTimeDisplay() {
        TextView starttime = (TextView) findViewById(R.id.textview_start_time);
        starttime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour,mMinute));
    }
    private TimePickerDialog.OnTimeSetListener mStartTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public  void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            startTimeDisplay();
        }
    };

    public void endDateDisplay() {
        TextView enddate = (TextView) findViewById(R.id.textview_end_date);
        enddate.setText(String.format(Locale.getDefault(),"%04d-%02d-%02d",mYear,mMonth,mDay));
    }
    private DatePickerDialog.OnDateSetListener mEndDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            endDateDisplay();
        }
    };

    public void endTimeDisplay() {
        TextView endtime = (TextView) findViewById(R.id.textview_end_time);
        endtime.setText(String.format(Locale.getDefault(),"%02d:%02d",mHour,mMinute));
    }
    private TimePickerDialog.OnTimeSetListener mEndTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public  void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            endTimeDisplay();
        }
    };

}