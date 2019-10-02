package com.example.lifeline;

public class infoDatabaseWrite {
    private String email,fname,mname,lname,gender,occupation,organization,mobNo,bloodGrp,dob;

    public infoDatabaseWrite(){

    }

    public infoDatabaseWrite(String email, String fname, String mname, String lname, String gender, String occupation, String organization, String mobNo, String bloodGrp, String dob) {
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
}
