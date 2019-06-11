package com.example.timereminder.core.datastructure;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class ExpressMessage extends TaskMessage implements Parcelable {
    private String m_code;//存储取件码
    private String m_expressCompany;//快递公司

    @Override
    public int describeContents() {
        return 0;
    }

    protected ExpressMessage(Parcel in) {
        super(in);
        m_code=in.readString();
        m_expressCompany=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeString(m_code);
        dest.writeString(m_expressCompany);
    }

    public static final Creator<ExpressMessage> CREATOR = new Creator<ExpressMessage>() {
        @Override
        public ExpressMessage createFromParcel(Parcel in) {
            return new ExpressMessage(in);
        }
        @Override
        public ExpressMessage[] newArray(int size) {
            return new ExpressMessage[size];
        }
    };

    public ExpressMessage(){
        super();
    }
    public ExpressMessage(String name, Date time, String code, String company){
        super(name,time);
        m_code=code;
        m_expressCompany=company;
    }

    public ExpressMessage(String name,Date time,String code,String company,String location){
        super(name,time);
        m_code=code;
        m_expressCompany=company;
        super.setLocation(location);
    }

    public void setCode(String m_code) {
        this.m_code = m_code;
    }

    public String getCode() {
        return m_code;
    }

    public void setExpressCompany(String m_expressCompany) {
        this.m_expressCompany = m_expressCompany;
    }

    public String getExpressCompany() {
        return m_expressCompany;
    }
}
