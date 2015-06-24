package com.bitmovin.bitcodin.api.job;

import java.util.List;

import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.media.EncodingProfile;
import com.google.gson.annotations.Expose;

public class JobDetails {
    @Expose
    public int jobId;
    @Expose
    public JobStatus status;
    @Expose
    public double frameRate;
    @Expose
    public int segmentsSplitted;
    @Expose
    public long bytesWritten;
    @Expose
    public Input input;
    @Expose
    public List<EncodingProfile> encodingProfiles;
    @Expose
    public int duration;
    @Expose
    public int encodedDuration;
    @Expose
    public int enqueueDuration;
    @Expose
    public double realtimeFactor;
    @Expose
    public ManifestUrls manifestUrls;
}
