package com.bitmovin.bitcodin.api.media;

import java.util.Vector;

import com.google.gson.annotations.Expose;

public class EncodingProfileConfig {
    @Expose
    public String name;
    @Expose
    public Vector<VideoStreamConfig> videoStreamConfigs = new Vector<VideoStreamConfig>();
    @Expose
    public Vector<AudioStreamConfig> audioStreamConfigs = new Vector<AudioStreamConfig>();
    @Expose
    public int rotation = 0;
    @Expose
    public WatermarkConfig watermarkConfig = null;
    @Expose
    public CroppingConfig croppingConfig = null;
    @Expose
    public int segmentLength = 4;
}
