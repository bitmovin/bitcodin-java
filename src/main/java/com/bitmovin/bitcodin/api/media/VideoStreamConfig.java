package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.Expose;

public class VideoStreamConfig {
    @Expose
    public int defaultStreamId;
    @Expose
    public long bitrate;
    @Expose
    public int width;
    @Expose
    public int height;
    @Expose
    public String profile;
    @Expose
    public String preset;
}
