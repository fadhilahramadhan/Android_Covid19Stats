package fadhilah.ramadhan.covid19stats.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import fadhilah.ramadhan.covid19stats.global.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;

public class BaseGlobalVar extends Fragment {

    public List<DataStats> dataStatsCountry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataStatsCountry = GlobalVar.getInstance().getDataStatsCountry();
    }


}
