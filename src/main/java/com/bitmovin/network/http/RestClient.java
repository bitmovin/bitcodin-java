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

    protected URI baseUrl;

    public RestClient(URI baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String request(RequestMethod method, URI resource, Map<String, String> headers) throws IOException, RestException {
        return this.request(method, resource, headers, null);
    }

    public String request(RequestMethod method, URI resource, Map<String, String> headers, String content) throws IOException, RestException {
        URL url = this.baseUrl.resolve(resource).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (!System.getProperty("java.vendor", "").toLowerCase().equals("the android project") &&
                !System.getProperty("java.vm.vendor", "").toLowerCase().equals("the android project")) {
            connection.setDoOutput(true);
        }
        connection.setRequestMethod(method.name());

        Iterator<Entry<String, String>> it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            connection.setRequestProperty(pair.getKey(), pair.getValue());
        }

        if (content != null) {
            OutputStream os = connection.getOutputStream();
            os.write(content.getBytes());
            os.flush();
        }

        if (connection.getResponseCode() < 200 || connection.getResponseCode() >= 300) {

            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getErrorStream())));

            String read = "";
            StringBuffer sb = new StringBuffer(read);
            while ((read = reader.readLine()) != null)
                sb.append(read);

            throw new RestException(connection.getResponseCode(), connection.getResponseMessage(), sb.toString());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String read = "";
        StringBuffer sb = new StringBuffer(read);
        while ((read = reader.readLine()) != null)
            sb.append(read);

        connection.disconnect();
        String response = sb.toString();

        return response;
    }

}
