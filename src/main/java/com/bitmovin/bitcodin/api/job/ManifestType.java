package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.SerializedName;

public enum ManifestType {
    @SerializedName("mpd")
    MPEG_DASH_MPD,
    @SerializedName("m3u8")
    HLS_M3U8
}
