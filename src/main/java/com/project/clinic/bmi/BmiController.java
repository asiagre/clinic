package com.project.clinic.bmi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/clinic")
public class BmiController {

    @Autowired
    private BmiClient bmiClient;

    @RequestMapping(method = RequestMethod.POST, value = "bmi", consumes = APPLICATION_JSON_VALUE)
    public BmiAnswer getBmi(@RequestBody BmiDto bmiDto) throws IOException {
        return bmiClient.getBmi(bmiDto);
    }
}
