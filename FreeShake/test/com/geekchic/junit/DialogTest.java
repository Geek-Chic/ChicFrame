/**
 * @Title: DialogTest.java
 * @Package com.geekchic.junit
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-8-7
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.junit;

import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.framework.ui.dialog.BasicDialog.Builder;
import com.geekchic.framework.ui.dialog.effect.Effectstype;

import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;

/**
 * @ClassName: DialogTest
 * @Descritpion: [用一句话描述作用] 
 * @author Administrator
 * @date 2014-8-7
 */
public class DialogTest  extends ApplicationTestCase<Application>
{

    public DialogTest(Class<Application> applicationClass)
    {
        super(applicationClass);
        BasicDialog basicDialog=new Builder(mContext)
        .setMessage("测试")
        .setEffectType(Effectstype.Shake)
        .setEffectDuration(700)
        .create();
    }
    
}
