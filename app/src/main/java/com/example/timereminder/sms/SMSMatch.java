package com.example.timereminder.sms;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 为用户提供的类接口
 * main调用这个类的对象->匹配短信类型值->根据类型值进行正则表达式编译->根据编译结果返回字符串给main
 * 实例化对象->.getIsSpecialSMS();--结果为true-->.getKeyContent();
 */
public class SMSMatch {
    private String SMSText;         //短信内容，SMSMatch(String SMSText)
    private boolean needPattern;    //是否需要匹配模型，public SMSMatch(String SMSText), choseType()
    private int type;               //快递短信类型，setIsSpecialSMS(), choseType()
    private String keyContent;      //关键内容，setKeyContent(), handleSMS()
    private String title;           //标题，setTitle(), handleSMS()
    private boolean hasJudgeSMS;    //是否判断过，SMSMatch(String SMSText)，setIsSpecialSMS()
    private int isDeliverySMS;      //是否快递信息0/1，setIsSpecialSMS()
    private boolean hasHandleSMS;   //是否处理过，SMSMatch(String SMSText)，handleSMS()
    private ArrayList<String> arrayList;    //综合title、keyContent、endTime信息，setArrayList()

    /*
     * 快递短信类型值，1-100
     */
    private static final String TYPE1 = "菜鸟驿站";
    private static final String TYPE2 = "云喇叭";
    private static final String TYPE3 = "EMS快递";
    private static final String TYPE4 = "妈妈驿站";
    private static final String TYPE5 = "顺丰速运";
    private static final String TYPE6 = "微快递";
    private static final String TYPE7 = "顺丰快递";
    private static final String TYPE8 = "丰巢";

    public boolean isDelivery(){
        return (type>0);
    }

    public SMSMatch(String SMSText) {
        this.SMSText = SMSText; //短信内容
        hasJudgeSMS = false;    //是否判断过短信类型
        needPattern = true;     //是否需要模型
        keyContent = null;      //关键信息
        title = null;           //标题
        type = 0;
        isDeliverySMS = 0;
        hasHandleSMS = false;
        arrayList = new ArrayList<>(2);     //初始化3行
    }

    public ArrayList<String> getArrayList() {   //获得ArrayList属性
        setArrayList();
        return arrayList;
    }

    private void setArrayList() {   //设置ArrayList属性
        setKeyContent();    //设置KeyContent属性，调用ticketMatch.getKeyContent();
        setTitle();         //设置title属性，调用deliveryMatch.getTitle();
        arrayList.add(title);
        arrayList.add(keyContent);
    }

    /**
     * 返回给后端短信的类型
     * @return
     * 	0：普通短信
     * 	1：快递短信
     * 	2：车票短信
     */
    public int getisDeliverySMS() {   //返回是否车票信息的判断
        if (!hasJudgeSMS) {
            setIsSpecialSMS();
        }
        return isDeliverySMS;
    }

    /**
     * String形式返回应生成的备忘录内容
     * @return 备忘录内容
     */
    public String getKeyContent() {
        setIsSpecialSMS();
        setKeyContent();
        return keyContent;
    }

    private void setIsSpecialSMS() {       //运行过后isDeliverySMS、isTicketSMS被设置好
        judgeSMSContent();
    }

    /**
     * 判断是否为特殊短信
     */
    private void judgeSMSContent() {    //运行过后isDeliverySMS、isTicketSMS被设置好
        hasJudgeSMS = true;
        if (needPattern) {  //需要模型为true的话
            choseType();    //choseType()运行过后，type被设置好
        }
        if (type == 0) {
            isDeliverySMS = 0;
        } else {
            isDeliverySMS = 1;
        }
    }

    /**
     * 为短信选择正确类型值
     */
    private void choseType() {
        needPattern = false;
        String regex = ".*?\\【(.*?)\\】.*";		//优先匹配最先出现的【】中的字符
        Matcher matcher = Pattern.compile(regex).matcher(SMSText);
        if (!matcher.find() || Pattern.matches(".*等[您你]很久.*", SMSText) ||
                Pattern.matches(".*在线选座.*", SMSText)) {         //如果没找到模式||找到其他非特征字符
            System.out.println("【】中的字符不对");
            type = 0;       //为普通短信
        } else {            //匹配成功
            String typeJudge = matcher.group(1);
            //System.out.println(typeJudge);
            judgeType(typeJudge);
        }
    }

    /**
     * 为特殊短信匹配类型值
     * @param typeJudge
     */
    private void judgeType(String typeJudge) {  //设置短信类型type
        //快递短信类型值
        if (typeJudge.equals(TYPE1)) {
            type = 1;
        } else if (typeJudge.equals(TYPE2)) {
            type = 2;
        } else if (typeJudge.equals(TYPE3)) {
            type = 3;
        } else if (typeJudge.equals(TYPE4)) {
            type = 4;
        } else if (typeJudge.equals(TYPE5)) {
            type = 5;
        } else if (typeJudge.equals(TYPE6)) {
            type = 6;
        } else if (typeJudge.equals(TYPE7)) {
            type = 7;
        } else if (typeJudge.equals(TYPE8)) {
            type = 8;
        } else {
            type = 0;
        }
    }

    private void setKeyContent() {
        if (hasHandleSMS == false) {
            handleSMS();
        }
    }

    private void setTitle() {
        if (hasHandleSMS == false) {
            handleSMS();
        }
    }

    private void handleSMS() {
        if (hasHandleSMS == false) {
            if (type <= 0) {
                keyContent = "ERROR:识别结果为普通短信!";
            } else {
                DeliveryMatch deliveryMatch = new DeliveryMatch(SMSText, type);
                keyContent = deliveryMatch.getKeyContent(); //获取关键的信息（属性）。将setKeyContent()更改结果综合
                title = deliveryMatch.getTitle();           //title = "您有" + companyName + "待取货"
            }
            hasHandleSMS = true;
        }
    }

    public String getTitle() {
        // TODO Auto-generated method stub
        setTitle();
        return title;
    }
}
