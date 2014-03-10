package com.geekchic.base.http;

import java.util.HashMap;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;

@Table(name="commHttp")
public class CommHttpURL
{
   public static class URLField{
       public static final String URL="url";
       public static final String LASTMODIFIED="lastmodified";
       public static final String ETAG="etag";
       public static final String LOCALDATA="localdata";
   }
   @Id
   @Column(name = "id")
   private int id;
   @Column(name=CommHttpURL.URLField.URL)
   private String url;
   @Column(name=CommHttpURL.URLField.LASTMODIFIED)
   private String lastModified;
   @Column(name=CommHttpURL.URLField.ETAG)
   private String etag;
   @Column(name=CommHttpURL.URLField.LOCALDATA)
   private String localData;
   private HashMap<String, String> postData;
   public CommHttpURL(){
       
   }
   public CommHttpURL(String url,String lastModified,String etag,String localData){
       this.url=url;
       this.lastModified=lastModified;
       this.etag=etag;
       this.localData=localData;
   }
public String getUrl()
{
    return url;
}
public void setUrl(String url)
{
    this.url = url;
}
public String getLastModified()
{
    return lastModified;
}
public void setLastModified(String lastModified)
{
    this.lastModified = lastModified;
}
public String getEtag()
{
    return etag;
}
public void setEtag(String etag)
{
    this.etag = etag;
}
public String getLocalData()
{
    return localData;
}
public void setLocalData(String localData)
{
    this.localData = localData;
}
public HashMap<String, String> getPostData()
{
    if (postData == null) {
        postData = new HashMap<String, String>();
}
    return postData;
}
public void setPostData(HashMap<String, String> postData)
{
    this.postData = postData;
}
    @Override
        public String toString()
        {
            // TODO Auto-generated method stub
        if(url!=null){
            return url;
        }
            return super.toString();
        }
}
