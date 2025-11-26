package com.example.carcontrol.dto;

public class CommandRequest{
    private String text;
    public CommandRequest(){}
    public CommandRequest(String text){
        this.text=text;
    }public String getText(){
        return text;
    }public void setText(String text){
        this.text=text;
    }
}