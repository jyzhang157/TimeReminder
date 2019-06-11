package com.example.timereminder.core.datastructure;

import java.io.Serializable;
import java.util.Date;

public class ExpressMessage extends TaskMessage implements Serializable {
    private String m_code;//存储取件码
    private String m_expressCompany;//快递公司

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
