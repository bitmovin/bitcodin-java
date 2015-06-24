package com.bitmovin.bitcodin.api.statistics;

import com.google.gson.annotations.Expose;

public class MonthlyStatistic {
    @Expose
    public int jobCountFinished;
    @Expose
    public double realtimeFactor;
    @Expose
    public String totalBytesWritten;
    @Expose
    public String avgBytesWritten;
    @Expose
    public String totalTimeEnqueued;
    @Expose
    public String avgTimeEnqueued;
    @Expose
    public String totalTimeEncoded;
    @Expose
    public String avgTimeEncoded;
    @Expose
    public String totalTime;
    @Expose
    public String avgJobTime;
}
