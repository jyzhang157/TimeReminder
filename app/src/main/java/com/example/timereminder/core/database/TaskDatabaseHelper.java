package com.example.timereminder.core.database;

import com.example.timereminder.core.datastructure.TaskMessage;

import org.litepal.LitePal;

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

    public static <E extends TaskMessage> List<E> readData(Class<E> modelClass,String... conditions){
        //TODO:读取想要的数据，需要思考用什么形式实现，目前考虑使用数据库中的查询形式
        return LitePal.where(conditions).find(modelClass);
    }

    public static <E extends TaskMessage> E getFirstData(Class<E> modelClass)
    {
        return LitePal.findFirst(modelClass);
    }

    public static <E extends TaskMessage> void deleteData(E data){
        data.delete();
    }

    public static <E extends TaskMessage> void updateData(E data){
        //TODO:更新数据库，每次数据库修改均需要调用
        data.save();
    }

    public static <E extends TaskMessage> void updateData(E data,long id){
        data.update(id);
    }
}
