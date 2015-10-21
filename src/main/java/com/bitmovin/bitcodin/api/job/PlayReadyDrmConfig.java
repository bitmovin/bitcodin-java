package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

public class PlayReadyDrmConfig extends AbstractDrmConfig {

    public PlayReadyDrmConfig()
    {
        this.system = DrmSystem.PLAYREADY;
        this.method = DrmMethod.MPEG_CENC;
    }

    @Expose
    public String keySeed;
    @Expose
    public String laUrl;
    @Expose
    public String kid;

}