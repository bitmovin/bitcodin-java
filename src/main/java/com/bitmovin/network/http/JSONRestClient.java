package com.bitmovin.network.http;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import com.google.gson.Gson;

public class JSONRestClient extends RestClient{

    public JSONRestClient(URI baseUrl) {
        super(baseUrl);
    }

    public <T> T post(URI resource, Map<String, String> headers, Object content, Class<T> classOfT) throws IOException, RestException {
        Gson gson = new Gson();
        String jsoncontent = gson.toJson(content);
        String response = this.request(RequestMethod.POST, resource, headers, jsoncontent);
        return gson.fromJson(response, classOfT);
    }
    public <T> T get(URI resource, Map<String, String> headers, Class<T> classOfT) throws IOException, RestException {
        Gson gson = new Gson();
        String response = this.request(RequestMethod.GET, resource, headers);
        return gson.fromJson(response, classOfT);
    }
    public <T> T delete(URI resource, Map<String, String> headers, Class<T> classOfT) throws IOException, RestException {
        Gson gson = new Gson();
        String response = this.request(RequestMethod.DELETE, resource, headers);
        return gson.fromJson(response, classOfT);
    }
    
    public void post(URI resource, Map<String, String> headers, Object content) throws IOException, RestException {
        Gson gson = new Gson();
        String jsoncontent = gson.toJson(content);
        this.request(RequestMethod.POST, resource, headers, jsoncontent);
    }
    public void get(URI resource, Map<String, String> headers) throws IOException, RestException {
        this.request(RequestMethod.GET, resource, headers);
    }
    public void delete(URI resource, Map<String, String> headers) throws IOException, RestException {
        this.request(RequestMethod.DELETE, resource, headers);
    }
}
