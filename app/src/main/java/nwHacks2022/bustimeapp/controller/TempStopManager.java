package nwHacks2022.bustimeapp.controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.model.BusStop;

public class TempStopManager implements Parcelable {
    private final ArrayList<BusStop> busStops;

    public TempStopManager(ArrayList<BusStop> stops) {
        busStops = stops;
    }


    public ArrayList<BusStop> getAll() {
        return busStops;
    }

    public BusStop get(int position) {
        return getAll().get(position);
    }

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(busStops);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<TempStopManager> CREATOR = new Parcelable.Creator<TempStopManager>() {
        public TempStopManager createFromParcel(Parcel in) {
            return new TempStopManager(in);
        }

        public TempStopManager[] newArray(int size) {
            return new TempStopManager[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private TempStopManager(Parcel in) {
        busStops = in.readArrayList(getClass().getClassLoader());
    }
}
