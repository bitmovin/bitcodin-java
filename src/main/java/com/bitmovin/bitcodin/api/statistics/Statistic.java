package com.bitmovin.bitcodin.api.statistics;

import com.google.gson.annotations.Expose;

public class Statistic {
    @Expose
    public int jobCountFinished;
    @Expose
    public double realtimeFactor;
    @Expose
    public String totalBytesWritten;
    @Expose
    public String avgBytesWritten;
}
