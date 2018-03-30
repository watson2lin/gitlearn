/*
 * Copyright (c) 2009, 2012, youcash. All rights reserved.
 * Use is subject to license terms.
 */
package com.youcash.uc.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 * @author Liuzheng
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WebServiceMessage")
public class WebServiceMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String SUCCESS="200";
	
	public static String FAIL="500";

	private String code;

	private String message;

	public WebServiceMessage() {}

	public WebServiceMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
