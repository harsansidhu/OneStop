package me.harsansidhu.onestop;
/*
    public container class that holds a businesses name, latitude, and longitude
 */
public class Container {
    public final String businessName;
    public final double latitude;
    public final double longitude;

    public Container(String businessName, double latitude, double longitude) {         
        this.businessName = businessName;
        this.latitude     = latitude;
        this.longitude    = longitude;
     }
 }