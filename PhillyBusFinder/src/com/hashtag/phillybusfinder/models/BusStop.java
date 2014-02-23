package com.hashtag.phillybusfinder.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BusStop implements Parcelable {
    Integer id;
    String name;
    Double lat;
    Double lon;
    Double distance;
    
    public BusStop() {
        // TODO Auto-generated constructor stub
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    private BusStop(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.distance = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeDouble(lat);
        out.writeDouble(lon);
        out.writeDouble(distance);
    }

    static final Parcelable.Creator<BusStop> CREATOR = new Parcelable.Creator<BusStop>() {

        @Override
        public BusStop createFromParcel(Parcel source) {
            return new BusStop(source);
        }

        @Override
        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };
}
