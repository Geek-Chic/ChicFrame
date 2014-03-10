package com.geekchic.junit;

import android.test.AndroidTestCase;

import com.geekchic.base.http.CommHttpDBOps;
import com.geekchic.base.http.CommHttpInfoDBImpl;
import com.geekchic.base.http.CommHttpURL;

public class DBTest extends AndroidTestCase
{
    CommHttpDBOps commHttpDBOps;

    @Override
    protected void setUp() throws Exception
    {
        // TODO Auto-generated method stub
        super.setUp();
        commHttpDBOps=CommHttpInfoDBImpl.getINSTANCE(getContext());
    }
    public void testDb(){
       CommHttpURL commHttpUrl=new CommHttpURL();
    }
    public void testDbSearchByUrltest(){
        CommHttpURL commHttpInfo=commHttpDBOps.getUrl("http://www.baidu.com");
        System.out.println(commHttpInfo.toString());
    }
    @Override
    protected void tearDown() throws Exception
    {
        // TODO Auto-generated method stub
        super.tearDown();
    }
    
}
