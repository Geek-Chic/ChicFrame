package com.geekchic.junit;

import android.test.AndroidTestCase;

import com.geekchic.base.http.CommHttpDBOps;
import com.geekchic.base.http.CommHttpInfoDBImpl;
import com.geekchic.base.http.CommHttpURL;
import com.geekchic.wuyou.bean.UserInfo;
import com.geekchic.wuyou.model.UserDao;
import com.geekchic.wuyou.model.UserDaoHelper;

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
    public void testDbUser(){
    	UserDao userDao=UserDao.getINSTANCE(getContext());
    	UserInfo userInfo=new UserInfo();
    	userInfo.setUuid("xcvzlxkjvalsdf");
    	userInfo.setPhone("13163353639");
    	
    	userDao.insert(userInfo);
    }
    @Override
    protected void tearDown() throws Exception
    {
        // TODO Auto-generated method stub
        super.tearDown();
    }
    
}
