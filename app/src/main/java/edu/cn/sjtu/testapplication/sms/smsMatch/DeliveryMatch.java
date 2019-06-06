package edu.cn.sjtu.testapplication.sms.smsMatch;

/**
 * finished
 *
 * DeliveryMatch类用于识别快递短信
 * 先调用getIsDelivery()返回是否快递短信(通过匹配“快递”和“取货码”两个关键字判断)
 * 在通过公有函数接口返回需求关键字信息(公司名称，取件时间，取货所需，取件地址)
 *
 * 通用正则表达式
 * 	匹配至】后第一个字符：[^\\】*]\\】
 * 	匹配xx快递的xx(可以没有快递两字)：([^快递]+)[快递]{0,2}
 * 	匹配一个中文字符：[\u4e00-\u9fcc]
 * 	匹配逗号(,和，)：[\\,\\，]
 * 	匹配数字(0-9):[0-9]
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//DeliveryMatch为类，变量为属性，函数为方法
public class DeliveryMatch {
    private String SMSText;     //短信内容
    private int deliveryType;   //投递类别

    private String companyName; //快递名称
    private String deliveryTime;    //取件时间
    private String deliveryLocation;   //取件地点
    private String deliveryNeed;    //取件需求
    private String title;       //
    private boolean hasHandleSMS;       //是否处理标记

    DeliveryMatch(String SMSText, int deliveryType) {   //将短信内容和快递类别匹配到类（初始化类）
        this.SMSText = SMSText;
        this.deliveryType = deliveryType;
        companyName = null;
        deliveryTime = null;
        deliveryLocation = null;
        deliveryNeed = null;
        title = null;
        hasHandleSMS = false;
    }

    public String getKeyContent() {     //获取关键的信息（属性）。将setKeyContent()更改结果综合
        setKeyContent();
        return "快递名称：" + companyName + "\n" + "取货时间：" + deliveryTime + "\n"
                + "取货地点：" + deliveryLocation + "\n" + "取货所需：" + deliveryNeed + "\n";
    }

    private void setKeyContent() {      //更改此类中的companyName、deliveryTime、deliveryLocation、deliveryNeed属性
        if (hasHandleSMS == false) {    //如果未处理过
            matchContent();
            if (deliveryNeed == null) {
                deliveryNeed = "本条短信或收件人手机尾号";
            }
            if (deliveryTime == null) {
                deliveryTime = "工作时间内";
            }
            hasHandleSMS = true;
        }
    }

    private void matchContent() {   //匹配各个属性以更改类
        switch (deliveryType) {
            case 1:
                matchType1();		//菜鸟驿站
                break;
            case 2:
                matchType2();		//云喇叭
                break;
            case 3:
                matchType3();		//ems快递
                break;
            case 4:
                matchType4();		//妈妈驿站
                break;
            case 5:
                matchType5();		//顺丰速运
                break;
            case 6:
                matchType6();		//微快递
                break;
            case 7:
                matchType7();		//顺丰快递
                break;
            case 8:
                matchType8();		//丰巢
                break;
            default:
                System.out.println("ERROR:需要新增快递模板!");
        }
    }

    /**
     * 匹配【菜鸟驿站】
     * 利用正则表达式匹配，()内匹配为一个group。Java中正则表达式转义符匹配为\\
     * [^\\】*] 匹配除了“】”外所有字符，\\】匹配一次“】”，[您的]{0,2} 匹配“您”、“的”各0~2次
     * group(1)=([^快递]+) 匹配除了“快递”外的字符大于等于1次，[快递]{0,2} 匹配“快”、“递”各0~2次
     * group(2)=([^\\,\\，]+) 匹配除了“，”、“,”外所有字符大于等于1次
     * group(3)=(.*) 贪婪匹配除\n、\r外所有字符
     * group(4)=(.*)
     */
    private void matchType1() {
        String regex = "[^\\】*]\\】[您的]{0,2}([^快递]+)[快递]{0,2}包裹"
                + "到([^\\,\\，]+)" + "[\\,\\，]请(.*)凭(.*?)[及取].*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            // System.out.println(m.group(0));
            companyName = m.group(1) + "快递";
            deliveryTime = m.group(3);
            deliveryLocation = m.group(2);
            deliveryNeed = "取货码" + m.group(4);
        } else {
            System.out.println("【菜鸟驿站】匹配失败");
        }
    }

    /**
     * 匹配【云喇叭】
     * group(1)=([0-9]*)
     * group(2)=([^快递]+)
     * group(3)=([^\u4e00-\u9fcc]+)
     * group(4)=(.*)
     */
    private void matchType2() {
        String regex;
        if (!Pattern.matches(".*苏宁.*", SMSText)) {
            regex = "[^\\】*]\\】编号([0-9]*)([^快递]+)[快递]"
                    + "{0,2}请你([^\u4e00-\u9fcc]+)(.*)领取.*";
            Pattern p = Pattern.compile(regex);
            //System.out.println(p.pattern());
            Matcher m = p.matcher(SMSText);
            if (m.find()) {
                //System.out.println(m.group(0));
                companyName = m.group(2) + "快递";
                deliveryTime = m.group(3);
                deliveryLocation = m.group(4);
                deliveryNeed = "取货码--" + m.group(1);
            } else {
                System.out.println("【云喇叭】匹配失败");
            }
        } else {
            regex = ".*编号([0-9a-zA-Z]+).*在([^\\,\\，带]+).*到(.*)取.*";
            Pattern p = Pattern.compile(regex);
            //System.out.println(p.pattern());
            Matcher m = p.matcher(SMSText);
            if (m.find()) {
                //System.out.println(m.group(0));
                companyName = "苏宁易购";
                deliveryTime = m.group(2);
                deliveryLocation = m.group(3);
                deliveryNeed = "取货码--" + m.group(1);
            } else {
                System.out.println("【云喇叭】匹配失败");
            }
        }
    }

    /**
     * 匹配【EMS快递】
     * group(1)=([^\\,\\，]*)
     * group(2)=(.*)
     * group(3)=(.*)
     * group(4)=(.*)
     */
    private void matchType3() {
        String regex = "[^\\】*]\\】([^\\,\\，]*)[\\,\\，](.*)到(.*)领.*按(.*)领.*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            //System.out.println(m.group(0));
            companyName = m.group(1) + "快递";
            deliveryTime = m.group(2);
            deliveryLocation = m.group(3);
            deliveryNeed = "取货码--" + m.group(4);
        } else {
            System.out.println("【EMS快递】匹配失败");
        }
    }

    /**
     * 匹配【妈妈驿站】
     * group(1)=([0-9]*)
     * group(2)=([^快递]+)
     * group(3)=([0-9\u4e00-\u9fcc]+)
     * group(4)=([0-9\\:\\：\\-\\—]+)
     */
    private void matchType4() {
        String regex = "[^\\】*]\\】取货码([0-9]*)[\\,\\，您有]{0,3}([^快递]+)"
                + "快递[包裹]{0,2}[\\，\\,已到]{0,3}([0-9\u4e00-\u9fcc]+).*时间"
                + "([0-9\\:\\：\\-\\—]+).*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            //System.out.println(m.group(0));
            companyName = m.group(2) + "快递";
            deliveryTime = m.group(4);
            deliveryLocation = m.group(3);
            deliveryNeed = "取货码--" + m.group(1);
        } else {
            System.out.println("【妈妈驿站】匹配失败");
        }
    }

    /**
     * 匹配【顺丰速运】
     * group(1)=([^\\，||\\,]+)
     * group(2)=(.*)
     */
    private void matchType5() {
        String regex = ".*放置于([^\\，||\\,]+).*请持(.*)前往.*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            //System.out.println(m.group(0));
            companyName = "顺丰速运";
            deliveryLocation = m.group(1);
            deliveryNeed = m.group(2);
        } else {
            System.out.println("【顺丰速运】匹配失败");
        }
    }

    /**
     * 匹配【微快递】
     * group(1)=([\u4e00-\u9fcc]+)
     * group(2)=([0-9]+)
     * group(3)=([^\\,||\\，]*)
     * group(4)=(.*)
     */
    private void matchType6() {
        //String regex = ".*\\｛([^\\｝]+).*编码([0-9]+).*到([^\\,||\\，]*).*请(.*)及时.*";
        String regex = "[^\\】*]\\】[^\u4e00-\u9fcc]*([\u4e00-\u9fcc]+)[^\u4e00-\u9fcc]*.*包裹[编码]*([0-9]+).*到([^\\,||\\，]*).*请(.*)及时.*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            //System.out.println(m.group(0));
            companyName = m.group(1);
            deliveryTime = m.group(4);
            deliveryLocation = m.group(3);
            deliveryNeed = m.group(2);
        } else {
            System.out.println("【微快递】匹配失败");
        }
    }

    /**
     * 匹配【顺丰快递】
     * group(1)=(.*)
     * group(2)=(.*)
     */
    private void matchType7() {
        String regex = ".*请(.*)到(.*)领取.*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            //System.out.println(m.group(0));
            companyName = "顺丰快递";
            deliveryTime = m.group(1);
            deliveryLocation = m.group(2);
        } else {
            System.out.println("【顺丰速运】匹配失败");
        }
    }

    /**
     * 匹配【丰巢】
     * group(1)=([0-9]+)
     * group(2)=(.*)
     * group(3)=(.*)
     */
    private void matchType8() {
        String regex = ".*取件码\\『{0,1}([0-9]+)\\』{0,1}至(.*)取(.*)的.*";
        Pattern p = Pattern.compile(regex);
        //System.out.println(p.pattern());
        Matcher m = p.matcher(SMSText);
        if (m.find()) {
            //System.out.println(m.group(0));
            companyName = m.group(3);
            //deliveryTime = m.group(4);
            deliveryLocation = m.group(2);
            deliveryNeed = "取货码--" + m.group(1);
        } else {
            System.out.println("【微快递】匹配失败");
        }
    }

    public String getTitle() {
        // TODO Auto-generated method stub
        setKeyContent();
        title = "您有" + companyName + "待取货";
        return title;
    }
}