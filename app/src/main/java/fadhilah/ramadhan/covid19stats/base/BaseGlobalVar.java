package fadhilah.ramadhan.covid19stats.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import fadhilah.ramadhan.covid19stats.model.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;

public class BaseGlobalVar extends Fragment {

    public List<DataStats> dataStatsCountry,dataStatsSummary;
    public DataStats dataStatsGlobal;
    public int layoutStatisticHeight =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataStatsCountry = GlobalVar.getInstance().getDataStatsCountry();
        dataStatsSummary = GlobalVar.getInstance().getDataStatsSummary();
        dataStatsGlobal   = GlobalVar.getInstance().getDataStatsGlobal();
        layoutStatisticHeight = GlobalVar.getInstance().getLayoutStatisticHeight();
    }


}
