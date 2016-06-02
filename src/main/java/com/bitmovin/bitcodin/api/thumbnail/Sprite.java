package com.bitmovin.bitcodin.api.thumbnail;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 25.11.15
 */
public class Sprite
{
    @Expose
    public String id;
    @Expose
    public String state;
    @Expose
    public String vttUrl;
    @Expose
    public String spriteUrl;
}
