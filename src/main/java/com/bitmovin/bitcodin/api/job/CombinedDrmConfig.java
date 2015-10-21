package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public class CombinedDrmConfig extends AbstractDrmConfig
{
    public CombinedDrmConfig()
    {
        this.method = DrmMethod.MPEG_CENC;
        this.system = DrmSystem.WIDEVINE_PLAYREADY;
    }

    @Expose
    public String kid;

    @Expose
    public String key;

    @Expose
    public String laUrl;

    @Expose
    public String pssh;
}
