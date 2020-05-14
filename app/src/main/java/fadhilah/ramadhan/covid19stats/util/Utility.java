package fadhilah.ramadhan.covid19stats.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fadhilah.ramadhan.covid19stats.Activity.MainActivity;
import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.component.AlertDialogCustom;
import fadhilah.ramadhan.covid19stats.model.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;

public class Utility {
    private static List<DataStats> dataStatsList;

    private Utility(){
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
            Log.e("JSONException : ", e.getMessage());
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
            Log.e("JSONException : ", e.getMessage());
        }
        return dataStatsList;
    }

    public static final DataStats buildDataGlobal(String json) {
        DataStats dataStats = new DataStats();
        try {
            JSONObject jsonObject = new JSONObject(json);
                dataStats.setCured(jsonObject.getInt("TotalRecovered"));
                dataStats.setDeath(jsonObject.getInt("TotalDeaths"));
                dataStats.setPostive(jsonObject.getInt("TotalConfirmed"));
        } catch (JSONException e) {
            Log.e("JSONException : ", e.getMessage());
        }
        return dataStats;
    }

    public static final String dateFormat(String format, String dateInput){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);
        Date date = null;
        try{
            date = inputFormat.parse(dateInput);
        }catch (ParseException e){
            Log.e("ParseException : ", e.getMessage());
        }
        return outputFormat.format(date);
    }

    public static boolean  cekValidResult(String result, Activity activity){

        try{
            if(!result.contains("errorCode")){
                return true;
            }else{
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.has("errorCode")){
                    String errorCode = jsonObject.getString("errorCode");
                    String message = jsonObject.getString("fullMessage");

                        AlertDialogCustom dialogs = new AlertDialogCustom(activity);
                        dialogs.setTitleandContent(activity.getString(R.string.header_message), message, activity.getString(R.string.button_close));

                    return false;
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
            AlertDialogCustom dialogs = new AlertDialogCustom(activity);
            dialogs.setTitleandContent(activity.getString(R.string.header_message), activity.getString(R.string.message_gagal), activity.getString(R.string.button_close));
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            AlertDialogCustom dialogs = new AlertDialogCustom(activity);
            dialogs.setTitleandContent(activity.getString(R.string.header_message), activity.getString(R.string.message_gagal), activity.getString(R.string.button_close));

            return false;
        }
        return true;
    }

    public static void setLocale(String lang, Context context, Activity activity) {

        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("lang", lang);
        editor.apply();

        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
