package com.bitmovin.bitcodin.api;

import java.util.List;

import com.google.gson.annotations.Expose;

public class InputList {
    
    @Expose
    public long totalCount;
    @Expose
    public List<Input> inputs;
}
