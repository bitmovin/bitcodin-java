package com.bitmovin.bitcodin.api.output;

import java.util.List;

import com.google.gson.annotations.Expose;

public class OutputList {
    @Expose
    public long totalCount;
    @Expose
    public List<Output> outputs;
}
