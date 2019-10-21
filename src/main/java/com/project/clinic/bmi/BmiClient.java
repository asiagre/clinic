package com.project.clinic.bmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BmiClient {

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public BmiAnswer getBmi(BmiDto bmiDto) throws IOException {

        MediaType mediaType = MediaType.parse("application/json");
        String json = objectMapper.writeValueAsString(bmiDto);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://bmi.p.rapidapi.com/")
                .post(body)
                .addHeader("x-rapidapi-host", "bmi.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "e313e5e53dmshdcb46793fae0f99p145781jsn2ccac2494f09")
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return objectMapper.readValue(response.body().string(), BmiAnswer.class);
    }


}
