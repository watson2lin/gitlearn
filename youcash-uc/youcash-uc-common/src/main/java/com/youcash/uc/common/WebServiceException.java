package com.youcash.uc.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * 封装WSRS的WebApplicationException，加入了错误code等功能。
 * 
 * @author liuzheng
 * @version 1.1
 */
public class WebServiceException extends WebApplicationException {

	private static final long serialVersionUID = 4595762021422358620L;
	
	private String message;

	public WebServiceException(String errCode, String message) {
		super(Response.status(Status.INTERNAL_SERVER_ERROR).entity(new WebServiceMessage(errCode, message)).type(MediaType.APPLICATION_XML).build());
		this.message = message;
	}

	public WebServiceException(Response response) {
		super(response);
	}

	@Override
	public String getLocalizedMessage() {
		return message;
	}

}
