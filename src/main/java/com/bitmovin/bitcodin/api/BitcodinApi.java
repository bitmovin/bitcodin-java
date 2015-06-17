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

public class BitcodinApi {

    private String apiKey;

    public BitcodinApi(String apiKey) {

        this.apiKey = apiKey;
    }

    public String getKey() {
        return this.apiKey;
    }

}
