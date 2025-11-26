package com.example.carcontrol.dto;

import com.example.carcontrol.model.CarState;

public class CommandResponse {
    private String message;
    private CarState state;

    public CommandResponse() {}

    public CommandResponse(String message, CarState state) {
        this.message = message;
        this.state = state;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public CarState getState() { return state; }
    public void setState(CarState state) { this.state = state; }
}
