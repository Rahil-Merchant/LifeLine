package com.example.lifeline;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
//import java.util.logging.Handler;
//import java.util.logging.Handler;
import android.os.Handler;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

class GeoLocation {
    public static void getAddress(final String locationAddress, final Context context, final Handler handler){
        Thread thread=new Thread(){

            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                String result = null;
                String resultLat=null;
                String resultLong=null;
                String resultLat1=null;
                String resultLong1=null;
                Double checkLat=7.000000;
                Double checkLong=100.00000;
                try {
                    List addressList = geocoder.getFromLocationName(locationAddress,1);
                    if(addressList!=null&& addressList.size()>0){
                        Address address =(Address)addressList.get(0);
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(address.getLatitude());
                        stringBuilder.append(address.getLongitude());
                        //result=stringBuilder.toString();
                        resultLat=String.valueOf(address.getLatitude());
                        resultLong=String.valueOf(address.getLongitude());
                        resultLat1=resultLat.substring(0,9);
                        resultLong1=resultLong.substring(0,9);
                        result=resultLat1+resultLong1;
                        checkLat=Double.parseDouble(resultLat);
                        checkLong=Double.parseDouble(resultLong);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    Message messagelat=Message.obtain();
                    messagelat.setTarget(handler);
                    Message messagelong=Message.obtain();
                    messagelong.setTarget(handler);

                    if(result!=null&&checkLat>=8&&checkLat<=37&&checkLong>=68&&checkLong<=98){
                        message.what = 1;
                        Bundle bundle=new Bundle();
                        Bundle bundlelat=new Bundle();
                        Bundle bundlelong=new Bundle();
                        //result = result;
                        bundle.putString("address",result);
                        bundlelong.putString("addresslong",resultLong);
                        bundlelat.putString("addresslat",resultLat);
                        message.setData(bundle);
                        //message.setData(bundlelat);
                        //message.setData(bundlelong);

                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

}
