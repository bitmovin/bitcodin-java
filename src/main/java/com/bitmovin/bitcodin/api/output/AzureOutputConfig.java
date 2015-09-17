package com.bitmovin.bitcodin.api.output;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * dmoser [david.moser@bitmovin.net]
 * on 15.09.15
 */
public class AzureOutputConfig {
    @Expose
    public OutputType type = OutputType.AZURE;
    @Expose
    public String name;
    @Expose
    public String accountName;
    @Expose
    public String accountKey;
    @Expose
    public String container;
    @Expose
    public String prefix;
    @Expose
    public boolean createSubDirectory;
}
