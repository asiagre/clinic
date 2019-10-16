package com.project.clinic.bmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Service
public class BmiClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public BmiAnswer getBmi(BmiDto bmiDto) throws IOException {

//        String url = "https://bmi.p.rapidapi.com/";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("x-rapidapi-host","bmi.p.rapidapi.com");
//        headers.add("x-rapidapi-key", "e313e5e53dmshdcb46793fae0f99p145781jsn2ccac2494f09");
//        headers.add("content-type", "application/json");
//        headers.add("accept", "application/json");
//        HttpEntity<String> request = new HttpEntity<>("parameters", headers);
//        HttpEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        return response.getBody();

        MediaType mediaType = MediaType.parse("application/json");
        String json = objectMapper.writeValueAsString(bmiDto);
        RequestBody body = RequestBody.create(mediaType, json);
        //RequestBody body = RequestBody.create(mediaType, createObject(bmiDto));
        //RequestBody body = RequestBody.create(mediaType, "{\"weight\":{\"value\":\"85.00\",\"unit\":\"kg\"},\"height\":{\"value\":\"170.00\",\"unit\":\"cm\"},\"sex\":\"m\",\"age\":\"24\",\"waist\":\"34.00\",\"hip\":\"40.00\"}");
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

    private String createString(BmiDto bmiDto) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"weight\":{\"value\":\"");
        builder.append(bmiDto.getWeight().getValue());
        builder.append("\",\"unit\":\"");
        builder.append(bmiDto.getWeight().getUnit());
        builder.append("\"},\"height\":{\"value\":\"");
        builder.append(bmiDto.getHeight().getValue());
        builder.append("\",\"unit\":\"");
        builder.append(bmiDto.getHeight().getUnit());
        builder.append("\"},\"sex\":\"");
        builder.append(bmiDto.getSex());
        builder.append("\",\"age\":\"");
        builder.append(bmiDto.getAge());
        builder.append("\"}");
        return builder.toString();
    }

}
