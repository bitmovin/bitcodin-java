package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.SerializedName;

public enum OutputType {
    @SerializedName("s3")
    S3,
    @SerializedName("gcs")
    GCS,
    @SerializedName("ftp")
    FTP;
}
