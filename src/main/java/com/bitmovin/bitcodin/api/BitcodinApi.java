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
import java.util.List;

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

}
