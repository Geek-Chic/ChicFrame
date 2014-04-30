package com.geekchic.base.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

/**
 * @ClassName: SaxServiceHandle
 * @Descritpion:公共SAX解析XML类
 * @author jp
 * @date Apr 12, 2014
 */
public abstract class SaxServiceHandle extends DefaultHandler {
	/**
	 * TAG
	 */
	private final static String TAG = SaxServiceHandle.class.getName();
	/**
	 * 通用实体类
	 */
	private XmlBean mCurrrentBean;
	/**
	 * TAG栈
	 */
	private Stack<String> mTagNameStack;
	private Stack<XmlBean> mTagBeanStack;
	/**
	 * 根节点
	 */
	private XmlBean rootTagBean;

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		mTagNameStack = new Stack<String>();
		mTagBeanStack = new Stack<XmlBean>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		mTagNameStack.push(qName);
		mCurrrentBean = new XmlBean();
		mCurrrentBean.setKey(qName);
		int index = attributes.getLength();
		if (index > 0) {
			Map<String, String> attrsMap = new HashMap<String, String>();
			for (int i = 0; i < index; i++) {
				attrsMap.put(attributes.getQName(i), attributes.getValue(i));
			}
			mCurrrentBean.setAttrMap(attrsMap);
		}
		mTagBeanStack.push(mCurrrentBean);
		if (rootTagBean == null) {
			rootTagBean = mCurrrentBean;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String tContent = new String(ch, start, length);
		tContent = tContent.trim();
		if (tContent.length() > 0) {
			XmlBean tThemeBean = mTagBeanStack.peek();
			tThemeBean.getAttrMap().put(mTagNameStack.peek(), tContent);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		mTagNameStack.pop();
		mCurrrentBean = mTagBeanStack.pop();
		if (!mCurrrentBean.getKey().equals(rootTagBean.getKey())) {
			mTagBeanStack.peek().setChildNode(mCurrrentBean);
		}
	}

	/**
	 * 根据xml文件解析
	 * 
	 * @param pXmlPath
	 *            xml文件路径
	 * @return 根节点
	 */
	public XmlBean getRootBeanFromPath(String pXmlPath) {
		try {
			getRootBean(getXmlFileStream(pXmlPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootTagBean;
	}

	public XmlBean getRootBeanFromContent(String content) {
		getRootBean(getXmlFromString(content));
		return rootTagBean;
	}

	/**
	 * 根据inputstream解析xml
	 * 
	 * @param inputStream
	 *            xml流
	 * @return 根节点
	 */
	public XmlBean getRootBean(InputStream inputStream) {

		// 根据具体程序要求进行分析
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			// 给分析器传入文件路径和分析规则
			parser.parse(inputStream, this);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 分析结束，获得信息
		return rootTagBean;

	}

	/**
	 * 从内容解析xml
	 * 
	 * @param content
	 * @return
	 */
	private InputStream getXmlFromString(String content) {
		InputStream inputStream = new ByteArrayInputStream(content.getBytes());
		return inputStream;
	}

	/**
	 * 从文件解析xml
	 * 
	 * @param pXmlPath
	 *            文件路径
	 * @return
	 * @throws FileNotFoundException
	 */
	private InputStream getXmlFileStream(String pXmlPath)
			throws FileNotFoundException {
		File xmlFile = new File(pXmlPath);
		InputStream inputStream = new FileInputStream(xmlFile);
		return inputStream;
	}

	public XmlBean getRootTag() {
		return rootTagBean;
	}
}
