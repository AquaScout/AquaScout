package com.example.aquascout;

import android.location.Geocoder;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Review")
public class ReviewParseObject extends ParseObject implements Serializable{

    public ReviewParseObject(){}

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser pu) {
        put("user", pu);
    }

    public ParseGeoPoint getAddress(){
        return getParseGeoPoint("address");
    }

    public void setAddress(ParseGeoPoint add){
        put("address", add);
    }

    public String getDetails(){
        return getString("details");
    }

    public void setDetails(String detail){
        put("details", detail);
    }

    public String getComment(){
        return getString("comment");
    }

    public void setComment(String com){
        put("comment", com);
    }

    public int getTemp(){
        return getInt("temperature");
    }

    public void setTemp(int temp){
        put("temperature", temp);
    }
}
