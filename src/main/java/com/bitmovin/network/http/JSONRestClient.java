package com.bitmovin.network.http;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import com.google.gson.Gson;

public class JSONRestClient extends RestClient{

    public JSONRestClient(URI baseUrl) {
        super(baseUrl);
    }

    public <T> T post(URI resource, Map<String, String> headers, Object content, Class<T> classOfT) throws IOException {
        Gson gson = new Gson();
        String jsoncontent = gson.toJson(content);
        return gson.fromJson(this.post(resource, headers, jsoncontent), classOfT);
    }
    public void post(URI resource, Map<String, String> headers, Object content) throws IOException {
        Gson gson = new Gson();
        String jsoncontent = gson.toJson(content);
        this.post(resource, headers, jsoncontent);
    }
    public <T> T get(URI resource, Map<String, String> headers, Class<T> classOfT) throws IOException {
        Gson gson = new Gson();
        String response = this.get(resource, headers);
        return gson.fromJson(response, classOfT);
    }
    public <T> T delete(URI resource, Map<String, String> headers, Class<T> classOfT) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(this.get(resource, headers), classOfT);
    }
}
