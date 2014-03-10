package com.geekchic.base.http;

import com.geekchic.base.db.BaseDao;

public interface CommHttpDBOps extends BaseDao<CommHttpURL>{
    public CommHttpURL getUrl(String url);
    public boolean insertURL(CommHttpURL url);
    public void updateURL(CommHttpURL url);
    public boolean existURL(String url);
    
}
