package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.SerializedName;

public enum OutputType {
    @SerializedName("s3")
    S3,
    @SerializedName("ftp")
    FTP,
    @SerializedName("azure")
    AZURE,
    @SerializedName("gcs")
    GCS
}
