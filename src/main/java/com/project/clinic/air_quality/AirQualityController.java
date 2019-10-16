package com.project.clinic.air_quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/clinic")
public class AirQualityController {

    @Autowired
    private AirQualityClient airQualityClient;

    @RequestMapping(method = RequestMethod.GET, value="air")
    public AirQualityPM getAirQualisty() {
        return airQualityClient.getAirQuality();
    }
}
