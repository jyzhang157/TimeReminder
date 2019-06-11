package com.example.timereminder.core.datastructure;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskMessage extends LitePalSupport implements Parcelable {
    private long id;
    private String m_name;
    private Date m_time;//格式为 "YYYY-MM-DD HH:MM:SS.SSS" 的日期
    private Date m_etime;//反映结束时间，格式同上
    private String m_description;//备注
    private String m_location;
    private String m_info;
    private boolean m_checked;//标注事件是否完成

    @Override
    public int describeContents() {
        return 0;
    }

    protected TaskMessage(Parcel in) {
        id = in.readLong();
        m_name = in.readString();
        m_time=new Date(in.readLong());
        m_etime=new Date(in.readLong());
        m_description= in.readString();
        m_location=in.readString();
        m_info=in.readString();
        boolean[] array = new boolean[1];
        in.readBooleanArray(array);
        m_checked=array[0];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(m_name);
        dest.writeLong(m_time.getTime());
        dest.writeLong(m_etime.getTime());
        dest.writeString(m_description);
        dest.writeString(m_location);
        dest.writeString(m_info);
        boolean[] array = new boolean[1];
        array[0]=m_checked;
        dest.writeBooleanArray(array);
    }

    public static final Creator<TaskMessage> CREATOR = new Creator<TaskMessage>() {
        @Override
        public TaskMessage createFromParcel(Parcel in) {
            return new TaskMessage(in);
        }
        @Override
        public TaskMessage[] newArray(int size) {
            return new TaskMessage[size];
        }
    };

    public TaskMessage()
    {

    }
    public TaskMessage(String name,Date time)
    {
        m_name=name;
        m_time=time;
    }
    public TaskMessage(String name,Date time,String description)
    {
        m_name=name;
        m_description=description;
        m_time=time;
    }
    public TaskMessage(String name,Date time,String description,String location)
    {
        m_name=name;
        m_description=description;
        m_time=time;
        m_location=location;
    }
    public TaskMessage(String name,Date time,String description,String location,String info)
    {
        m_name=name;
        m_description=description;
        m_time=time;
        m_location=location;
        m_info=info;
    }
    public long getId() {
        return id;
    }
    public void setName(String name) {
        m_name=name;
    }
    public String getName() {
        return m_name;
    }
    public void setDescription(String des) {
        m_description=des;
    }
    public String getDescription() {
        return m_description;
    }
    public void setTime(Date time){
        m_time=time;
    }
    public Date getTime() {
        return m_time;
    }
    public void setEndTime(Date time){
        m_etime=time;
    }
    public Date getEndTime() {
        return m_etime;
    }

    public void setLocation(String location) {
        m_location=location;
    }
    public String getLocation() {
        return m_location;
    }
    public void setInfo(String info) {
        m_info=info;
    }
    public String getInfo() {
        return m_info;
    }
    public void setChecked(boolean m_checked) {
        this.m_checked = m_checked;
    }
    public boolean isChecked() {
        return m_checked;
    }

    public boolean equals(TaskMessage obj) {
        return (this == obj);
    }

}
