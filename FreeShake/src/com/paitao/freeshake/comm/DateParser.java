package com.paitao.freeshake.comm;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateParser
{
    private static final String TAG="DateParser";
    private static String[] ADDITIONAL_MASKS;
    static {
//      ADDITIONAL_MASKS = PropertiesLoader.getPropertiesLoader().getTokenizedProperty("datetime.extra.masks","|");
      ADDITIONAL_MASKS=new String[]{"HH:mm yyyy/MM/dd","yyyy-MM-dd HH:mm:ss",
     "yyyy-MM-dd HH:mm"};
  }
    //由于SimpleDateFormat.parse 并不会因为异常而失败，所以我们要先检查其有有效性
    private static final String[] RFC822_MASKS = {
        "EEE, dd MMM yy HH:mm:ss z",
        "EEE, dd MMM yy HH:mm z",
        "dd MMM yy HH:mm:ss z",
        "dd MMM yy HH:mm z"
    };
    private static final String[] W3CDATETIME_MASKS = {
        "yyyy-MM-dd'T'HH:mm:ss.SSSz",
        "yyyy-MM-dd't'HH:mm:ss.SSSz",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd't'HH:mm:ss.SSS'z'",
        "yyyy-MM-dd'T'HH:mm:ssz",
        "yyyy-MM-dd't'HH:mm:ssz",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd't'HH:mm:ss'z'",
        "yyyy-MM-dd'T'HH:mmz",   // together with logic in the parseW3CDateTime they
        "yyyy-MM'T'HH:mmz",      // handle W3C dates without time forcing them to be GMT
        "yyyy'T'HH:mmz",          
        "yyyy-MM-dd't'HH:mmz", 
        "yyyy-MM-dd'T'HH:mm'Z'", 
        "yyyy-MM-dd't'HH:mm'z'", 
        "yyyy-MM-dd",
        "yyyy-MM",
        "yyyy"
    };
    private static final String[] MASKS = {
        "yyyy-MM-dd'T'HH:mm:ss.SSSz",
        "yyyy-MM-dd't'HH:mm:ss.SSSz",                         // invalid
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd't'HH:mm:ss.SSS'z'",                       // invalid
        "yyyy-MM-dd'T'HH:mm:ssz",
        "yyyy-MM-dd't'HH:mm:ssz",                             // invalid
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd't'HH:mm:ss'z'",                           // invalid
        "yyyy-MM-dd'T'HH:mmz",                                // invalid
        "yyyy-MM-dd't'HH:mmz",                                // invalid
        "yyyy-MM-dd'T'HH:mm'Z'",                              // invalid
        "yyyy-MM-dd't'HH:mm'z'",                              // invalid
        "yyyy-MM-dd",
        "yyyy-MM",
        "yyyy"
      };
   private DateParser(){}
   private static Date parseUsingMask(String[] masks,String sDate){
       sDate=(sDate!=null)?sDate.trim():null;
       ParsePosition parsePosition=null;
       Date date=null;
       for(int i=0;date==null && i<masks.length;i++){
           DateFormat dateFormat=new SimpleDateFormat(masks[i], Locale.CHINA);
           dateFormat.setLenient(true);
           try
        {
            parsePosition=new ParsePosition(0);
            date=dateFormat.parse(sDate,parsePosition);
            if(parsePosition.getIndex()!=sDate.length()){
                date=null;
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
       }
    return date;
   }
   /**
    * 通过RFC822样式解析String型日期值
    * RFC822模版如下：
     * <ul>
     *   <li>"EEE, dd MMM yyyy HH:mm:ss z"</li>
     *   <li>"EEE, dd MMM yyyy HH:mm z"</li>
     *   <li>"EEE, dd MMM yy HH:mm:ss z"</li>
     *   <li>"EEE, dd MMM yy HH:mm z"</li>
     *   <li>"dd MMM yyyy HH:mm:ss z"</li>
     *   <li>"dd MMM yyyy HH:mm z"</li>
     *   <li>"dd MMM yy HH:mm:ss z"</li>
     *   <li>"dd MMM yy HH:mm z"</li>
     * </ul>
    * @param sDate
    * @return RFC822表示的Date引用
    * 如果无法解析成功则返回null
    */
    public static Date parseRFC822(String sDate){
        int utIndex=sDate.indexOf(" UT");
        if(utIndex>-1){
            String pre=sDate.substring(0,utIndex);
            String post=sDate.substring(utIndex+3);
            sDate=pre+" GMT"+post;
        }
        return parseUsingMask(RFC822_MASKS, sDate);
    }
    /**
     * 按W3C日期样式解析String型日期值
     *  <ul>
     *   <li>"yyyy-MM-dd'T'HH:mm:ssz"</li>
     *   <li>"yyyy-MM-dd'T'HH:mmz"</li>
     *   <li>"yyyy-MM-dd"</li>
     *   <li>"yyyy-MM"</li>
     *   <li>"yyyy"</li>
     * </ul>
     * @param sDate
     * @return W3C表示的Date引用
     * 如果无法解析成功则返回null
     */
    public static Date parseW3CDateTime(String sDate){
        int tIndex=sDate.indexOf("T");
        if(tIndex>-1){
            if(sDate.endsWith("Z")){
                sDate=sDate.substring(0,sDate.length()-1)+"+00:00";
            }
            int tzdIndex=sDate.indexOf("+", tIndex);
            if(tzdIndex==-1){
                tzdIndex=sDate.indexOf("-", tIndex);
            }
            if(tzdIndex>-1){
                String pre=sDate.substring(0,tzdIndex);
                int secFraction=pre.indexOf(",");
                if(secFraction>-1){
                    pre=pre.substring(0,secFraction);
                }
                String post=sDate.substring(tzdIndex);
                sDate=pre+"GMT"+post;
            }
        }else {
            sDate+="T00:00GMT";
        }
        return parseUsingMask(W3CDATETIME_MASKS, sDate);
    }
    public static Date parseDate(String sDate){
        Date date=parseW3CDateTime(sDate);
        if(date==null){
            date=parseRFC822(sDate);
            if(date==null && ADDITIONAL_MASKS.length>0){
                date=parseUsingMask(ADDITIONAL_MASKS, sDate);
            }
        }
        return date;
    }
    /**
     * 生成RFC822 Date型日期数据
     * @param date
     * @return
     */
    public static String formatRFC822(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss'GMT'",Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }
    /**
     * 生成W3C Date型日期数据
     * @param date
     * @return
     */
    public static String formatW3CDateTime(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }
}
