package com.example.carcontrol.controller;

import com.example.carcontrol.dto.CommandRequest;
import com.example.carcontrol.dto.CommandResponse;
import com.example.carcontrol.model.CarState;
import com.example.carcontrol.service.CommandService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CarController {

    private final CommandService commandService;

    public CarController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/command")
    public CommandResponse handleCommand(@RequestBody CommandRequest request) {
        String text = request != null ? request.getText() : null;
        CarState updated = commandService.processCommand(text);
        String msg = "Processed: \"" + (text == null ? "" : text) + "\"";
        return new CommandResponse(msg, updated);
    }

    @GetMapping("/state")
    public CarState getState() {
        return commandService.processCommand(null);
    }
}
