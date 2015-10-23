package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.Expose;

public class CroppingConfig {
    @Expose
    public int top;
    @Expose
    public int bottom;
    @Expose
    public int left;
    @Expose
    public int right;
}
