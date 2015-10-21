package com.bitmovin.bitcodin.api.input;

import com.google.gson.annotations.Expose;

public class AzureInputConfig {
    @Expose
    public InputType type = InputType.ABS;
    @Expose
    public String accountKey;
    @Expose
    public String accountName;
    @Expose
    public String container;
    @Expose
    public String url;
}
