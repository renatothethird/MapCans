package com.example.masterpeps.mapcans2.Model;

/**
 * Created by masterpeps on 3/24/2017.
 */
public class Mapbase {
    public String displayName;
    public String latitude;
    public String longitude;
    public String placedBy;
    public String uId;


    public Mapbase(){}

    public Mapbase(String displayName, String latitude, String longitude, String placedBy, String uId){
        this.displayName = displayName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placedBy = placedBy;
        this.uId = uId;
    }

    public String getDisplayName(){
        return displayName;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public String getPlacedBy(){
        return placedBy;
    }

    public String getuId(){
        return uId;
    }
}
