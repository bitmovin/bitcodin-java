package com.bitmovin.bitcodin.api.transfer;


import com.google.gson.annotations.Expose;

public class Transfer {

    public class OutputProfile {
        @Expose
        public String name;
        @Expose
        public String outputUrl;
        @Expose
        public String relOutputUrl;
    }

    @Expose
    public int id;
    @Expose
    public int jobId;
    @Expose
    public String status;
    @Expose
    public int progress;
    @Expose
    public String createdAt;
    @Expose
    public String finishedAt;
    @Expose
    public int duration;
    @Expose
    public OutputProfile outputProfile;
}
