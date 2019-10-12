package com.example.lifeline;

public class History {
    String doa, timeSlot, repdoc;

    public History(){

    }

    public History(String doa, String timeSlot, String repdoc) {
        this.doa = doa;
        this.timeSlot = timeSlot;
        this.repdoc = repdoc;
    }

    public String getDoa() {
        return doa;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getRepdoc() {
        return repdoc;
    }
}
