package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.Expose;

public class WatermarkConfig {
    @Expose
    public String image;
    @Expose
    public Integer top = null;
    @Expose
    public Integer bottom = null;
    @Expose
    public Integer left = null;
    @Expose
    public Integer right = null;
}
