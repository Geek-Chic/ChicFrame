package com.geekchic.common;

public class MqttUtils
{
    /**
     * ����MqttЭ����Remaining Length���ȣ�ռ1-4���ֽڲ���
     * @param strLength
     * @return
     */
    public static String encodeProtocalLength(int strLength){
        int digit=0,tempLength=strLength;
        StringBuilder res=new StringBuilder();
         do
        {
             digit=tempLength % 128;
             tempLength/=128;
             if(tempLength>0){
                 digit=digit | 0x80;
             }
             res.append((char)digit);
        } while (tempLength>0);
         return res.toString();
    }
    /**
     * ����MQTTЭ����Remaining Length�ַ�ԭΪ����
     * @param arry
     * @return
     */
    public static int deCodeProtocalLength(String arry){
       char[] tempArray=arry.toCharArray();
       int multiplier=1,value=0,digit=0;
       int index=0;
       do
    {
           digit=tempArray[index];
           value+=(digit & 127 )*multiplier;
           multiplier*=128;
           index++;
    } while ((digit &128)!=0&&index<tempArray.length);
        return value;
    }
    
}
