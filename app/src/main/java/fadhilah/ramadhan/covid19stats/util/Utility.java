package fadhilah.ramadhan.covid19stats.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fadhilah.ramadhan.covid19stats.global.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;

public class Utility {
    private static List<DataStats> dataStatsList;

    private Utility(){
    }

    public static void clearGlobalVariable(){
        GlobalVar.getInstance().setDataStatsCountry(null);
    }

    public static final List<DataStats> buildDataStats(String json) {
        dataStatsList = new ArrayList<DataStats>();
        DataStats dataStats = new DataStats();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                dataStats.setActiveCases(array.getJSONObject(i).getInt("Active"));
                dataStats.setCured(array.getJSONObject(i).getInt("Recovered"));
                dataStats.setDeath(array.getJSONObject(i).getInt("Deaths"));
                dataStatsList.add(dataStats);
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
        return dataStatsList;
    }
}
