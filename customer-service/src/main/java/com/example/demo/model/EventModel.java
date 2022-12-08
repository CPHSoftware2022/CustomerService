package com.example.demo.model;

import org.springframework.http.HttpStatus;

public class EventModel {

    private String method;
    private HttpStatus status;
    private String responseBody;

    public EventModel() {
    }

    public EventModel(String method, HttpStatus status, String responseBody) {
        this.method = method;
        this.status = status;
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "{"+
                    "\"method\": \"" + method + "\" ," +
                    "\"status\": \"" + status + "\" ," +
                    "\"responseBody\": \"" + responseBody + "\"" +
                "}";
    }

}
