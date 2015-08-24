package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public abstract class AbstractDrmConfig {
    @Expose
    public DrmSystem system;
    @Expose
    public DrmMethod method;
}
