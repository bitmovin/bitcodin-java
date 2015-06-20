package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.SerializedName;

public enum Profile {
    @SerializedName("Baseline")
    BASELINE,
    @SerializedName("Main")
    MAIN,
    @SerializedName("High")
    HIGH
}
