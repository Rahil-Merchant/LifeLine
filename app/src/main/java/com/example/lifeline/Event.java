package com.example.lifeline;

import android.content.Context;

public class Event {
    private String Title;
    private String Description;
    private String Image;
    public Context context;
    private String Date;
    private String Phone;
    private String Address;
    private String Latitude;
    private String Longitude;

    public Event(){
        //Useful
    }

    public Event(String title, String description, String Image, String date,String phone,String address,String latitude,String longitude){
        this.Title=title;
        this.Description=description;
        this.Image=Image;
        this.Date=date;
        this.Phone=phone;
        this.Address=address;
        this.Latitude=latitude;
        this.Longitude=longitude;
    }


    public String getTitle() {

        return Title;
    }

    public String getDescription() {

        return Description;
    }

    public String getDate(){
        return Date;
    }
  /*  public String getImage() {
        return Image;
    }*/

    public String getImage() {

        return Image;
    }

    public String getPhone(){
        return Phone;
    }

    public String getAddress(){
        return Address;
    }

    public String getLatitude(){
        return Latitude;
    }

    public String getLongitude(){
        return Longitude;
    }


    /*public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }*/
}
