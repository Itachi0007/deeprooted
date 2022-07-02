package com.project.employee;

import org.springframework.http.HttpStatus;

public class ResponseBody {
    private String message;
    private HttpStatus statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseBody() {
        super();
        // TODO Auto-generated constructor stub
    }


}
