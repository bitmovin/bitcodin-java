package com.bitmovin.bitcodin.api.media;

import java.util.List;

import com.google.gson.annotations.Expose;

public class EncodingProfileList {
    @Expose
    public long totalCount;
    @Expose
    public List<EncodingProfile> profiles;
}
