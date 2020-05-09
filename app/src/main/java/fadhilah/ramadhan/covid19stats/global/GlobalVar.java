package fadhilah.ramadhan.covid19stats.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fadhilah.ramadhan.covid19stats.model.DataStats;

public class GlobalVar {

    private static GlobalVar instance;
    private List<DataStats> DataStatsCountry =  new ArrayList<>();

    public List<DataStats> getDataStatsCountry() {
        return DataStatsCountry;
    }

    public void setDataStatsCountry(List<DataStats> dataStatsCountry) {
        DataStatsCountry = dataStatsCountry;
    }

    static {
        instance = new GlobalVar();
    }

    public static GlobalVar getInstance() {
        return instance;
    }

    public static void setInstance(GlobalVar instance) {
        GlobalVar.instance = instance;
    }
}
