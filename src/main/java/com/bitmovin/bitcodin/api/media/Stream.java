package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.Expose;

public class Stream {

    /* General */
    @Expose
    public int streamId;
    @Expose
    public long duration;
    @Expose
    public double rate;
    @Expose
    public String codec;
    @Expose
    public String type;
    @Expose
    public long bitrate;
    
    /* Video */
    @Expose
    public int width;
    @Expose
    public int height;
    @Expose
    public String pixelFormat;
    @Expose
    public int sampleAspectRatioNum;
    @Expose
    public int sampleAspectRatioDen;
    @Expose
    public int displayAspectRatioNum;
    @Expose
    public int displayAspectRatioDen;
    

    /* Audio */
    @Expose
    public int sampleFormat;
    @Expose
    public String channelFormat;

}
