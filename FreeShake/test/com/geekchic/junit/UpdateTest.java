package com.geekchic.junit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.geekchic.base.update.UpdateFrequent;
import com.geekchic.base.update.UpdateManager;
import com.geekchic.base.update.UpdateOptions;
import com.geekchic.base.update.UpdateOptions.UpdateFormat;
import com.geekchic.base.update.UpdateXmlParser;
import com.geekchic.freeshake.module.shake.FreeShakeActivity;

public class UpdateTest extends ActivityInstrumentationTestCase2<FreeShakeActivity> {
	private Context context;
	private Button button;
	public UpdateTest() {
		super(FreeShakeActivity.class);
	}
	private UpdateXmlParser mUpdateXmlParser;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		mUpdateXmlParser = new UpdateXmlParser();
		this.context=getInstrumentation().getContext();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
//		AssetManager assetManager=getContext().getResources().getAssets();
//		InputStream inputStream=assetManager.open("updateinfo.xml");
//		
//		UpdateInfo info=mUpdateXmlParser.p(inputStream);
//		System.out.println(info.toString());
	}
    public void testUpdateManager(){
    	String urlString="https://raw.githubusercontent.com/snowdream/android-autoupdater/master/docs/test/updateinfo.xml";
    	  UpdateManager manager = new UpdateManager(context);

          UpdateOptions options = new UpdateOptions.Builder(context)
                  .checkUpdate(urlString)
                  .checkUpdateFormat(UpdateFormat.XML)
                  .udpateFrequent(new UpdateFrequent(UpdateFrequent.EACH_TIME))
                  .checkPackageName(true)
                  .build();
          manager.check(context, options);
          
    }
	public String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line + "/n");

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return sb.toString();

	}
}
