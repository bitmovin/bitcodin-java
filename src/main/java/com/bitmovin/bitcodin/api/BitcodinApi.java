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
import java.util.HashMap;

import com.bitmovin.network.http.RestClient;

public class BitcodinApi {

    private String apiKey;

    public BitcodinApi(String apiKey) {

        this.apiKey = apiKey;
    }

    public String getKey() {
        return this.apiKey;
    }
    
    public String createInput(String url){
        
        RestClient rest = new RestClient("http://portal.bitcodin.com/api");

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("bitcodin-api-version", "v1");
        headers.put("bitcodin-api-key", this.apiKey);
        
        try {
            return rest.post("/input/create", headers, "{\"url\": \"" + url + "\"}");
        } catch (IOException e) {
            return "Input could not be analyzed!";
        }
    }

}
