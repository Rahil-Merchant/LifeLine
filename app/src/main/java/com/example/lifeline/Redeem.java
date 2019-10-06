package com.example.lifeline;

public class Redeem {

    private String fname,mname,lname,email,mobNo;
    private int rewards_count,timesDonated;

    public Redeem(){

    }

    public Redeem(String fname, String mname, String lname, String email, String mobNo, int rewards_count, int timesDonated) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.email = email;
        this.mobNo = mobNo;
        this.rewards_count = rewards_count;
        this.timesDonated = timesDonated;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public int getRewards_count() {
        return rewards_count;
    }

    public int getTimesDonated() {
        return timesDonated;
    }
}
