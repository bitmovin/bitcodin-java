package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public class AudioMetaData {
    @Expose
    public int defaultStreamId;
    @Expose
    public String language;
    @Expose
    public String label;
}
