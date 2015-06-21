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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.bitmovin.bitcodin.api.exception.BitcodinApiException;
import com.bitmovin.bitcodin.api.input.HTTPInputConfig;
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
import com.bitmovin.network.http.JSONRestClient;
import com.bitmovin.network.http.RestException;

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
    
    public <T> T post(String resource, Map<String, String> headers, Object content, Class<T> classOfT) throws BitcodinApiException{
        JSONRestClient jRest;
        try {
            jRest = new JSONRestClient(new URI(this.apiUrl));
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("API url not valid", e);
        }
        try {
            return jRest.post(new URI(resource), this.defaultHeaders, content, classOfT);
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("Resource url not valid", e);
        }  catch (RestException e) {
            throw new BitcodinApiException("Request is not vaild: " + e.body, e);
        } catch (FileNotFoundException e) {
            throw new BitcodinApiException("Resource not available", e);
        } catch (IOException e) {
            throw new BitcodinApiException("Network problem", e);
        }
    }
    
    public void post(String resource, Map<String, String> headers, Object content) throws BitcodinApiException {
        JSONRestClient jRest;
        try {
            jRest = new JSONRestClient(new URI(this.apiUrl));
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("API url not valid", e);
        }
        try {
            jRest.post(new URI(resource), this.defaultHeaders, content);
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("Resource url not valid", e);
        }  catch (RestException e) {
            throw new BitcodinApiException("Request is not vaild: " + e.body, e);
        } catch (FileNotFoundException e) {
            throw new BitcodinApiException("Resource not available", e);
        } catch (IOException e) {
            throw new BitcodinApiException("Network problem", e);
        }
    }
    
    public <T> T get(String resource, Map<String, String> headers, Class<T> classOfT) throws BitcodinApiException {
        JSONRestClient jRest;
        try {
            jRest = new JSONRestClient(new URI(this.apiUrl));
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("API url not valid", e);
        }
        try {
            return jRest.get(new URI(resource), this.defaultHeaders, classOfT);
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("Resource url not valid", e);
        }  catch (RestException e) {
            throw new BitcodinApiException("Request is not vaild: " + e.body, e);
        } catch (FileNotFoundException e) {
            throw new BitcodinApiException("Resource not available", e);
        } catch (IOException e) {
            throw new BitcodinApiException("Network problem", e);
        }
    }
    public <T> T delete(String resource, Map<String, String> headers, Class<T> classOfT) throws BitcodinApiException {
        JSONRestClient jRest;
        try {
            jRest = new JSONRestClient(new URI(this.apiUrl));
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("API url not valid", e);
        }
        try {
            return jRest.delete(new URI(resource), this.defaultHeaders, classOfT);
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("Resource url not valid", e);
        }  catch (RestException e) {
            throw new BitcodinApiException("Request is not vaild: " + e.body, e);
        } catch (FileNotFoundException e) {
            throw new BitcodinApiException("Resource not available", e);
        } catch (IOException e) {
            throw new BitcodinApiException("Network problem", e);
        }
    }
    public void delete(String resource, Map<String, String> headers) throws BitcodinApiException {
        JSONRestClient jRest;
        try {
            jRest = new JSONRestClient(new URI(this.apiUrl));
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("API url not valid", e);
        }
        try {
            jRest.delete(new URI(resource), this.defaultHeaders);
        } catch (URISyntaxException e) {
            throw new BitcodinApiException("Resource url not valid", e);
        }  catch (RestException e) {
            throw new BitcodinApiException("Request is not vaild: " + e.body, e);
        } catch (FileNotFoundException e) {
            throw new BitcodinApiException("Resource not available", e);
        } catch (IOException e) {
            throw new BitcodinApiException("Network problem", e);
        }
    }

    public Input createInput(HTTPInputConfig httpInputConfig) throws BitcodinApiException {
        return this.post("input/create", this.defaultHeaders, httpInputConfig, Input.class);
    }

    public InputList listInputs(int pageNumber) throws BitcodinApiException {
        return this.get("inputs/" + Integer.toString(pageNumber), this.defaultHeaders, InputList.class);
    }

    public Input getInput(int id) throws BitcodinApiException {
        return this.get("input/" + Integer.toString(id), this.defaultHeaders, Input.class);
    }

    public void deleteInput(int id) throws BitcodinApiException {
        this.delete("input/" + Integer.toString(id), this.defaultHeaders);
    }

    public Output createS3Output(S3OutputConfig output) throws BitcodinApiException {
        return this.post("output/create", this.defaultHeaders, output, Output.class);
    }

    public Output createGCSOutput(GCSOutputConfig output) throws BitcodinApiException {
        return this.post("output/create", this.defaultHeaders, output, Output.class);
    }

    public Output createFTPOutput(FTPOutputConfig output) throws BitcodinApiException {
        return this.post("output/create", this.defaultHeaders, output, Output.class);
    }

    public OutputList listOutputs(int pageNumber) throws BitcodinApiException {
        return this.get("outputs/" + Integer.toString(pageNumber), this.defaultHeaders, OutputList.class);
    }

    public Output getOutput(int id) throws BitcodinApiException {
        return this.get("output/" + Integer.toString(id), this.defaultHeaders, Output.class);
    }

    public void deleteOutput(int id) throws BitcodinApiException {
        this.delete("output/" + Integer.toString(id), this.defaultHeaders);
    }

    public EncodingProfile createEncodingProfile(EncodingProfileConfig profile) throws BitcodinApiException {
        return this.post("encoding-profile/create", this.defaultHeaders, profile, EncodingProfile.class);
    }

    public EncodingProfileList listEncodingProfiles(int pageNumber) throws BitcodinApiException {
        return this.get("encoding-profiles/" + Integer.toString(pageNumber), this.defaultHeaders, EncodingProfileList.class);
    }

    public EncodingProfile getEncodingProfile(int id) throws BitcodinApiException {
        return this.get("encoding-profile/" + Integer.toString(id), this.defaultHeaders, EncodingProfile.class);
    }

    public Job createJob(JobConfig jobConfig) throws BitcodinApiException {
        return this.post("job/create", this.defaultHeaders, jobConfig, Job.class);
    }

    public JobList listJobs(int pageNumber) throws BitcodinApiException {
        return this.get("jobs/" + Integer.toString(pageNumber), this.defaultHeaders, JobList.class);
    }

    public Job getJob(int id) throws BitcodinApiException {
        return this.get("job/" + Integer.toString(id), this.defaultHeaders, Job.class);
    }

    public void transfer(TransferConfig transferConfig) throws BitcodinApiException {
        this.post("job/transfer", this.defaultHeaders, transferConfig);
    }

    public TransferList listTransfers(int jobId) throws BitcodinApiException {
        return this.get("jobs/" + Integer.toString(jobId) + "/transfers", this.defaultHeaders, TransferList.class);
    }

    public Statistic getStatistics() throws BitcodinApiException {
        return this.get("statistics", this.defaultHeaders, Statistic.class);
    }

    public Statistic getStatistics(String from, String to) throws BitcodinApiException {
        return this.get("statistics/jobs/" + from + "/" + to, this.defaultHeaders, Statistic.class);
    }
}
