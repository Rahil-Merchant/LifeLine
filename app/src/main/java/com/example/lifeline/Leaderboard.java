package com.example.lifeline;

public class Leaderboard {
    private String fname;
    private int timesDonated;

    public Leaderboard(){

    }

    public Leaderboard(String fname, int timesDonated) {
        this.fname = fname;
        this.timesDonated = timesDonated;
    }

    public String getFname() {
        return fname;
    }

    public int getTimesDonated() {
        return timesDonated;
    }
}
