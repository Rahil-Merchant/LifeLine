package com.example.lifeline;

public class infoDatabaseWrite {
    private String email,fname,mname,lname,gender,occupation,organization,mobNo,bloodGrp,dob,last_donated;
    private int timesDonated,rewards_count;

    public infoDatabaseWrite(){

    }

    public infoDatabaseWrite(String email, String fname, String mname, String lname, String gender, String occupation, String organization, String mobNo, String bloodGrp, String dob, int timesDonated, int rewards_count, String last_donated) {
        this.email = email;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.gender = gender;
        this.occupation = occupation;
        this.organization = organization;
        this.mobNo = mobNo;
        this.bloodGrp = bloodGrp;
        this.dob = dob;
        this.timesDonated = timesDonated;
        this.rewards_count = rewards_count;
        this.last_donated = last_donated;
    }

    public String getEmail() {
        return email;
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

    public String getGender() {
        return gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getOrganization() {
        return organization;
    }

    public String getMobNo() {
        return mobNo;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public String getDob() {
        return dob;
    }

    public int getTimesDonated() {
        return timesDonated;
    }

    public String getLast_donated() {
        return last_donated;
    }

    public int getRewards_count() {
        return rewards_count;
    }
}
