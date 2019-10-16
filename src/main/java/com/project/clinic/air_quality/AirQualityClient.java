package com.project.clinic.air_quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirQualityClient {

    @Value("${air.api.endpoint.prod}")
    private String airEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public AirQualityPM getAirQuality() {
        AirQualityPM response = restTemplate.getForObject(airEndpoint, AirQualityPM.class);
        return response;
    }
}
