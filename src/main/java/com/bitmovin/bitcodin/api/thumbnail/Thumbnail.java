package com.bitmovin.bitcodin.api.thumbnail;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 13.11.15
 */
public class Thumbnail {
    @Expose
    public String id;
    @Expose
    public String thumbnailUrl;
    @Expose
    public String state;
}
