package com.example.timereminder.core.database;

import android.util.Log;

import com.example.timereminder.core.datastructure.TaskMessage;
import com.haibin.calendarview.Calendar;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class TaskDatabaseHelper<E extends TaskMessage> {

    //private List<E> database;

    public TaskDatabaseHelper(/*Class<E> modelClass*/){
        //TODO:每次从数据库中获取相应的数据，读入到database中
        //database= LitePal.findAll(modelClass);
    }
    public TaskDatabaseHelper(List<E> taskData){

        //仅用于创建数据库
        //database=taskData;
        for(E data:taskData){
            data.save();
        }
    }
    public static <E extends TaskMessage> void addData(E data) {
        //TODO:考虑添加判断条件，用于判断数据库中是否存在已有的数据
        //database.add(data);
        data.save();
    }

    public static <E extends TaskMessage> List<E> getAllData(Class<E> modelClass){
        //TODO:从数据库中读取所有数据
        return LitePal.findAll(modelClass);
    }

    public static <E extends TaskMessage> List<E> findData(Class<E> modelClass,String... conditions){
        //TODO:读取想要的数据，需要思考用什么形式实现，目前考虑使用数据库中的查询形式
        return LitePal.where(conditions).find(modelClass);
    }

    public static <E extends TaskMessage> E getFirstData(Class<E> modelClass){
        return LitePal.findFirst(modelClass);
    }

    public static <E extends TaskMessage> void deleteData(E data){
        data.delete();
    }

    public static <E extends TaskMessage> void updateData(E data){
        //TODO:更新数据库，每次数据库修改均需要调用
        data.save();
    }

    public static <E extends TaskMessage> void updateData(E data ,long id){
        data.update(id);
    }

    public static <E extends TaskMessage>
    List<E> getAllDataInTimeOrder(
            Class<E> modelClass,
            boolean order){
        //TODO:该方法主要用来输出所有按时间顺序排列的Task数据，其中order为true为正序（从早到晚），false为逆序
        if(order)
            return LitePal.order("m_time asc").find(modelClass);
        else
            return LitePal.order("m_time desc").find(modelClass);
    }

    public static <E extends TaskMessage>
    List<E> findDataInTimeOrder(
            Class<E> modelClass,
            Date historyDate,
            Date futureDate,
            boolean order){
        //TODO:该方法主要用来输出条件内按时间顺序排列的Task数据，其中order为true为正序（从早到晚），false为逆序
        List<E> taskList;
        if(order)
            taskList= LitePal.order("m_time asc").find(modelClass);
        else
            taskList= LitePal.order("m_time desc").find(modelClass);
        for(int i=0;i<taskList.size();i++){
            Date time=taskList.get(i).getTime();
            if(time.before(historyDate)||time.after(futureDate)) {
                Log.e("当前时间",time.toString());
                taskList.remove(i);
                i--;
            }
        }
        //Log.e("筛选时间",taskList.toString());
        return taskList;
    }

    public static <E extends TaskMessage> List<E> findDataWithLocation(Class<E> modelClass,String location){
        return LitePal.where("m_location like",location).order("m_time asc").find(modelClass);
    }
    //返回当前日期的任务
    public static <E extends TaskMessage> List<E> findOneDayDate(Class<E> modelClass,Calendar date){
        Log.e("筛选条件",date.toString());
        Date historyDate=new Date(date.getYear()-1900,date.getMonth()-1,date.getDay(),0,0,0);
        Date futureDate=new Date(date.getYear()-1900,date.getMonth()-1,date.getDay(),23,59,59);
        Log.e("筛选条件",historyDate.toString()+futureDate.toString());
        return findDataInTimeOrder(modelClass,historyDate,futureDate,true);
    }
}
