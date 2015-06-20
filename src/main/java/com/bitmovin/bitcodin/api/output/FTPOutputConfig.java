package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.Expose;

public class FTPOutputConfig {
    @Expose
    public OutputType type = OutputType.FTP;
    @Expose
    public String name;
    @Expose
    public String host;
    @Expose
    public String username;
    @Expose
    public String password;
    @Expose
    public boolean passive;
}
