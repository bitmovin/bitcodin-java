package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.Expose;

public class AudioStreamConfig {
    @Expose
    public int defaultStreamId;
    @Expose
    public long bitrate;
    @Expose
    public float samplerate;
}
