package com.geekchic.junit;

import java.io.InputStream;

import android.test.AndroidTestCase;

import com.alibaba.fastjson.JSON;
import com.geekchic.base.http.CommHttpRequest;
import com.geekchic.common.log.Logger;
import com.geekchic.wuyou.bean.UserInfo;

public class NetTest extends AndroidTestCase
{
    static final String PATH_INPUTSTREAM = "http://qiuming.sinaapp.com/";
    static final String PATH_STRING = "http://qiuming.sinaapp.com/";
    static final String PATH_BITMAP = "http://tp3.sinaimg.cn/1859125850/180/5628821209/1";
    static final String PATH_WITHPARAMS = "http://qiuming.sinaapp.com/";
    static final String PATH_POSTCONTENT = "http://qiuming.sinaapp.com/";
    @Override
    protected void setUp() throws Exception
    {
        // TODO Auto-generated method stub
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception
    {
        // TODO Auto-generated method stub
        super.tearDown();
    }
    public void testInputStreamRequest(){
        CommHttpRequest request=CommHttpRequest.requestWithURL(getContext(), PATH_INPUTSTREAM);
        InputStream is=request.startSynchronous();
        assertNotNull(is);
        if(is!=null){
            System.out.println(is.toString());
        }
        
    }
    public void testJSON(){
    	UserInfo userInfo=new UserInfo();
    	userInfo.setPhone("13163353639");
    	userInfo.setSessionId("assdfdfvbxsdfasdfasdf");
    	String testString="{'phone':'13163353639'}";
    	UserInfo userInfo2=JSON.parseObject(testString, UserInfo.class);
    	Logger.d("evil", userInfo2.toString());
    }
    public void testStringRequest(){
        
    }
    
}
