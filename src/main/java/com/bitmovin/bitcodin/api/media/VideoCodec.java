package com.bitmovin.bitcodin.api.media;

import com.google.gson.annotations.SerializedName;

public enum VideoCodec {
    @SerializedName("h264")
    H264,
    @SerializedName("hevc")
    HEVC
}
