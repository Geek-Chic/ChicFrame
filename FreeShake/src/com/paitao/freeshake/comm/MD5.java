package com.paitao.freeshake.comm;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5
{
    public static String encodeMD5String(String str){
        return encode(str,"MD5");
    }

    private static String encode(String str, String method)
    {
        // TODO Auto-generated method stub
        MessageDigest md=null;
        String dstr=null;
        try
        {
            md=MessageDigest.getInstance(method);
            md.update(str.getBytes());
            dstr=new BigInteger(1,md.digest()).toString(16);
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return dstr;
    }
    
}
