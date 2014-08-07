/**
 * @Title: UdpateXmlParser.java
 * @Package com.geekchic.base.update
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 13, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.update;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.geekchic.base.xml.SaxServiceHandle;
import com.geekchic.base.xml.XmlBean;

/**
 * @ClassName: UdpateXmlParser
 * @Descritpion: xml配置解析器
 * @author evil
 * @date Apr 13, 2014
 */
public class UpdateXmlParser extends SaxServiceHandle implements AbstractParser
{
    public UpdateInfo updateInfo;
    
    public UpdateXmlParser()
    {
        updateInfo = new UpdateInfo();
    }
    
    public UpdateInfo p(InputStream inputStream)
    {
        // TODO Auto-generated method stub
        XmlBean rootBean = getRootBean(inputStream);
        if (null != rootBean)
        {
            processParse(rootBean);
        }
        return updateInfo;
    }
    
    @Override
    public UpdateInfo parse(String content)
    {
        // TODO Auto-generated method stub
        XmlBean rootBean = getRootBeanFromContent(content);
        if (null != rootBean)
        {
            processParse(rootBean);
        }
        return updateInfo;
    }
    
    /**
     * 解析生成实体类
     * @param xmlBean
     */
    public void processParse(XmlBean xmlBean)
    {
        String key;
        for (XmlBean child : xmlBean.getChildNode())
        {
            key = child.getKey();
            if (key.equalsIgnoreCase(TAG_APP_NAME))
            {
                updateInfo.setAppName(child.getAttrMap().get(key));
            }
            else if (key.equalsIgnoreCase(TAG_APP_DESCRIPTION))
            {
                updateInfo.setAppDescription(child.getAttrMap().get(key));
            }
            else if (key.equalsIgnoreCase(TAG_PACKAGE_NAME))
            {
                updateInfo.setPackageName(child.getAttrMap().get(key));
            }
            else if (key.equalsIgnoreCase(TAG_VERSION_CODE))
            {
                updateInfo.setVersionCode(child.getAttrMap().get(key));
            }
            else if (key.equalsIgnoreCase(TAG_VERSION_NAME))
            {
                updateInfo.setVersionName(child.getAttrMap().get(key));
            }
            else if (key.equalsIgnoreCase(TAG_FORCE_UPDATE))
            {
                boolean forceUpdate = Boolean.parseBoolean(child.getAttrMap()
                        .get(key));
                updateInfo.setForceUpdate(forceUpdate);
            }
            else if (key.equalsIgnoreCase(TAG_AUTO_UPDATE))
            {
                boolean autoUpdate = Boolean.parseBoolean(child.getAttrMap()
                        .get(key));
                updateInfo.setAutoUpdate(autoUpdate);
            }
            else if (key.equalsIgnoreCase(TAG_APK_URL))
            {
                updateInfo.setApkUrl(child.getAttrMap().get(key));
            }
            else if (key.equalsIgnoreCase(TAG_UPDATE_TIPS))
            {
                updateInfo.setUpdateTips(parseUpdateTips(child));
            }
        }
    }
    
    /**
     * 解析UpdateTips
     * @param updateTips updateTips节点
     * @return
     */
    private Map<String, String> parseUpdateTips(XmlBean updateTips)
    {
        HashMap<String, String> tips = new HashMap<String, String>();
        for (XmlBean child : updateTips.getChildNode())
        {
            String key = child.getKey();
            tips.put(key, child.getAttrMap().get(key));
        }
        return tips;
        
    }
}
