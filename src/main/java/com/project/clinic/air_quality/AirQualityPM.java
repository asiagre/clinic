package com.project.clinic.air_quality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQualityPM {

    private String status;

    private int pm25;

    private String city;

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackNested(Map<String,Object> data) {
        Map<String, Object> iaqi = (Map<String, Object>)data.get("iaqi");
        Map<String, Integer> pm = (Map<String, Integer>)iaqi.get("pm25");
        this.pm25 = pm.get("v");

        Map<String, String> cityJson = (Map<String, String>)data.get("city");
        this.city = cityJson.get("name");
    }

}
