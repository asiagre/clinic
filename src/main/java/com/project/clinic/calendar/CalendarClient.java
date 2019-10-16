package com.project.clinic.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class CalendarClient {

    @Autowired
    private RestTemplate restTemplate;

//    public String getToken() {
//        body = new FormBody.Builder()
//                .add("text", "EVENT_TEXT")
//                .build();
//        URI url = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/calendar/v3/calendars/clinic.projectkodilla@gmail.com/events")
//                .build().encode().toUri();
//
//    }
}
