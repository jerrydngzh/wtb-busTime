package nwHacks2022.bustimeapp.model;

import java.util.Locale;
import java.util.UUID;

public class BusStop {
    String name;
    String busNumber;
    String busStop;

    String latitude;
    String longitude;

    String id = UUID.randomUUID().toString();


    public BusStop(String name, String busNumber, String busStop, String latitude, String longitude) {
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

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setCoords(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }
}
