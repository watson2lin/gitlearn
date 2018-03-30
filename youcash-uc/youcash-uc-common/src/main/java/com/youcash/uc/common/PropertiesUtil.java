/**
 * @(#)PropertiesUtil.java
 * 
 * Copyright youcash.All rights reserved.
 * COD System.
 * 
 * @Version:V1.6.0
 * @JDK:jdk 1.6.0
 * @Module:cod-application-1.6.1
 */
/*- 			History
 ***********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2013-12-19          liqunhuan    Created
 *  2     2014-01-07       liqunhuan    Updated
 ***********************************************
 */
package com.youcash.uc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @Title: 配置文件工具类
 * @Desription:公共、灵活的配置文件工具类
 * @Company:youcash
 * @ClassName:PropertiesUtil
 * @Author:liqunhuan
 * @CreateDate:2013-12-19
 * @UpdateUser:liqunhuan
 * @Version:1.0
 * 
 */
public class PropertiesUtil {

	private final Logger log = Logger.getLogger(PropertiesUtil.class);

	/** 配置文件名称 */
	private String filePath;

	/** 配置属性集 */
	private Properties prop = null;

	/**
	 * 默认构造方法，初始化默认JNDI配置文件
	 */
	public PropertiesUtil() {
		filePath = "/default-jndi.properties";
		init(this.getClass().getResourceAsStream(filePath));
	}

	/**
	 * 指定配置文件构造方法，注意！跨模块调用要用流，请看构造方法PropertiesUtil(InputStream in)
	 * 
	 * 
	 * @param propertiesFile
	 *            文件路径
	 */
	public PropertiesUtil(String propertiesFile) {
		this.filePath = propertiesFile;
		init(this.getClass().getResourceAsStream(filePath));
	}

	/**
	 * 指定配置文件构造方法
	 * 
	 * @param in
	 */
	public PropertiesUtil(InputStream in) {
		init(in);
	}

	/**
	 * 指定配置文件构造方法
	 * 
	 * @param file
	 *            File对象
	 * @throws FileNotFoundException
	 */
	public PropertiesUtil(File file) throws FileNotFoundException {
		init(new FileInputStream(file));
	}

	/**
	 * @ClassName: PropertiesUtil
	 * @Description:初始方法
	 * @author: liqunhuan
	 * @date: 2013-12-19
	 * @param in
	 */
	private void init(InputStream in) {
		// 获取本地配置文件流
		prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public String getValue(String key) {
		return (String) prop.get(key);
	}

	/**
	 * 向配置文件写值，被写的文件只能存在与工程以外的固定路径
	 * 
	 * @ClassName: PropertiesUtil
	 * @Description:
	 * @author: Yangjinbo
	 * @date: 2013-12-25
	 * @UpdateUser:liqunhuan
	 * @UpdateDate: 2014-01-07 优化写入异常情况处理
	 * @param key
	 * @param value
	 * @param file
	 */
	public void setValue(String key, String value, File file) {
		String str = prop.getProperty(key);
		if (StringUtils.isBlank(str)) {
			prop.put(key, value);
		} else {
			prop.setProperty(key, value);
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			prop.store(out, "");
			out.flush();
		} catch (FileNotFoundException e) {
			log.error("写入配置文件异常", e);
		} catch (IOException e) {
			log.error("写入配置文件异常", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error("写入配置文件异常", e);
				}
			}
		}
	}
}
