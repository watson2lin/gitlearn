package com.youcash.uc.common;

import java.io.Serializable;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 封装WSRS的WebApplicationException，加入了错误code等功能。
 *
 * @author hansheng.lin
 */
public class UCWebApplicationException extends WebApplicationException {

    private static final long serialVersionUID = 4595762021422358620L;

    public UCWebApplicationException(String errCode, String message) {
        super(Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new UCWebApplicationFault(errCode, message))
                .type(MediaType.APPLICATION_XML).build());
    }

    public UCWebApplicationException(Response response) {
        super(response);
    }

    @XmlRootElement(name = "UCWebApplicationFault")
    public static class UCWebApplicationFault implements Serializable {


        private static final long serialVersionUID = 3140824142386163359L;

        private String code;

        private String message;

        public UCWebApplicationFault(String code, String message) {
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

        public UCWebApplicationFault() {
        }

    }
}
