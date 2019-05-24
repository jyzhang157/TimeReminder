package com.example.timereminder.pager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timereminder.R;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.core.datepicker.DatePickerFragment;
import com.example.timereminder.core.datepicker.TimePickerFragment;
import com.example.timereminder.task.TaskAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class settingFragment extends BaseFragment implements
         DatePickerFragment.OnGetDate,
         TimePickerFragment.OnGetTime
         //View.OnClickListener
        {

    EditText mName;
    EditText mDate;
    EditText mTime;
    EditText mLocation;
    Button mAddButton;
    RecyclerView taskRecyclerView;
    LinearLayoutManager layoutManager;
    TaskAdapter<TaskMessage> adapter;
    List<TaskMessage> taskList;
    Button mDButton;
    @Override
    protected int getLayoutId(){
        return R.layout.fragment_pager_setting;
    }

    @Override
    protected void initView(){
        mName=(EditText) mRootView.findViewById(R.id.test_name);
        mDate=(EditText)mRootView.findViewById(R.id.test_date);
        mTime=(EditText)mRootView.findViewById(R.id.test_edate);
        mLocation=(EditText)mRootView.findViewById(R.id.test_location);
        mAddButton=(Button)mRootView.findViewById(R.id.test_add_button);
        mDButton=(Button)mRootView.findViewById(R.id.test_d_button);
        taskRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view);

        layoutManager=new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter=new TaskAdapter<TaskMessage>(getContext());
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                AlertDialog mMoreDialog = new AlertDialog.Builder(getContext())
//                            .setTitle(R.string.list_dialog_title)
                    .setItems(R.array.list_on_task, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                            }
                        }
                    })
                    .create();
        mMoreDialog.show();
            }
        });
        adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        mTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                Date time;
                Date etime;
                String location;
                name=mName.getText().toString();
                time=convertStringToDate(mDate.getText().toString());
                etime=convertStringToDate(mTime.getText().toString());
                location=mLocation.getText().toString();
                TaskMessage task=new TaskMessage(name,time);
                task.setEndTime(etime);
                task.setLocation(location);
                TaskDatabaseHelper.addData(task);
                updateRecycleView();


            }
        });

        mDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskMessage task=new TaskMessage();
                task=TaskDatabaseHelper.getFirstData(TaskMessage.class);
            }
        });
    }

    @Override
    protected  void initData(){
        adapter.clear();
        Date historyDate,futureDate;
        historyDate=new Date((1990-1900),(1-1),1);
        futureDate=new Date((2000-1900),(1-1),1);
        Log.e("筛选条件","开始时间"+historyDate.toString()+"结束时间"+futureDate.toString());
        taskList=TaskDatabaseHelper.findDataInTimeOrder(TaskMessage.class,historyDate,futureDate,true);
        adapter.addAll(taskList);

    }
    //再添加数据后，根据数据库更新显示列表

    private void updateRecycleView(){
        Date historyDate,futureDate;
        historyDate=new Date((1990-1900),(1-1),1);
        futureDate=new Date((2000-1900),(1-1),1);
        taskList=TaskDatabaseHelper.findDataInTimeOrder(TaskMessage.class,historyDate,futureDate,true);
        Log.e("update data",Integer.toString(taskList.size()));
        adapter.clear();
        adapter.addAll(taskList);
    }


    //用于将String解析为Date类型
    private Date convertStringToDate(String str){
        Date date=new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setOnGetTime(this);
        newFragment.show(getChildFragmentManager(), "timePicker");
        //return ((TimePickerFragment) newFragment).getTime();
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment) newFragment).setOnGetDate(this);
        newFragment.show(getChildFragmentManager(), "datePicker");
        //return ((DatePickerFragment) newFragment).getDate();
    }

    public void getDate(int year, int month, int day){
        mDate.setText(Integer.toString(year)
                +"-"+Integer.toString(month)
                +"-"+Integer.toString(day));
    }

    public void getTime(int hourOfDay, int minute){
        mTime.setText(Integer.toString(hourOfDay)
                +":"+Integer.toString(minute));
    }

//    public void onClick(View v){
//        int itemPosition=taskRecyclerView.getChildLayoutPosition(v);
//            AlertDialog mMoreDialog = new AlertDialog.Builder(getContext())
////                            .setTitle(R.string.list_dialog_title)
//                    .setItems(R.array.list_on_task, new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which) {
//                                case 0:
//                                    break;
//                                case 1:
//                                    break;
//                            }
//                        }
//                    })
//                    .create();
//        mMoreDialog.show();
//    }
}
