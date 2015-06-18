package com.bitmovin.bitcodin.api.job;

import com.google.gson.annotations.Expose;

public class Job {
    @Expose
    public int jobId;
    @Expose
    public String status;
}
