package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.SerializedName;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public enum Location {
    @SerializedName("default")
    DEFAULT,
    @SerializedName("eu-west-1")
    EU_WEST
}
