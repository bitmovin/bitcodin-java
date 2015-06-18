package com.bitmovin.network.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RestClient {

    private URI baseUrl;

    public RestClient(URI baseUrl) {

        this.baseUrl = baseUrl;
    }

    public String post(URI resource, Map<String, String> headers, String content) throws IOException {

        URL url = this.baseUrl.resolve(resource).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        Iterator<Entry<String, String>> it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            connection.setRequestProperty(pair.getKey(), pair.getValue());
        }

        OutputStream os = connection.getOutputStream();
        os.write(content.getBytes());
        os.flush();

        if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED)
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String read = "";
        StringBuffer sb = new StringBuffer(read);
        while ((read = reader.readLine()) != null)
            sb.append(read);

        connection.disconnect();
        return sb.toString();
    }

}
