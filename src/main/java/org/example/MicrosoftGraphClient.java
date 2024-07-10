package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MicrosoftGraphClient {
    private final String accessToken;

    public MicrosoftGraphClient(String accessToken) {
        this.accessToken = accessToken;
    }

    public void getUserProfile() throws Exception {
        String url = "https://graph.microsoft.com/v1.0/me";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer " + accessToken);

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result.toString());
        System.out.println(jsonNode.toPrettyString());

        client.close();
    }
}