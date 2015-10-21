package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */

public class WidevineDrmConfig extends AbstractDrmConfig {

    public WidevineDrmConfig()
    {
        this.method = DrmMethod.MPEG_CENC;
        this.system = DrmSystem.WIDEVINE;
    }

    @Expose
    public String provider;
    @Expose
    public String signingKey;
    @Expose
    public String signingIV;
    @Expose
    public String requestUrl;
    @Expose
    public String contentId;
}
