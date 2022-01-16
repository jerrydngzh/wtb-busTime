package nwHacks2022.bustimeapp.model;

import java.util.Locale;

public class BusStop {
    String name;
    int busNumber;
    int busStop;

    String latitude;
    String longitude;

    String nfcCode;

    public BusStop(String name, int busNumber, int busStop, String latitude, String longitude, String nfcCode) {
        this.name = name;
        this.busNumber = busNumber;
        this.busStop = busStop;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nfcCode = nfcCode;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public int getBusStop() {
        return busStop;
    }

    public void setBusStop(int busStop) {
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
        return nfcCode;
    }
}
