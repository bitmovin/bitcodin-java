package com.bitmovin.bitcodin.api.input;

import com.google.gson.annotations.Expose;

public class S3InputConfig {
    @Expose
    public InputType type = InputType.S3;
    @Expose
    public String accessKey;
    @Expose
    public String secretKey;
    @Expose
    public String bucket;
    @Expose
    public String region;
    @Expose
    public String objectKey;
}
