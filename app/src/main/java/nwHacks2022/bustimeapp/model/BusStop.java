package nwHacks2022.bustimeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Locale;
import java.util.UUID;

import nwHacks2022.bustimeapp.controller.TempStopManager;

public class BusStop implements Parcelable {
    String name;
    String busNumber;
    String busStop;

    double latitude;
    double longitude;

    String id = UUID.randomUUID().toString();


    public BusStop(String name, String busNumber, String busStop, double latitude, double longitude) {
        this.name = name;
        this.busNumber = busNumber;
        this.busStop = busStop;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextMessage() {
        String textFormat = "%d %d";
        return String.format(Locale.CANADA, textFormat, busStop, busNumber);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setCoords(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(busNumber);
        out.writeString(busStop);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeString(id);
    }

    public boolean equals(BusStop busStop) {
        if (busStop.name != this.name || busStop.busNumber != this.busNumber || busStop.busStop != this.busStop
            || busStop.latitude != this.latitude || busStop.longitude != this.longitude) {
            return false;
        }
        return true;
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<BusStop> CREATOR = new Parcelable.Creator<BusStop>() {
        public BusStop createFromParcel(Parcel in) {
            return new BusStop(in);
        }

        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private BusStop(Parcel in) {
        this.name = in.readString();
        this.busNumber = in.readString();
        this.busStop = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.id = in.readString();
    }
}
