package com.bitmovin.bitcodin.api.thumbnail;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 25.11.15
 */
public class SpriteConfig
{
    @Expose
    public int jobId;

    @Expose
    public int height;

    @Expose
    public int width;

    @Expose
    public int distance;

    @Expose
    public boolean async;
}
