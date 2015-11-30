package com.bitmovin.bitcodin.api.manifest;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 30.11.15
 */
public class VttHlsConfig
{
    @Expose
    public int jobId;

    @Expose
    public VttSubtitle[] subtitles;

    @Expose
    public String outputFileName;
}
