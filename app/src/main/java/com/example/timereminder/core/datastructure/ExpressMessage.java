package com.example.timereminder.core.datastructure;

import java.util.Date;

public class ExpressMessage extends TaskMessage {
    private int m_code;//存储取件码
    private String m_expressCompany;//快递公司

    public ExpressMessage(){
        super();
    }
    public ExpressMessage(String name, Date time, int code, String company){
        super(name,time);
        m_code=code;
        m_expressCompany=company;
    }

    public ExpressMessage(String name,Date time,int code,String company,String location){
        super(name,time);
        m_code=code;
        m_expressCompany=company;
        super.setLocation(location);
    }

    public void setCode(int m_code) {
        this.m_code = m_code;
    }

    public int getCode() {
        return m_code;
    }

    public void setExpressCompany(String m_expressCompany) {
        this.m_expressCompany = m_expressCompany;
    }

    public String getExpressCompany() {
        return m_expressCompany;
    }
}
