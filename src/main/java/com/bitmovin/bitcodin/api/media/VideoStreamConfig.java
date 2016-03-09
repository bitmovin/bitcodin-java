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
    public Profile profile;
    @Expose
    public Preset preset;
    @Expose
    public VideoCodec codec;
    @Expose
    public int rate;
}
