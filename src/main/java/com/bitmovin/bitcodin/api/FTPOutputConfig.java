package com.bitmovin.bitcodin.api;

import com.google.gson.annotations.Expose;

public class FTPOutputConfig {
    @Expose
    public String type = "ftp";
    @Expose
    public String name;
    @Expose
    public String host;
    @Expose
    public String basicAuthUser;
    @Expose
    public String basicAuthPassword;
    @Expose
    public boolean passive;
}
