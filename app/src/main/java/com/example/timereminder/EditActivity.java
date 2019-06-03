package com.example.activitytest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    int mYear,mMonth,mDay;
    final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.hide();
        }

        TextView title = (TextView) findViewById(R.id.textview_title);/*更改页面标题*/
        title.setText("修改活动");

        RelativeLayout buttom = findViewById(R.id.layout_buttom);/*隐藏底部编辑栏*/
        buttom.setVisibility(View.GONE);

        EditText issue = (EditText) findViewById(R.id.edittext_title);/*事件标题*/
        EditText location = (EditText) findViewById(R.id.edittext_location);/*地点*/
        final EditText expcode = (EditText) findViewById(R.id.edittext_express_code);/*取货码*/
        EditText descrip = (EditText) findViewById(R.id.edittext_description);/*备注*/
        TextView startdate = (TextView) findViewById(R.id.textview_start_date);/*开始日期*/
        TextView enddate = (TextView) findViewById(R.id.textview_end_date);/*结束日期*/
        TextView starttime = (TextView) findViewById(R.id.textview_start_time);/*开始时间*/
        TextView endtime = (TextView) findViewById(R.id.textview_end_time);/*结束时间*/
        Spinner remind = (Spinner) findViewById(R.id.spinner_reminder);//提醒

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
                Toast.makeText(EditActivity.this,"提醒事件已保存",Toast.LENGTH_SHORT).show();
            }
        });

        Switch ex = (Switch) findViewById(R.id.switch_express);/*快递选择开关*/
        ex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    expcode.setVisibility(View.VISIBLE);
                else
                    expcode.setVisibility(View.GONE);
            }
        });

        /*选择开始日期*/
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

    }
    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(EditActivity.this);
        normalDialog.setMessage("放弃编辑此活动?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        TextView startdate = (TextView) findViewById(R.id.textview_start_date);
        startdate.setText(new StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日"));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

}
