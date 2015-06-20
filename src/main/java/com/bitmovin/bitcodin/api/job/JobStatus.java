package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.SerializedName;

public enum JobStatus {
    @SerializedName("Created")
    CREATED,
    @SerializedName("Enqueued")
    ENQUEUED,
    @SerializedName("In Progress")
    IN_PROGRESS,
    @SerializedName("Finished")
    FINISHED,
    @SerializedName("Error")
    ERROR
}
