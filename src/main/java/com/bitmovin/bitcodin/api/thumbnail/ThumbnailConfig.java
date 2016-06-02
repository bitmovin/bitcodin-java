package com.bitmovin.bitcodin.api.thumbnail;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 13.11.15
 */
public class ThumbnailConfig {
    @Expose
    public int jobId;

    @Expose
    public int height;

    @Expose
    public int position;

    @Expose
    public String fileName;

    @Expose
    public boolean async;
}
