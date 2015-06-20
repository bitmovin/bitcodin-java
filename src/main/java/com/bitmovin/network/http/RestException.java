package com.bitmovin.network.http;

public class RestException extends Exception {

    private static final long serialVersionUID = 1L;
    public int status;
    public String reason;
    public String body;
    
    public RestException(int status, String reason, String body) {
        super(reason);
        
        this.status = status;
        this.reason = reason;
        this.body = body;
    }

}
