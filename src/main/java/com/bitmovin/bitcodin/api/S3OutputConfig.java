package com.bitmovin.bitcodin.api;

import com.google.gson.annotations.Expose;

public class S3OutputConfig {
    @Expose
    public String type = "s3";
    @Expose
    public String name;
    @Expose
    public String host;
    @Expose
    public String accessKey;
    @Expose
    public String secretKey;
    @Expose
    public String bucket;
    @Expose
    public String prefix;
    @Expose
    public boolean makePublic;
}
