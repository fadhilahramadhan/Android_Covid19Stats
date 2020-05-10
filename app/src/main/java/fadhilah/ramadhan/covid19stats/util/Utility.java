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
        dataStatsList.clear();

        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                DataStats dataStats = new DataStats();
                dataStats.setActiveCases(array.getJSONObject(i).getInt("Active"));
                dataStats.setCured(array.getJSONObject(i).getInt("Recovered"));
                dataStats.setDeath(array.getJSONObject(i).getInt("Deaths"));
                dataStats.setCountry(array.getJSONObject(i).getString("Country"));
                dataStats.setCountryCode(array.getJSONObject(i).getString("CountryCode"));
                dataStats.setDate(array.getJSONObject(i).getString("Date"));
                dataStats.setPostive(array.getJSONObject(i).getInt("Confirmed"));
                dataStatsList.add(dataStats);
            }
        } catch (JSONException e) {
            Log.e("JSONException aaa", e.getMessage());
        }
        return dataStatsList;
    }

    public static final List<DataStats> buildDataSummary(String json) {
        dataStatsList = new ArrayList<DataStats>();
        dataStatsList.clear();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                DataStats dataStats = new DataStats();
                dataStats.setCured(array.getJSONObject(i).getInt("TotalRecovered"));
                dataStats.setDeath(array.getJSONObject(i).getInt("TotalDeaths"));
                dataStats.setCountry(array.getJSONObject(i).getString("Country"));
                dataStats.setCountryCode(array.getJSONObject(i).getString("CountryCode"));
                dataStats.setDate(array.getJSONObject(i).getString("Date"));
                dataStats.setPostive(array.getJSONObject(i).getInt("TotalConfirmed"));
                dataStatsList.add(dataStats);
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
        return dataStatsList;
    }
}
