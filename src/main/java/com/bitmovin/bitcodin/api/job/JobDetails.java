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
    public String jobFolder;
    @Expose
    public Input input;
    @Expose
    public List<EncodingProfile> encodingProfiles;
    @Expose
    public String statusDescription;
    @Expose
    public CreatedAt createdAt;
    @Expose
    public List<String> warnings;
    @Expose
    public int duration;
    @Expose
    public int encodedDuration;
    @Expose
    public int enqueueDuration;
    @Expose
    public double realtimeFactor;
    @Expose
    public EnqueuedAt enqueuedAt;
    @Expose
    public StartedAt startedAt;
    @Expose
    public FinishedAt finishedAt;
    @Expose
    public ManifestUrls manifestUrls;

    public class CreatedAt {

        @Expose
        public String date;
        @Expose
        public Timezone timezone;
    }

    public class EnqueuedAt {

        @Expose
        public String date;
        @Expose
        public Timezone timezone;
    }

    public class FinishedAt {

        @Expose
        public String date;
        @Expose
        public Timezone timezone;
    }

    public class StartedAt {

        @Expose
        public String date;
        @Expose
        public Timezone timezone;
    }

    public class Timezone {

        @Expose
        public int timezoneType;
        @Expose
        public String timezone;

    }
}
