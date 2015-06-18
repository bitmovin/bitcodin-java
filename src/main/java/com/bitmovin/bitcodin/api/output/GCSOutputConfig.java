package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.Expose;

public class GCSOutputConfig {
    @Expose
    public String type = "gcs";
    @Expose
    public String name;
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
