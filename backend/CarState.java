package com.example.carcontrol.model;
public class CarState{
    private boolean acOn;
    private int temperature;
    private boolean headlightsOn;
    private boolean musicOn;
    public CarState(){
        this.acOn=false;
        this.temperature=32;
        this.headlightsOn=false;
        this.musicOn=false;
    }

    public boolean isAcOn(){
        return acOn;
    }public void setAcOn(boolean acOn){this.acOn=acOn;}

    public int getTemperature(){return temperature;}
    public void setTemperature(int temperature){
        this.temperature=temperature;}
    
    
    public boolean isHeadlightsOn() { return headlightsOn; }
    public void setHeadlightsOn(boolean headlightsOn) { this.headlightsOn = headlightsOn; }

    public boolean isMusicOn() { return musicOn; }
    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }
}