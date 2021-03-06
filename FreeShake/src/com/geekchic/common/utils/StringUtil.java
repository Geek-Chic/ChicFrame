
package com.geekchic.common.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.text.TextUtils;


public class StringUtil
{
    private static final Logger logger=LoggerFactory.getLogger("StringUtil");
    
    /**
     * 手机号码 正则验证
     */
    private static final String NUMBER_REGEX = "(\\+86|86|0086)?(13[0-9]|15[0-35-9]|14[57]|18[02356789])\\d{8}";
    
    /**
     * 国内移动号码
     */
    private static final String MOBILE_REGEX = "(0|\\+971|00971)\\D?(50|55|56)\\D?\\d{7,8}?$";
    
    // /**
    // * 国际移动号码
    // */
    // private static final String FOREIGN_MOBILE_REGEX =
    // "(\\+86|0086)\\D?(13[0-9]|15[0-35-9]|14[57]|18[02356789])\\d{8}";
    
    /**
     * 国内固话
     */
    private static final String PSTN_REGEX = "(0|\\+971|00971)\\D?(2|3|4|6|7|9)\\D?\\d{7,8}?$";
    
    // /**
    // * 国际固话
    // */
    // private static final String FOREIGN_PSTN_REGEX =
    // "(\\+86|0086)\\D?(025)\\d{7,8}";
    
    /**
     * voip C-C
     */
    private static final String C2C_REGEX = "(0|\\+971)\\D?(50|55|56)\\d{7,8}?$";
    
    /**
     * #过滤标签的正则表达式
     */
    private static final String REGEX = "#(.*?)#";
    
    /**
     * 判断是否是电话号码<BR>
     * 
     * @param resetInfo
     *            输入的号码
     * @return true 是电话号码 false不是电话号码
     */
    public static boolean isPhoneNumber(String resetInfo)
    {
        return Pattern.matches(NUMBER_REGEX, resetInfo);
    }
    
    /**
     * 根据号码规整出来C-P
     * 
     * @param number
     *            电话号码
     * @return 是否可以拨打C-P电话
     */
    public static boolean canCallC2P(String number)
    {
        if (isNullOrEmpty(number))
        {
            return false;
        }
        return Pattern.matches(MOBILE_REGEX, number)
                | Pattern.matches(PSTN_REGEX, number) ? true
                : number.startsWith("00") | number.startsWith("+");
    }
    
    /**
     * 根据号码规整出来C-C
     * 
     * @param number
     *            电话号码
     * @return 是否可以拨打C-C电话
     */
    public static boolean isCanCallC2C(String number)
    {
        if (isNullOrEmpty(number))
        {
            return false;
        }
        return Pattern.matches(C2C_REGEX, number);
    }
    
    /**
     * 判断是否为null或空值
     * 
     * @param str
     *            String
     * @return true or false
     */
    public static boolean isNullOrEmpty(String str)
    {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * 判断str1和str2是否相同
     * 
     * @param str1
     *            str1
     * @param str2
     *            str2
     * @return true or false
     */
    public static boolean equals(String str1, String str2)
    {
        return str1 == str2 || str1 != null && str1.equals(str2);
    }
    
    /**
     * 判断str1和str2是否相同(不区分大小写)
     * 
     * @param str1
     *            str1
     * @param str2
     *            str2
     * @return true or false
     */
    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }
    
    /**
     * 
     * 判断字符串str1是否包含字符串str2
     * 
     * @param str1
     *            源字符串
     * @param str2
     *            指定字符串
     * @return true源字符串包含指定字符串，false源字符串不包含指定字符串
     */
    public static boolean contains(String str1, String str2)
    {
        return str1 != null && str1.contains(str2);
    }
    
    /**
     * 
     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
     * 
     * @param str
     *            待判断字符串
     * @return 判断后的字符串
     */
    public static String getString(String str)
    {
        return str == null ? "" : str;
    }
    
    /**
     * 过滤HTML标签，取出文本内容
     * 
     * @param inputString
     *            HTML字符串
     * @return 过滤了HTML标签的字符串
     */
    public static String filterHtmlTag(String inputString)
    {
        String htmlStr = inputString;
        String textStr = "";
        Pattern pScript;
        Matcher mScript;
        Pattern pStyle;
        Matcher mStyle;
        Pattern pHtml;
        Matcher mHtml;
        try
        {
            // 定义script的正则表达式
            String regExScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式
            String regExStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regExHtml = "<[^>\"]+>";
            
            pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
            mScript = pScript.matcher(htmlStr);
            // 过滤script标签
            htmlStr = mScript.replaceAll("");
            
            pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
            mStyle = pStyle.matcher(htmlStr);
            // 过滤style标签
            htmlStr = mStyle.replaceAll("");
            
            pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
            mHtml = pHtml.matcher(htmlStr);
            // 过滤html标签
            htmlStr = mHtml.replaceAll("");
            
            textStr = htmlStr;
            
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        
        return textStr;
    }
    
    /**
     * 将字符串数组转化为字符串，默认以","分隔
     * 
     * @param values
     *            字符串数组
     * @param split
     *            分隔符，默认为","
     * @return 有字符串数组转化后的字符串
     */
    public static String arrayToString(String[] values, String split)
    {
        StringBuffer buffer = new StringBuffer();
        if (values != null)
        {
            if (split == null)
            {
                split = ",";
            }
            int len = values.length;
            for (int i = 0; i < len; i++)
            {
                buffer.append(values[i]);
                if (i != len - 1)
                {
                    buffer.append(split);
                }
            }
        }
        return buffer.toString();
    }
    
    /**
     * 将字符串list转化为字符串，默认以","分隔<BR>
     * 
     * @param strList
     *            字符串list
     * @param split
     *            分隔符，默认为","
     * @return 组装后的字符串对象
     */
    public static String listToString(Collection<String> strList, String split)
    {
        String[] values = null;
        if (strList != null)
        {
            values = new String[strList.size()];
            strList.toArray(values);
        }
        return arrayToString(values, split);
    }
    
    /**
     * 验证字符串是否符合email格式
     * 
     * @param email
     *            需要验证的字符串
     * @return 验证其是否符合email格式，符合则返回true,不符合则返回false
     */
    public static boolean isEmail(String email)
    {
        // 通过正则表达式验证email是否合法
        if (email == null)
        {
            return false;
        }
        if (email.toLowerCase(Locale.getDefault())
                .matches("^([a-zA-Z0-9_\\.-])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$"))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 验证字符串是否为数字
     * 
     * @param str
     *            需要验证的字符串
     * @return 不是数字返回false，是数字就返回true
     */
    public static boolean isNumeric(String str)
    {
        return str != null && str.matches("[0-9]*");
    }
    
    /**
     * 验证字符串是否符合手机号格式 电话号码格式 +9715xxxxxxxx（备注：971为电话代码）
     * 
     * @param str
     *            需要验证的字符串
     * @return 不是手机号返回false，是手机号就返回true
     */
    public static boolean isMobile(String str)
    {
        return str != null
                && str.matches("\\+971?5[0-9]\\d{3}\\d{4}")
                || str.matches("5[0-9]\\d{3}\\d{4}")
                || str.matches("(\\+86|86|0086)?(13[0-9]|15[0-35-9]|14[57]|18[02356789])\\d{8}");
    }
    
    /**
     * 验证字符串是否符合手机号格式 电话号码格式 +9715xxxxxxxx（备注：971为电话代码）
     * 
     * @param str
     *            手机号码字符串
     * @return 是手机号码格式返回true，反之false
     */
    public static boolean isMobile2(String str)
    {
        // return str != null
        // && str.matches("\\+971\\-5[0-9]\\-[0-9]{3}\\-[0-9]{4}");
        return str != null
                && str.matches("(\\+86|86|0086)?(13[0-9]|15[0-35-9]|14[57]|18[02356789])\\d{8}");
    }
    
    /**
     * 验证字符串是否符合手机号格式 电话号码格式 +9715xxxxxxxx（备注：971为电话代码）
     * 
     * @param str
     *            手机号码字符串
     * @return 是手机号码格式返回true，反之false
     */
    public static boolean isMobile3(String str)
    {
        // return str != null && str.matches("5[0-9]\\-[0-9]{3}\\-[0-9]{4}");
        return str != null
                && str.matches("(\\+86|86|0086)?(13[0-9]|15[0-35-9]|14[57]|18[02356789])\\d{8}");
    }
    
    /**
     * 替换字符串中特殊字符
     * 
     * @param strData
     *            源字符串
     * @return 替换了特殊字符后的字符串，如果strData为NULL，则返回空字符串
     */
    public static String encodeString(String strData)
    {
        if (strData == null)
        {
            return "";
        }
        return strData.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("'", "&apos;")
                .replaceAll("\"", "&quot;");
    }
    
    /**
     * 还原字符串中特殊字符
     * 
     * @param strData
     *            strData
     * @return 还原后的字符串
     */
    public static String decodeString(String strData)
    {
        if (strData == null)
        {
            return "";
        }
        return strData.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&apos;", "'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&");
    }
    
    /**
     * 
     * 组装XML字符串<BR>
     * [功能详细描述]
     * 
     * @param map
     *            键值Map
     * @return XML字符串
     */
    public static String generateXml(Map<String, Object> map)
    {
        
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<root>");
        if (map != null)
        {
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry<String, Object> entry = it.next();
                String key = entry.getKey();
                xml.append("<");
                xml.append(key);
                xml.append(">");
                xml.append(entry.getValue());
                xml.append("</");
                xml.append(key);
                xml.append(">");
            }
        }
        xml.append("</root>");
        return xml.toString();
    }
    
    /**
     * 
     * 组装XML字符串<BR>
     * [功能详细描述]
     * 
     * @param values
     *            key、value依次排列
     * @return XML字符串
     */
    public static String generateXml(String... values)
    {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<root>");
        if (values != null)
        {
            int size = values.length >> 1;
            for (int i = 0; i < size; i++)
            {
                xml.append("<");
                xml.append(values[i << 1]);
                xml.append(">");
                xml.append(values[(i << 1) + 1]);
                xml.append("</");
                xml.append(values[i << 1]);
                xml.append(">");
            }
        }
        xml.append("</root>");
        return xml.toString();
    }
    
    /**
     * 将srcString按split拆分，并组装成List。默认以','拆分。<BR>
     * 
     * @param srcString
     *            源字符串
     * @param split
     *            分隔符
     * @return 返回list
     */
    public static List<String> parseStringToList(String srcString, String split)
    {
        List<String> list = null;
        if (!StringUtil.isNullOrEmpty(srcString))
        {
            if (split == null)
            {
                split = ",";
            }
            String[] strArr = srcString.split(split);
            if (strArr != null && strArr.length > 0)
            {
                list = new ArrayList<String>(strArr.length);
                for (String str : strArr)
                {
                    list.add(str);
                }
            }
        }
        return list;
    }
    
    /**
     * 
     * 去掉url中多余的斜杠
     * 
     * @param url
     *            字符串
     * @return 去掉多余斜杠的字符串
     */
    public static String fixUrl(String url)
    {
        if (null == url)
        {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        for (int i = stringBuffer.indexOf("//", stringBuffer.indexOf("//") + 2); i != -1; i = stringBuffer.indexOf("//",
                i + 1))
        {
            stringBuffer.deleteCharAt(i);
        }
        return stringBuffer.toString();
    }
    
    /**
     * 
     * 按照一个汉字两个字节的方法计算字数
     * 
     * @param string
     *            String
     * @return 返回字符串's count
     */
    public static int count2BytesChar(String string)
    {
        int count = 0;
        if (string != null)
        {
            for (char c : string.toCharArray())
            {
                count++;
                if (isChinese(c))
                {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * 判断字符串中是否包含中文 <BR>
     * [功能详细描述] [added by 杨凡]
     * 
     * @param str
     *            检索的字符串
     * @return 是否包含中文
     */
    public static boolean hasChinese(String str)
    {
        boolean hasChinese = false;
        if (str != null)
        {
            for (char c : str.toCharArray())
            {
                if (isChinese(c))
                {
                    hasChinese = true;
                    break;
                }
            }
        }
        return hasChinese;
    }
    
    /**
     * 
     * 截取字符串，一个汉字按两个字符来截取<BR>
     * [功能详细描述] [added by 杨凡]
     * 
     * @param src
     *            源字符串
     * @param charLength
     *            字符长度
     * @return 截取后符合长度的字符串
     */
    public static String subString(String src, int charLength)
    {
        if (src != null)
        {
            int i = 0;
            for (char c : src.toCharArray())
            {
                i++;
                charLength--;
                if (isChinese(c))
                {
                    charLength--;
                }
                if (charLength <= 0)
                {
                    if (charLength < 0)
                    {
                        i--;
                    }
                    break;
                }
            }
            return src.substring(0, i);
        }
        return src;
    }
    
    /**
     * 对字符串进行截取, 超过则以...结束
     * 
     * @param originStr
     *            原字符串
     * @param maxCharLength
     *            最大字符数
     * @return 截取后的字符串
     */
    public static String trim(String originStr, int maxCharLength)
    {
        if (TextUtils.isEmpty(originStr))
        {
            return "";
        }
        int count = 0;
        int index = 0;
        int originLen = originStr.length();
        for (index = 0; index < originLen; index++)
        {
            char c = originStr.charAt(index);
            int len = 1;
            if (isChinese(c))
            {
                len++;
            }
            if (count + len <= maxCharLength)
            {
                count += len;
            }
            else
            {
                break;
            }
        }
        
        if (index < originLen)
        {
            return originStr.substring(0, index) + "...";
        }
        else
        {
            return originStr;
        }
    }
    
    /**
     * 
     * 判断参数c是否为中文<BR>
     * [功能详细描述] [added by 杨凡]
     * 
     * @param c
     *            char
     * @return 是中文字符返回true，反之false
     */
    public static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
        
    }
    
    /**
     * 
     * 检测密码强度
     * 
     * @param password
     *            密码
     * @return 密码强度（1：低 2：中 3：高）
     */
    public static int checkStrong(String password)
    {
        boolean num = false;
        boolean lowerCase = false;
        boolean upperCase = false;
        boolean other = false;
        
        int threeMode = 0;
        int fourMode = 0;
        
        for (int i = 0; i < password.length(); i++)
        {
            // 单个字符是数字
            if (password.codePointAt(i) >= 48 && password.codePointAt(i) <= 57)
            {
                num = true;
            }
            // 单个字符是小写字母
            else if (password.codePointAt(i) >= 97
                    && password.codePointAt(i) <= 122)
            {
                lowerCase = true;
            }
            // 单个字符是大写字母
            else if (password.codePointAt(i) >= 65
                    && password.codePointAt(i) <= 90)
            {
                upperCase = true;
            }
            // 特殊字符
            else
            {
                other = true;
            }
        }
        
        if (num)
        {
            threeMode++;
            fourMode++;
        }
        
        if (lowerCase)
        {
            threeMode++;
            fourMode++;
        }
        
        if (upperCase)
        {
            threeMode++;
            fourMode++;
        }
        
        if (other)
        {
            fourMode = fourMode + 1;
        }
        
        // 数字、大写字母、小写字母只有一个，密码强度低（只有一种特殊字符也是密码强度低）
        if (threeMode == 1 && !other || fourMode == 1)
        {
            return 1;
        }
        // 四种格式有其中两个，密码强度中
        else if (fourMode == 2)
        {
            return 2;
        }
        // 四种格式有三个或者四个，密码强度高
        else if (fourMode >= 3)
        {
            return 3;
        }
        // 正常情况下不会出现该判断
        else
        {
            return 3;
        }
    }
    
    /**
     * 
     * 返回一个制定长度范围内的随机字符串
     * 
     * @param min
     *            范围下限
     * @param max
     *            范围上限
     * @return 字符串
     */
    public static String createRandomString(int min, int max)
    {
        StringBuffer strB = new StringBuffer();
        Random random = new Random();
        int lenght = min;
        if (max > min)
        {
            lenght += random.nextInt(max - min + 1);
        }
        for (int i = 0; i < lenght; i++)
        {
            strB.append((char) (97 + random.nextInt(26)));
        }
        return strB.toString();
    }
    
    /**
     * 
     * [用于获取字符串中字符的个数]<BR>
     * [功能详细描述]
     * 
     * @param content
     *            文本内容
     * @return 返回字符的个数
     */
    public static int getStringLeng(String content)
    {
        return (int) Math.ceil(count2BytesChar(content) / 2.0);
    }
    
    /**
     * 
     * 根据参数tag（XML标签）解析该标签对应的值<BR>
     * 本方法针对简单的XML文件，仅通过字符串截取的方式获取标签值
     * 
     * @param xml
     *            XML文件字符串
     * @param tag
     *            XML标签名，说明：标签名不需加“<>”，方法中已做处理
     * @return 标签对应的值
     */
    public static String getXmlValue(String xml, String tag)
    {
        if (xml == null || tag == null)
        {
            logger.error("XML OR TAG is null!");
            return null;
        }
        
        // 如果标签中包含了"<"或">"，先去掉
        tag = tag.replace("<", "").replace(">", "");
        
        // 截取值
        int index = xml.indexOf(tag);
        if (index != -1)
        {
            int endIndex = xml.indexOf('<', index);
            if (endIndex != -1)
            {
                return xml.substring(index + tag.length() + 1, endIndex);
            }
        }
        
        logger.error("No such tag : " + tag + " was found!");
        return null;
    }
    
    /**
     * 去掉字符串前后空格
     * 
     * @param string
     *            要去掉前后空格的字符串
     * @return 去掉前后空格的字符串
     */
    public static String stringTrimAllSpace(String string)
    {
        if (TextUtils.isEmpty(string))
        {
            return "";
        }
        string = string.trim();
        while (string.startsWith(" "))
        {
            string = string.substring(1, string.length());
        }
        return string;
    }
    
    /**
     * 根据业务拼装电话号码<BR>
     * 
     * @param number
     *            电话号码
     * @return 拼装后的电话号码
     */
    public static String fixPortalPhoneNumber(String number)
    {
        if (StringUtil.isNullOrEmpty(number))
        {
            return number;
        }
        
        String retPhoneNumber = number.trim();
        
        // 确定是否是手机号码，然后将前缀去除，只保留纯号码
        if (isMobile(retPhoneNumber))
        {
            if (retPhoneNumber.startsWith("+86"))
            {
                retPhoneNumber = retPhoneNumber.substring(3);
            }
            else if (retPhoneNumber.startsWith("86"))
            {
                retPhoneNumber = retPhoneNumber.substring(2);
            }
            else if (retPhoneNumber.startsWith("0086"))
            {
                retPhoneNumber = retPhoneNumber.substring(4);
            }
        }
        
        return retPhoneNumber;
    }
    
    /**
     * 
     * 生成唯一的字符串对象<BR>
     * 
     * @return 唯一的字符串
     */
    public static String generateUniqueID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * 
     * @param jid
     *            jid
     * @return String
     */
    public static String[] splitJidAndRealmName(String jid)
    {
        if (null != jid)
        {
            return jid.split("@");
        }
        return null;
    }
    
    /**
     * Finds the index of the first word that starts with the given prefix.
     * <p>
     * 
     * @param text
     *            the text in which to search for the prefix
     * @param prefix
     *            the text to find, in upper case letters
     * @return index If not found,returns -1.
     */
    public static int indexOfWordPrefix(CharSequence text, char[] prefix)
    {
        if (prefix == null || text == null)
        {
            return -1;
        }
        
        int textLength = text.length();
        int prefixLength = prefix.length;
        
        if (prefixLength == 0 || textLength < prefixLength)
        {
            return -1;
        }
        
        int i = 0;
        while (i < textLength)
        {
            // Skip non-word characters
            while (i < textLength && !Character.isLetterOrDigit(text.charAt(i)))
            {
                i++;
            }
            
            if (i + prefixLength > textLength)
            {
                return -1;
            }
            
            // Compare the prefixes
            int j;
            for (j = 0; j < prefixLength; j++)
            {
                if (Character.toUpperCase(text.charAt(i + j)) != prefix[j])
                {
                    break;
                }
            }
            if (j == prefixLength)
            {
                return i;
            }
            
            // Skip this word
            while (i < textLength && Character.isLetterOrDigit(text.charAt(i)))
            {
                i++;
            }
        }
        
        return -1;
    }
    
    /**
     * 進一步过滤HTML标签，取出文本内容
     * 
     * @param inputString
     *            HTML字符串
     * @return 过滤了HTML标签的字符串
     */
    public static String filterHtmlTagDeeply(String inputString)
    {
        String htmlStr = filterHtmlTag(inputString).replace("&nbsp;", " ");
        Pattern pHtml;
        Matcher mHtml;
        
        try
        {
            // 定义HTML标签的正则表达式
            String regExHtml = "<[^>]+>";
            
            pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
            mHtml = pHtml.matcher(htmlStr);
            // 过滤html标签
            htmlStr = mHtml.replaceAll("");
            
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        
        return htmlStr;
    }
    
    /**
     * 
     * 获取一个操作序列号<BR>
     * 
     * @return 序列号 消息格式hhmissSSS，如果首位为0则去掉首位 如果为 000000000 则返回0
     */
    public static String getSeq()
    {
        String result = DateUtil.getMillisecond();
        while (result.startsWith("0"))
        {
            result = result.substring(1, result.length());
        }
        if (result.length() == 0)
        {
            result = "0";
        }
        return result;
    }
    
    /**
     * 判断字符串中是否包含中文<BR>
     * 
     * @param string
     *            传入的参数
     * @return 是否包含中文
     */
    public static boolean containChinese(String string)
    {
        if (StringUtil.isNullOrEmpty(string))
        {
            return false;
        }
        else
        {
            for (char ch : string.toCharArray())
            {
                if (isChinese(ch))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * #过滤标签的<BR>
     * 
     * @param str
     *            原字符串
     * @return 返回字符串中热门标签list 1不带＃号
     */
    public static List<String> getSharpTag(String str)
    {
        List<String> strlist = new ArrayList<String>();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find())
        {
            strlist.add(matcher.group(1));
        }
        return strlist;
    }
    
    /**
     * 将毫秒数改成时间00:00显示<BR>
     * 
     * @param millisInFuture
     *            毫秒数
     * @return 00:00显示的string
     */
    public static String getCountDownTimerText(long millisInFuture)
    {
        int times = (int) (millisInFuture / 1000);
        int minute = times / 60;
        int second = times % 60;
        StringBuffer timeSB = new StringBuffer();
        timeSB.append(getTimeStr(minute));
        timeSB.append(":");
        timeSB.append(getTimeStr(second));
        return timeSB.toString();
    }
    
    /**
     * 将个位数前补0组成2位数<BR>
     * 
     * @param minute
     *            分钟或毫米数
     * @return 将个位数前补0组成2位数
     */
    private static String getTimeStr(int minute)
    {
        if (minute < 10)
        {
            StringBuffer minuteSB = new StringBuffer("0");
            minuteSB.append(minute);
            return minuteSB.toString();
        }
        else
        {
            return String.valueOf(minute);
        }
    }
    
    /**
     * 正常显示价格，去除大数自动转换为科学计数法<BR>
     * 
     * @param price
     *            价格
     * @return 返回价格显示字符串
     */
    public static String getPriceStr(Double price)
    {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        // 保证价格小数位数为0-2位，以防Double计算出现多位小数情况
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setMaximumFractionDigits(2);
        // 去除所有字符串中的价格分隔符
        String resPrice = decimalFormat.format(price).replace(",", "");
        return resPrice;
    }
}
