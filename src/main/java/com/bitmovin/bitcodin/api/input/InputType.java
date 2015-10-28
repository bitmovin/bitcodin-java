package com.bitmovin.bitcodin.api.input;

import com.google.gson.annotations.SerializedName;

public enum InputType {
    @SerializedName("abs")
    ABS,
    @SerializedName("url")
    URL,
    @SerializedName("s3")
    S3
}
