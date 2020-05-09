package fadhilah.ramadhan.covid19stats.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.base.BaseGlobalVar;
import fadhilah.ramadhan.covid19stats.global.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.service.AsyncTaskCompleteListener;
import fadhilah.ramadhan.covid19stats.util.service.CallService;

public class HomeFragment extends BaseGlobalVar {
    private TextView textTitleStat, textActiveCase, textCures, textDeath;
    private DataStats dataStats;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        textTitleStat   = v.findViewById(R.id.textTitleStats);
        textActiveCase  = v.findViewById(R.id.textActiveCase);
        textCures       = v.findViewById(R.id.textCured);
        textDeath       = v.findViewById(R.id.textDeath);

        dataStats = dataStatsCountry.get(dataStatsCountry.size() - 1);

        textTitleStat.append("Indonesia");

        textActiveCase.setText(getString(R.string.label_activeCase) +"\n"+ String.valueOf(dataStats.getActiveCases()));
        textCures.setText(getString(R.string.label_cured) +"\n"+ String.valueOf(dataStats.getCured()));
        textDeath.setText(getString(R.string.label_death) +"\n"+String.valueOf(dataStats.getDeath()));

        return v;
    }



}