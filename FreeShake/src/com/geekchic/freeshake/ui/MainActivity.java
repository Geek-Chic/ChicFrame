package com.geekchic.freeshake.ui;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import com.geekchic.base.http.CommHttpRequest;
import com.geekchic.base.http.CommHttpRequest.CommHttpRequestLinstener;
import com.geekchic.common.view.ctransview.CtransAdapter;
import com.geekchic.common.view.ctransview.CtransMenu;
import com.geekchic.freeshake.R;
import com.geekchic.freeshake.module.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener
{
    Logger log=LoggerFactory.getLogger(MainActivity.class);
    private CtransMenu mCtransMenu;
    private CtransAdapter mCtransAdapter;
    private Button mPopupButton,mRotateButton;
    static final String PATH_INPUTSTREAM = "http://qiuming.sinaapp.com/";
    static final String PATH_STRING = "http://qiuming.sinaapp.com/";
    static final String PATH_BITMAP = "http://tp3.sinaimg.cn/1859125850/180/5628821209/1";
    static final String PATH_WITHPARAMS = "http://qiuming.sinaapp.com/";
    static final String PATH_POSTCONTENT = "http://qiuming.sinaapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int a=10;
        log.error("会务奇才在在e{}",a);
        log.info("会务奇才在在i");
        log.warn("会务奇才在在w");
        log.debug("会务奇才在d在");
        log.error("会务奇才在在");
        
        mPopupButton=(Button) findViewById(R.id.main_popup_button);
        mRotateButton=(Button) findViewById(R.id.main_rotate_button);
        mPopupButton.setOnClickListener(this);
        mRotateButton.setOnClickListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.main_popup_button){
            CommHttpRequest request;
            request = CommHttpRequest.requestWithURL(this, PATH_INPUTSTREAM);
            // 必须先设置回调函数，否则调用异步请求无效
            request.setCallBack(new CommHttpRequestLinstener()
            {
                
                @Override
                public void loadFinished(InputStream ins, boolean fromcache)
                {
                    // TODO Auto-generated method stub
                    log.info("成功");
                }
                
                @Override
                public void loadFailed(HttpResponse response, InputStream cacheInputStream)
                {
                    // TODO Auto-generated method stub
                    log.error("失败");
                }
            });
            request.startAsynchronous();
            
        }else if(v.getId()==R.id.main_rotate_button){
            mCtransMenu.circleItems();
        }
        
    }
    
}
