package com.bitmovin.bitcodin.api.statistics;

import com.google.gson.annotations.Expose;

public class Statistic {
    @Expose
    public double averageDurationInSeconds;
    @Expose
    public int jobsFinished;
    @Expose
    public int durationAllInSeconds;
    @Expose
    public int allJobs;
    @Expose
    public int jobsUnfinished;
    @Expose
    public double workloadPercentage;
    @Expose
    public int timeWindowInSeconds;
    @Expose
    public int enqueueDuration;
    @Expose
    public double averageEnqueueDuration;
}
