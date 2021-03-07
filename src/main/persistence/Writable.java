package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Based off of the JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();

    // Maybe?
//    public JSONArray toJsonArray(ArrayList itemList) {
//        JSONArray jsonArray = new JSONArray();
//
//        for (Object i: itemList) {
//            i.toJson();
//        }
//    }
}
