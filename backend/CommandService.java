package com.example.carcontrol.service;

import com.example.carcontrol.model.CarState;
import org.springframework.stereotype.Service;

@Service
public class CommandService{
    private final CarState carState=new CarState();
    public CarState processCommand(String rawCommand){
        if(rawCommand==null || rawCommand.trim().isEmpty()){
            return carState;
        }
        String command=rawCommand.toLowerCase().trim();

        if(command.contains("turn on ac")||command.contains("ac on")|command.contains("switch on ac")){
            carState.setAcOn(true);

        }else if (command.contains("turn off ac") || command.contains("ac off") || command.contains("switch off ac")) {
            carState.setAcOn(false);

    }else if (command.contains("increase temperature") || command.contains("temp up") || command.matches(".*\\b(raise|increase)\\b.*temp.*")) {
            carState.setTemperature(carState.getTemperature() + 1);
        } else if (command.contains("decrease temperature") || command.contains("temp down") || command.matches(".*\\b(decrease|lower)\\b.*temp.*")) {
            carState.setTemperature(carState.getTemperature() - 1);
} else if (command.matches(".*set.*temperature.*\\b(\\d{1,2})\\b.*")) {
            // set temperature to N (1-2 digits)
            try {
                String digits = command.replaceAll(".*set.*temperature.*?(\\d{1,2}).*", "$1");
                int t = Integer.parseInt(digits);
                carState.setTemperature(t);
            } catch (Exception ignored) {}
        }

    if (command.contains("turn on headlight") || command.contains("headlights on") || command.contains("lights on")) {
            carState.setHeadlightsOn(true);
        } else if (command.contains("turn off headlight") || command.contains("headlights off") || command.contains("lights off")) {
            carState.setHeadlightsOn(false);
        }

    if (command.contains("play music") || command.contains("music on") || command.contains("start music")) {
            carState.setMusicOn(true);
        } else if (command.contains("stop music") || command.contains("music off") || command.contains("pause music")) {
            carState.setMusicOn(false);
        }

        return carState;




    }}