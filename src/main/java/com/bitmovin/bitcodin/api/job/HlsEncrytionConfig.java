package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public class HlsEncrytionConfig {
    @Expose
    public HlsMethod method;
    @Expose
    public String key;
    @Expose
    public String iv;
    @Expose
    public String uri;
}
