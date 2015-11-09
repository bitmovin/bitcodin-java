package com.bitmovin.bitcodin.api.manifest;

import com.google.gson.annotations.Expose;

public class VttMpdConfig {
    @Expose
    public int jobId;

    @Expose
    public VttSubtitle[] subtitles;
}
