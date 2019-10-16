package com.project.clinic.bmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BmiAnswer {

    private Bmi bmi;
    @JsonProperty("ideal_weight")
    private String idealWeight;
    @JsonProperty("surface_area")
    private String surfaceArea;

}
