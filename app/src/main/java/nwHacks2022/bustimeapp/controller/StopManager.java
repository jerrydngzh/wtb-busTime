package nwHacks2022.bustimeapp.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.model.BusStop;

public class StopManager {
    private ArrayList<BusStop> busStops = new ArrayList<>();
    public static StopManager instance;

    private SaveManager saveOption;

    private StopManager() {
    }

    public static StopManager getInstance() {
        if (instance == null) {
            instance = new StopManager();
        }
        return instance;
    }

    public void add(BusStop busStop) {
        busStops.add(busStop);
        save();
    }

    public ArrayList<BusStop> getAll() {
        return busStops;
    }

    public BusStop get(int position) {
        return getAll().get(position);
    }


    public void fromJson(String jsonString) {
        Gson gson = new Gson();
        if (!(jsonString.isEmpty())) {
            busStops = gson.fromJson(jsonString, new TypeToken<ArrayList<BusStop>>() {}.getType());
        }
    }

    private String toJson() {
        Gson gson = new Gson();
        return gson.toJson(busStops);
    }

    private void save() {
        saveOption.save(toJson());
    }

    public void load() {
        fromJson(saveOption.load());
    }

    public void deleteStop(BusStop toDelete) {
        for (BusStop bs : busStops) {
            if (bs.getId().equals(toDelete.getId())) {
                busStops.remove(bs);
            }
        }
        save();
    }

    public void setSaveOption(SaveManager saveInterface) {
        saveOption = saveInterface;
    }

    public interface SaveManager {
        String load();
        void save(String saveJson);
    }
}
