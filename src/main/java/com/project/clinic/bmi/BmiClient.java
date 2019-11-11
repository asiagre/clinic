package com.project.clinic.bmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BmiClient {

    @Value("${bmi.api.endpoint.prod}")
    private String apiEndpoint;

    @Value("${x-rapidapi-host}")
    private String apiHost;

    @Value("${x-rapidapi-key}")
    private String apiKey;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public BmiAnswer getBmi(BmiDto bmiDto) throws IOException {

        MediaType mediaType = MediaType.parse("application/json");
        String json = objectMapper.writeValueAsString(bmiDto);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(apiEndpoint)
                .post(body)
                .addHeader("x-rapidapi-host", apiHost)
                .addHeader("x-rapidapi-key", apiKey)
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return objectMapper.readValue(response.body().string(), BmiAnswer.class);
    }


}
