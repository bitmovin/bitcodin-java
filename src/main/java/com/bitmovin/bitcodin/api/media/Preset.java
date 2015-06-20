package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.SerializedName;

public enum Preset {
    @SerializedName("Standard")
    STANDARD,
    @SerializedName("Professional")
    PROFESSIONAL,
    @SerializedName("Premium")
    PREMIUM
}
