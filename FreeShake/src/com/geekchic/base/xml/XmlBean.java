package com.geekchic.base.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * XMl解析节点实体类
 * 
 * @ClassName: XmlBean
 * @Descritpion:xml节点实体类
 * @author jp
 * @date Apr 12, 2014
 */
public class XmlBean {
	/**
	 * 节点名
	 */
	private String key;
	/**
	 * 属性键值对
	 */
	private Map<String, String> attrMap;
	/**
	 * 子节点
	 */
	private ArrayList<XmlBean> childNode;

	/**
	 * XmlBean构造函数
	 */
	public XmlBean() {
		// TODO Auto-generated constructor stub
		attrMap = new HashMap<String, String>();
		childNode = new ArrayList<XmlBean>();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public void setAttrMap(Map<String, String> attrMap) {
		this.attrMap = attrMap;
	}

	public ArrayList<XmlBean> getChildNode() {
		return childNode;
	}

	public void setChildNode(XmlBean xmlBean) {
		this.childNode.add(xmlBean);
	}

}
