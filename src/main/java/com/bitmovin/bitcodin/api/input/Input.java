package com.bitmovin.bitcodin.api.input;

import java.util.List;

import com.bitmovin.bitcodin.api.media.Stream;
import com.google.gson.annotations.Expose;

public class Input {
    @Expose
    public int inputId;
    @Expose
    public String filename;
    @Expose
    public String thumbnailUrl;
    @Expose
    public InputType inputType;
    @Expose
    public String url;
    @Expose
    public List<Stream> mediaConfigurations;
}
