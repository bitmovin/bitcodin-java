package com.bitmovin.bitcodin.api.job;

import java.util.Vector;

import com.google.gson.annotations.Expose;


public class JobConfig {
    @Expose
    public int inputId;
    @Expose
    public int outputId;
    @Expose
    public int encodingProfileId;
    @Expose
    public Vector<ManifestType> manifestTypes = new Vector<>();
    @Expose
    public boolean extractClosedCaptions;
    @Expose
    public VideoMetaData[] videoMetaData;
    @Expose
    public AudioMetaData[] audioMetaData;
    @Expose
    public AbstractDrmConfig drmConfig;
    @Expose
    public Speed speed;
    @Expose
    public Location location;
    @Expose
    public HlsEncryptionConfig hlsEncryptionConfig;
}
