package com.example.timereminder.pager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timereminder.R;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.task.DailyTaskAdapter;
import com.example.timereminder.task.TaskAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class calendarFragment extends BaseFragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearViewChangeListener{

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
    private ImageView mFuncImage;

    RecyclerView taskRecyclerView;
    LinearLayoutManager layoutManager;
    DailyTaskAdapter<TaskMessage> adapter;
    List<TaskMessage> taskList;

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_pager_calendar;
    }

    @Override
    protected void initView(){
        mTextMonthDay = (TextView) mRootView.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) mRootView.findViewById(R.id.tv_year);
        mTextLunar = (TextView) mRootView.findViewById(R.id.tv_lunar);
        mRelativeTool = (RelativeLayout) mRootView.findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView) mRootView.findViewById(R.id.calendarView);
        //mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
        mTextCurrentDay = (TextView) mRootView.findViewById(R.id.tv_current_day);
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
        mRootView.findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoreDialog == null) {
                    mMoreDialog = new AlertDialog.Builder(getContext())
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

        mRootView.findViewById(R.id.iv_func).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
                taskList= TaskDatabaseHelper.findOneDayDate(TaskMessage.class,mCalendarView.getSelectedCalendar());
                adapter.clear();
                adapter.addAll(taskList);
            }
        });

        mCalendarLayout = (CalendarLayout) mRootView.findViewById(R.id.calendarLayout);
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


        //设置列表
        taskList=new ArrayList<TaskMessage>();
        taskRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view_calendar);
        layoutManager=new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter=new DailyTaskAdapter<TaskMessage>(getContext());
        //adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    protected  void initData(){
        taskList= TaskDatabaseHelper.findOneDayDate(TaskMessage.class,mCalendarView.getSelectedCalendar());
        adapter.clear();
        adapter.addAll(taskList);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Toast.makeText(getContext(), String.format("%s : OutOfRange", calendar), Toast.LENGTH_SHORT).show();
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
            taskList= TaskDatabaseHelper.findOneDayDate(TaskMessage.class,calendar);
            adapter.clear();
            adapter.addAll(taskList);
//            Toast.makeText(getContext(), getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        }
//        Log.e("lunar "," --  " + calendar.getLunarCalendar().toString() + "\n" +
//        "  --  " + calendar.getLunarCalendar().getYear());
//        Log.e("onDateSelected", "  -- " + calendar.getYear() +
//                "  --  " + calendar.getMonth() +
//                "  -- " + calendar.getDay() +
//                "  --  " + isClick + "  --   " + calendar.getScheme());
//        Log.e("onDateSelected", "  " + mCalendarView.getSelectedCalendar().getScheme() +
//                "  --  " + mCalendarView.getSelectedCalendar().isCurrentDay());
//        Log.e("干支年纪 ： ", " -- " + TrunkBranchAnnals.getTrunkBranchYear(calendar.getLunarCalendar().getYear()));
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {
        Toast.makeText(getContext(), String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
//        Toast.makeText(getContext(), "长按不选择日期\n" + getCalendarText(calendar), Toast.LENGTH_SHORT).show();
    }

//    private static String getCalendarText(Calendar calendar) {
//        return String.format("新历%s \n 农历%s \n 公历节日：%s \n 农历节日：%s \n 节气：%s \n 是否闰月：%s",
//                calendar.getMonth() + "月" + calendar.getDay() + "日",
//                calendar.getLunarCalendar().getMonth() + "月" + calendar.getLunarCalendar().getDay() + "日",
//                TextUtils.isEmpty(calendar.getGregorianFestival()) ? "无" : calendar.getGregorianFestival(),
//                TextUtils.isEmpty(calendar.getTraditionFestival()) ? "无" : calendar.getTraditionFestival(),
//                TextUtils.isEmpty(calendar.getSolarTerm()) ? "无" : calendar.getSolarTerm(),
//                calendar.getLeapMonth() == 0 ? "否" : String.format("闰%s月", calendar.getLeapMonth()));
//    }

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
        Toast.makeText(getContext(), calendar.toString() + "拦截不可点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
        Log.e("onYearChange", " 年份变化 " + year);
    }


}
