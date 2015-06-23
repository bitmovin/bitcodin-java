package com.bitmovin.bitcodin.api.exception;

public class BitcodinApiException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public BitcodinApiException (String message, Throwable cause) {
        super(message, cause);
    }
    public BitcodinApiException (String message) {
        super(message);
    }

}
