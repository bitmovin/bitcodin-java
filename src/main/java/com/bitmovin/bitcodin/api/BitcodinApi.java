/*
 * BitcodinApi.java
 *****************************************************************************
 * Copyright (C) 2015, bitmovin, All Rights Reserved
 *
 * Created on: Jun 17, 2015
 * Author: Christopher Mueller <christopher.mueller@bitmovin.net>
 *
 * This source code and its use and distribution, is subject to the terms
 * and conditions of the applicable license agreement.
 *****************************************************************************/

package com.bitmovin.bitcodin.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.bitmovin.bitcodin.api.input.Input;
import com.bitmovin.bitcodin.api.input.InputList;
import com.bitmovin.bitcodin.api.job.Job;
import com.bitmovin.bitcodin.api.job.JobConfig;
import com.bitmovin.bitcodin.api.job.JobList;
import com.bitmovin.bitcodin.api.media.EncodingProfile;
import com.bitmovin.bitcodin.api.media.EncodingProfileConfig;
import com.bitmovin.bitcodin.api.media.EncodingProfileList;
import com.bitmovin.bitcodin.api.output.FTPOutputConfig;
import com.bitmovin.bitcodin.api.output.GCSOutputConfig;
import com.bitmovin.bitcodin.api.output.Output;
import com.bitmovin.bitcodin.api.output.OutputList;
import com.bitmovin.bitcodin.api.output.S3OutputConfig;
import com.bitmovin.bitcodin.api.statistics.Statistic;
import com.bitmovin.bitcodin.api.transfer.TransferConfig;
import com.bitmovin.bitcodin.api.transfer.TransferList;
import com.bitmovin.network.http.RestClient;
import com.google.gson.Gson;

public class BitcodinApi {

    private String apiKey;
    private String apiUrl;
    private HashMap<String, String> defaultHeaders = new HashMap<String, String>();

    public BitcodinApi(String apiKey) {

        this.apiKey = apiKey;
        this.apiUrl = "http://portal.bitcodin.com/api/";
        this.defaultHeaders.put("Content-Type", "application/json");
        this.defaultHeaders.put("bitcodin-api-version", "v1");
        this.defaultHeaders.put("bitcodin-api-key", this.apiKey);
    }

    public String getKey() {
        return this.apiKey;
    }
    
    public Input createInput(String url) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.post(new URI("input/create"), this.defaultHeaders, "{\"url\": \"" + url + "\"}"), Input.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public InputList listInputs(int pageNumber) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("inputs/" + Integer.toString(pageNumber)), this.defaultHeaders), InputList.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Input getInput(int id) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("input/" + Integer.toString(id)), this.defaultHeaders), Input.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteInput(int id) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            rest.delete(new URI("input/" + Integer.toString(id)), this.defaultHeaders);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void createS3Output(S3OutputConfig output) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String content = gson.toJson(output);
            rest.post(new URI("output/create"), this.defaultHeaders, content);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void createGCSOutput(GCSOutputConfig output) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String content = gson.toJson(output);
            rest.post(new URI("output/create"), this.defaultHeaders, content);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void createFTPOutput(FTPOutputConfig output) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String content = gson.toJson(output);
            rest.post(new URI("output/create"), this.defaultHeaders, content);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public OutputList listOutputs(int pageNumber) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("outputs/" + Integer.toString(pageNumber)), this.defaultHeaders), OutputList.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Output getOutput(int id) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("output/" + Integer.toString(id)), this.defaultHeaders), Output.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteOutput(int id) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            rest.delete(new URI("output/" + Integer.toString(id)), this.defaultHeaders);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public EncodingProfile createEncodingProfile(EncodingProfileConfig profile) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String content = gson.toJson(profile);
            return gson.fromJson(rest.post(new URI("encoding-profile/create"), this.defaultHeaders, content), EncodingProfile.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public EncodingProfileList listEncodingProfiles(int pageNumber) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("encoding-profiles/" + Integer.toString(pageNumber)), this.defaultHeaders), EncodingProfileList.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public EncodingProfile getEncodingProfile(int id) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("encoding-profile/" + Integer.toString(id)), this.defaultHeaders), EncodingProfile.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Job createJob(JobConfig jobConfig) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String content = gson.toJson(jobConfig);
            return gson.fromJson(rest.post(new URI("job/create"), this.defaultHeaders, content), Job.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public JobList listJobs(int pageNumber) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("jobs/" + Integer.toString(pageNumber)), this.defaultHeaders), JobList.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Job getJob(int id) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("job/" + Integer.toString(id)), this.defaultHeaders), Job.class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void transfer(TransferConfig config) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String content = gson.toJson(config);
            String response = rest.post(new URI("job/transfer"), this.defaultHeaders, content);
            /*TODO: Test when outputs work */
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public TransferList listTransfers(int jobId) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            return gson.fromJson(rest.get(new URI("jobs/" + Integer.toString(jobId) + "/transfers"), this.defaultHeaders), TransferList.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statistic getStatistics() {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String response = rest.get(new URI("statistics"), this.defaultHeaders);
            return gson.fromJson(response, Statistic.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statistic getStatistics(String from, String to) {
        try {
            RestClient rest = new RestClient(new URI(this.apiUrl));
            Gson gson = new Gson();
            String response = rest.get(new URI("statistics/jobs/" + from + "/" + to), this.defaultHeaders);
            return gson.fromJson(response, Statistic.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
