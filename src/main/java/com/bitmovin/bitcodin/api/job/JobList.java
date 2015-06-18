package com.bitmovin.bitcodin.api.job;

import java.util.List;

import com.google.gson.annotations.Expose;

public class JobList {
    @Expose
    public long totalCount;
    @Expose
    public List<Job> jobs;
}
