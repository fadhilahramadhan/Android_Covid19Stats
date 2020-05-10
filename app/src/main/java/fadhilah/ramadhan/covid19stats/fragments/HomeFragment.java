package fadhilah.ramadhan.covid19stats.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.Collection;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.base.BaseGlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class HomeFragment extends BaseGlobalVar {
    private TextView titleStatText, activeCaseText, curesText, deathText;
    private DataStats dataStats;
    private FitChart homeDataStats;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);


        titleStatText   = v.findViewById(R.id.titleStatsText);
        activeCaseText  = v.findViewById(R.id.activeCaseText);
        curesText       = v.findViewById(R.id.curedText);
        deathText       = v.findViewById(R.id.deathText);
        homeDataStats   = v.findViewById(R.id.homeDataStats);

        dataStats = dataStatsCountry.get(dataStatsCountry.size() - 1);

        titleStatText.append(" Indonesia");

        activeCaseText.setText(getString(R.string.label_activeCase) +"\n"+ String.valueOf(dataStats.getActiveCases()));
        curesText.setText(getString(R.string.label_cured) +"\n"+ String.valueOf(dataStats.getCured()));
        deathText.setText(getString(R.string.label_death) +"\n"+String.valueOf(dataStats.getDeath()));

        buildStats((float) dataStats.getActiveCases() + dataStats.getCured() + dataStats.getDeath());

        setFont();
        return v;
    }

    public void setFont(){
        titleStatText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        activeCaseText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        curesText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        deathText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
    }

    public void buildStats(Float maxValue){
        homeDataStats.setMaxValue(maxValue);
        homeDataStats.setMinValue(0f);

        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float)dataStats.getActiveCases(), getResources().getColor(R.color.bg_Blue)));
        values.add(new FitChartValue((float)dataStats.getCured(), getResources().getColor(R.color.bg_Green)));
        values.add(new FitChartValue((float)dataStats.getDeath(), getResources().getColor(R.color.bg_Red)));
        homeDataStats.setValues(values);
    }


}