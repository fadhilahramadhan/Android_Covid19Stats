package fadhilah.ramadhan.covid19stats.model;

import java.util.ArrayList;
import java.util.List;

public class GlobalVar {

    private static GlobalVar instance;
    private List<DataStats> DataStatsCountry = new ArrayList<>();
    private List<DataStats> DataStatsSummary = new ArrayList<>();
    private DataStats DataStatsGlobal = new DataStats();
    private int layoutStatisticHeight;

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

    public List<DataStats> getDataStatsSummary() {
        return DataStatsSummary;
    }

    public void setDataStatsSummary(List<DataStats> dataStatsSummary) {
        DataStatsSummary = dataStatsSummary;
    }

    public DataStats getDataStatsGlobal() {
        return DataStatsGlobal;
    }

    public void setDataStatsGlobal(DataStats dataStatsGlobal) {
        DataStatsGlobal = dataStatsGlobal;
    }

    public int getLayoutStatisticHeight() {
        return layoutStatisticHeight;
    }

    public void setLayoutStatisticHeight(int layoutStatisticHeight) {
        this.layoutStatisticHeight = layoutStatisticHeight;
    }
}
