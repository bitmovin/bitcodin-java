package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.SerializedName;

/**
 * Created by
 * David Moser [david.moser@bitmovin.net]
 * on 24.08.15
 */
public enum HlsMethod {
    @SerializedName("SAMPLE-AES")
    SAMPLE_AES,
    @SerializedName("FAIRPLAY")
    FAIRPLAY,
    @SerializedName("AES-128")
    AES_128
}
