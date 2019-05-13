package com.example.timereminder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//数据库相关
import com.example.timereminder.base.activity.BaseActivity;
import com.example.timereminder.core.datastructure.TaskMessage;
//日历相关
import com.example.timereminder.task.TaskAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearViewChangeListener {

//数据库相关，考虑移动到基类里
    //TaskDatabaseHelper<TaskMessage> taskbase;
    List<TaskMessage> taskList;

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
                    mCalendarView.setVisibility(View.GONE);
                    mRelativeTool.setVisibility(View.GONE);
                    findViewById(R.id.recycler_view).setVisibility(View.GONE);//TODO
                    mTextMessage.setText(R.string.title_setting);
                    return true;
                case R.id.navigation_calendar:
                    mCalendarView.setVisibility(View.VISIBLE);
                    mRelativeTool.setVisibility(View.VISIBLE);
                    findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);//TODO
                    mTextMessage.setText(R.string.title_calendar);
                    return true;
                case R.id.navigation_notifications:
                    mCalendarView.setVisibility(View.GONE);
                    mRelativeTool.setVisibility(View.GONE);
                    findViewById(R.id.recycler_view).setVisibility(View.GONE);//TODO
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
        //mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
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

        findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoreDialog == null) {
                    mMoreDialog = new AlertDialog.Builder(MainActivity.this)
//                            .setTitle(R.string.list_dialog_title)
                            .setItems(R.array.list_dialog_items, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            break;
                                        case 1:
                                            mCalendarView.scrollToNext(false);
                                            break;
                                        case 2:
                                            mCalendarView.scrollToPre(false);
                                            break;
                                    }
                                }
                    })
                            .create();
                }
                mMoreDialog.show();
            }
        });

        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        mCalendarView.setOnYearViewChangeListener(this);

        mCalendarView.setOnViewChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        //TODO:添加一些基本的事件类型，用于测试显示列表，后续注意删除
        taskList=new ArrayList<TaskMessage>();
        TaskMessage task1=new TaskMessage("读书","2019-5-12 01:06:55.300");
        taskList.add(task1);
        TaskMessage task2=new TaskMessage("上学","2019-5-12 01:06:55.300");
        taskList.add(task2);
        TaskMessage task3=new TaskMessage("码代码","2019-5-12 01:06:55.300");
        taskList.add(task3);
        TaskMessage task4=new TaskMessage("读书","2019-5-12 01:06:55.300");
        taskList.add(task4);
        TaskMessage task5=new TaskMessage("上学","2019-5-12 01:06:55.300");
        taskList.add(task5);
        TaskMessage task6=new TaskMessage("码代码","2019-5-12 01:06:55.300");
        taskList.add(task6);
        TaskMessage task7=new TaskMessage("读书","2019-5-12 01:06:55.300");
        taskList.add(task7);
        TaskMessage task8=new TaskMessage("上学","2019-5-12 01:06:55.300");
        taskList.add(task8);
        TaskMessage task9=new TaskMessage("码代码","2019-5-12 01:06:55.300");
        taskList.add(task9);

        RecyclerView taskRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);
        TaskAdapter<TaskMessage> adapter=new TaskAdapter<TaskMessage>(this);
        adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);


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

    @Override
    protected void initData() {

        final int year = mCalendarView.getCurYear();
        final int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();

        //28560 数据量增长不会影响UI响应速度，请使用这个API替换
        mCalendarView.setSchemeDate(map);

        //可自行测试性能差距
        //mCalendarView.setSchemeDate(schemes);

    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Toast.makeText(this, String.format("%s : OutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //Log.e("onDateSelected", "  -- " + calendar.getYear() + "  --  " + calendar.getMonth() + "  -- " + calendar.getDay());
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        if (isClick) {
            Toast.makeText(this, getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        }
//        Log.e("lunar "," --  " + calendar.getLunarCalendar().toString() + "\n" +
//        "  --  " + calendar.getLunarCalendar().getYear());
        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
        Log.e("onDateSelected", "  " + mCalendarView.getSelectedCalendar().getScheme() +
                "  --  " + mCalendarView.getSelectedCalendar().isCurrentDay());
        Log.e("干支年纪 ： ", " -- " + TrunkBranchAnnals.getTrunkBranchYear(calendar.getLunarCalendar().getYear()));
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {
        Toast.makeText(this, String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        Toast.makeText(this, "长按不选择日期\n" + getCalendarText(calendar), Toast.LENGTH_SHORT).show();
    }

    private static String getCalendarText(Calendar calendar) {
        return String.format("新历%s \n 农历%s \n 公历节日：%s \n 农历节日：%s \n 节气：%s \n 是否闰月：%s",
                calendar.getMonth() + "月" + calendar.getDay() + "日",
                calendar.getLunarCalendar().getMonth() + "月" + calendar.getLunarCalendar().getDay() + "日",
                TextUtils.isEmpty(calendar.getGregorianFestival()) ? "无" : calendar.getGregorianFestival(),
                TextUtils.isEmpty(calendar.getTraditionFestival()) ? "无" : calendar.getTraditionFestival(),
                TextUtils.isEmpty(calendar.getSolarTerm()) ? "无" : calendar.getSolarTerm(),
                calendar.getLeapMonth() == 0 ? "否" : String.format("闰%s月", calendar.getLeapMonth()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month) {
        Log.e("onMonthChange", "  -- " + year + "  --  " + month);
        Calendar calendar = mCalendarView.getSelectedCalendar();
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onViewChange(boolean isMonthView) {
        Log.e("onViewChange", "  ---  " + (isMonthView ? "月视图" : "周视图"));
    }


    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        for (Calendar calendar : weekCalendars) {
            Log.e("onWeekChange", calendar.toString());
        }
    }

    @Override
    public void onYearViewChange(boolean isClose) {
        Log.e("onYearViewChange", "年视图 -- " + (isClose ? "关闭" : "打开"));
    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        Log.e("onCalendarIntercept", calendar.toString());
        int day = calendar.getDay();
        return day == 1 || day == 3 || day == 6 || day == 11 || day == 12 || day == 15 || day == 20 || day == 26;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(this, calendar.toString() + "拦截不可点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
        Log.e("onYearChange", " 年份变化 " + year);
    }


}
