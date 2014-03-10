package com.geekchic.base.http;

import java.util.List;

import android.content.Context;

import com.geekchic.base.db.BaseDaoImpl;
import com.geekchic.base.http.CommHttpURL.URLField;
import com.geekchic.common.LogUtil;

public class CommHttpInfoDBImpl extends BaseDaoImpl<CommHttpURL> implements CommHttpDBOps
{
    private static CommHttpInfoDBImpl INSTANCE;
    public static CommHttpInfoDBImpl getINSTANCE(Context context){
        if(INSTANCE==null){
            INSTANCE=new CommHttpInfoDBImpl(context);
        }
        return INSTANCE;
    }
    private CommHttpInfoDBImpl(Context context)
    {
        super(new CommHttpDBHelper(context),CommHttpURL.class);
        // TODO Auto-generated constructor stub
    }

    @Override
    public CommHttpURL getUrl(String url)
    {
        CommHttpURL commHttpUrl=null;
        String[] columns = {URLField.URL,URLField.LASTMODIFIED,URLField.ETAG,URLField.LOCALDATA};
        List<CommHttpURL> resCommHttpInfos=find(columns, URLField.URL+"=?", new String[]{url}, null, null, null,null);
        if(resCommHttpInfos!=null && resCommHttpInfos.size()>0){
             commHttpUrl=resCommHttpInfos.get(0);
             return commHttpUrl;
        }else {
            return null;
        }
    }
    @Override
    public boolean insertURL(CommHttpURL url)
    {
       long err=insert(url, true);
       if (err == -1) {
               LogUtil.i("Error from insertURL:" + err);
               return false;
       } else {
               LogUtil.i("insertURL successful! ");
               return true;
       }
    }
    @Override
    public void updateURL(CommHttpURL url)
    {
        update(url);
    }
    @Override
    public boolean existURL(String url)
    {
        // TODO Auto-generated method stub
        String[] columns=new String[]{CommHttpURL.URLField.URL};
        String selection=CommHttpURL.URLField.URL+"=?";
        String[] selectionArgs={url};
        List<CommHttpURL> res=find(columns, selection, selectionArgs, null, null, null, null);
        if(res!=null){
            return true;
        }
          
        return false;
        
    }
}
