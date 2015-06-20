package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.Expose;

public class Output {
    @Expose
    public int outputId;
    @Expose
    public String name;
    @Expose
    public String type;
}
