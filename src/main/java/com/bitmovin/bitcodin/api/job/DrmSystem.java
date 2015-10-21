package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.SerializedName;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public enum DrmSystem {
    @SerializedName("widevine")
    WIDEVINE,
    @SerializedName("playready")
    PLAYREADY,
    @SerializedName("widevine_playready")
    WIDEVINE_PLAYREADY
}
