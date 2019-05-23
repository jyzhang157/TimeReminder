package com.example.timereminder.core.datastructure;

import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskMessage extends LitePalSupport {
    private long id;
    private String m_name;
    private Date m_time;//格式为 "YYYY-MM-DD HH:MM:SS.SSS" 的日期
    private Date m_etime;//反映结束时间，格式同上
    private String m_description;
    private String m_location;
    private String m_info;//备注
    private boolean m_checked;//标注事件是否完成

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
