/**
 * @Title: ShareTest.java
 * @Package com.geekchic.junit
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-7-7
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.junit;

import com.geekchic.base.share.ShareManager;

import android.test.AndroidTestCase;

/**
 * @ClassName: ShareTest
 * @Descritpion: [用一句话描述作用] 
 * @author Administrator
 * @date 2014-7-7
 */
public class ShareTest extends AndroidTestCase
{

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
    
    public void testShareService(){
        ShareManager shareManager=ShareManager.getInstance(getContext());
        
    }
}
