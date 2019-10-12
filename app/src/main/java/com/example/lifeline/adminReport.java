package com.example.lifeline;

public class adminReport {
    private String email, fname, mname, lname, gender, occupation, organization, mobNo, bloodGrp, dob, last_donated, uid, doa, repdoc;
    private int timesDonated, rewards_count;
    Boolean isAccepted;

    public adminReport() {

    }

    public adminReport(String email, String fname, String mname, String lname, String gender, String occupation, String organization, String mobNo, String bloodGrp, String dob, String last_donated, String uid, String doa, String repdoc, int timesDonated, int rewards_count, Boolean isAccepted) {
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
        this.last_donated = last_donated;
        this.uid = uid;
        this.doa = doa;
        this.repdoc = repdoc;
        this.timesDonated = timesDonated;
        this.rewards_count = rewards_count;
        this.isAccepted = isAccepted;
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

    public String getLast_donated() {
        return last_donated;
    }

    public String getUid() {
        return uid;
    }

    public String getDoa() {
        return doa;
    }

    public String getRepdoc() {
        return repdoc;
    }

    public int getTimesDonated() {
        return timesDonated;
    }

    public int getRewards_count() {
        return rewards_count;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }
}