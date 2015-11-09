package com.bitmovin.bitcodin.api.manifest;

import com.google.gson.annotations.Expose;

public class VttSubtitle {
    @Expose
    public String langShort;

    @Expose
    public String langLong;

    @Expose
    public String url;
}
