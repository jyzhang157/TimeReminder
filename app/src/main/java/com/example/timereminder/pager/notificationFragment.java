package com.example.timereminder.pager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.timereminder.R;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.task.TaskAdapter;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class notificationFragment extends BaseFragment {

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    Calendar mCalendar;
    RecyclerView taskRecyclerView;
    LinearLayoutManager layoutManager;
    TaskAdapter<TaskMessage> adapter;

    List<TaskMessage> taskList;

    public static notificationFragment newInstance() {
        return new notificationFragment();
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_pager_notification;
    }

    @Override
    protected void initView(){
        mTextMonthDay = (TextView) mRootView.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) mRootView.findViewById(R.id.tv_year);
        mTextLunar = (TextView) mRootView.findViewById(R.id.tv_lunar);
        mTextCurrentDay = (TextView) mRootView.findViewById(R.id.tv_current_day);
        mCalendar = Calendar.getInstance();

        mTextYear.setText(String.valueOf(mCalendar.get(Calendar.YEAR)));
        mTextMonthDay.setText((mCalendar.get(Calendar.MONTH)+1) + "月" + mCalendar.get(Calendar.DATE) + "日");
        mTextLunar.setText("今日");

        //TODO:添加一些基本的事件类型，用于测试显示列表，后续注意删除
        taskList=new ArrayList<TaskMessage>();
        Date date=new Date(0,1,1);
        TaskMessage task1=new TaskMessage("读书",date);
        taskList.add(task1);
        TaskMessage task2=new TaskMessage("上学",date);
        taskList.add(task2);
        TaskMessage task3=new TaskMessage("码代码",date);
        taskList.add(task3);
        TaskMessage task4=new TaskMessage("读书",date);
        taskList.add(task4);
        TaskMessage task5=new TaskMessage("上学",date);
        taskList.add(task5);
        TaskMessage task6=new TaskMessage("码代码",date);
        taskList.add(task6);
        TaskMessage task7=new TaskMessage("读书",date);
        taskList.add(task7);
        TaskMessage task8=new TaskMessage("上学",date);
        taskList.add(task8);
        TaskMessage task9=new TaskMessage("码代码",date);
        taskList.add(task9);
        TaskMessage task11=new TaskMessage("读书",date);
        taskList.add(task11);
        TaskMessage task12=new TaskMessage("上学",date);
        taskList.add(task12);
        TaskMessage task13=new TaskMessage("码代码",date);
        taskList.add(task13);
        TaskMessage task14=new TaskMessage("读书",date);
        taskList.add(task14);
        TaskMessage task15=new TaskMessage("上学",date);
        taskList.add(task15);
        TaskMessage task16=new TaskMessage("码代码",date);
        taskList.add(task16);
        TaskMessage task17=new TaskMessage("读书",date);
        taskList.add(task17);
        TaskMessage task18=new TaskMessage("上学",date);
        taskList.add(task18);
        TaskMessage task19=new TaskMessage("码代码",date);
        taskList.add(task19);

        taskRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter=new TaskAdapter<TaskMessage>(getContext());
        adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    protected  void initData(){
    }
}
