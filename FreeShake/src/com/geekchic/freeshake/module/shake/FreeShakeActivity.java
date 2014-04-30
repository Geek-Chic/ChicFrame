package com.geekchic.freeshake.module.shake;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geekchic.base.update.UpdateFrequent;
import com.geekchic.base.update.UpdateManager;
import com.geekchic.base.update.UpdateOptions;
import com.geekchic.base.update.UpdateOptions.UpdateFormat;
import com.geekchic.common.utils.ToastUtil;
import com.geekchic.wuyou.R;
import com.widget.slidingmenu.SlidingMenu;

public class FreeShakeActivity extends Activity
{
    protected SlidingMenu mSlidingMenu;
    private static final String CITY_CODE_URL="https://raw.githubusercontent.com/snowdream/android-autoupdater/master/docs/test/updateinfo.xml";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    	String urlString="https://raw.githubusercontent.com/snowdream/android-autoupdater/master/docs/test/updateinfo.xml";
    	  UpdateManager manager = new UpdateManager(this);

          UpdateOptions options = new UpdateOptions.Builder(this)
                  .checkUpdate(urlString)
                  .checkUpdateFormat(UpdateFormat.XML)
                  .udpateFrequent(new UpdateFrequent(UpdateFrequent.EACH_TIME))
                  .checkPackageName(true)
                  .build();
          manager.check(this, options);
          
    
//        DisplayMetrics dm = new DisplayMetrics();
//        com.geekchic.common.log.Logger.d("佳士科技","sf ");
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
//        // customize the SlidingMenu
//        setBehindContentView(R.layout.menu_frame_right);
//        mSlidingMenu = getSlidingMenu();
//        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置是左滑还是右滑，还是左右都可以滑，我这里左右都可以滑
//        mSlidingMenu.setShadowWidth(mScreenWidth / 40);// 设置阴影宽度
//        mSlidingMenu.setBehindOffset(mScreenWidth / 8);// 设置菜单宽度
//        mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
//        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        mSlidingMenu.setShadowDrawable(R.drawable.slidingmenu_left_shadow);// 设置左菜单阴影图片
//        mSlidingMenu.setSecondaryShadowDrawable(R.drawable.slidingmenu_right_shadow);// 设置右菜单阴影图片
//        mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
//        mSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
//        mSlidingMenu.setSecondaryMenu(R.layout.menu_frame);
//        test();
//        throw new NullPointerException();
//          getStringVolley();
    }
	private void getStringVolley() {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		StringRequest sRequest=new StringRequest(Request.Method.GET,CITY_CODE_URL, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println(response);				
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				System.out.println("sorry,Error");
			}
		});
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
    public void test(){
        try
        {
            int dres=5/0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ToastUtil.show(this, "哈合", Toast.LENGTH_LONG);
        }
    }
}
