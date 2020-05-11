package fadhilah.ramadhan.covid19stats.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.adapter.StatisticListAdapter;
import fadhilah.ramadhan.covid19stats.adapter.TopCountriesAdapter;
import fadhilah.ramadhan.covid19stats.base.BaseGlobalVar;
import fadhilah.ramadhan.covid19stats.model.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;
import fadhilah.ramadhan.covid19stats.util.Utility;
import fadhilah.ramadhan.covid19stats.util.service.AsyncTaskCompleteListener;
import fadhilah.ramadhan.covid19stats.util.service.CallService;

public class StatisticFragment extends BaseGlobalVar implements AsyncTaskCompleteListener {
    private TextView titleTopCountriesText, titleCovidGlobalText, titleStatisticText, activeCaseText, curesText, deathText;
    private RecyclerView topCountriesSlider;
    private FitChart globalDataStats;
    private DecimalFormat formatter = new DecimalFormat("#,###,###");
    private ListView statisticList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_statistic, container, false);

        titleTopCountriesText   = v.findViewById(R.id.titleTopCountriesText);
        titleCovidGlobalText    = v.findViewById(R.id.titleCovidGlobalText);
        titleStatisticText      = v.findViewById(R.id.titleStatisticText);
        topCountriesSlider      = v.findViewById(R.id.topCountriesSlider);
        activeCaseText          = v.findViewById(R.id.activeCaseText);
        curesText               = v.findViewById(R.id.curedText);
        deathText               = v.findViewById(R.id.deathText);
        globalDataStats         = v.findViewById(R.id.globalDataStats);
        statisticList           = v.findViewById(R.id.statisticList);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(topCountriesSlider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        topCountriesSlider.setLayoutManager(horizontalLayoutManager);

        if(GlobalVar.getInstance().getDataStatsSummary().size() != 0){
            topCountriesSlider.setAdapter(new TopCountriesAdapter(getActivity().getBaseContext(), (ArrayList<DataStats>)  getTop5(GlobalVar.getInstance().getDataStatsSummary())));
            buildStats(dataStatsGlobal);

            activeCaseText.setText(getString(R.string.label_activeCase) +"\n"+ formatter.format(dataStatsGlobal.getPostive()));
            curesText.setText(getString(R.string.label_cured) +"\n"+ formatter.format(dataStatsGlobal.getCured()));
            deathText.setText(getString(R.string.label_death) +"\n"+formatter.format(dataStatsGlobal.getDeath()));

            statisticList.setAdapter(new StatisticListAdapter(getContext(), dataStatsSummary));
        }else{
            CallService callService = new CallService(getContext(),this);
            callService.execute("summary", Constant.METHOD_GET);
        }

        setFont(v);
        return v;
    }

    public void setFont(View v){

        TextView titleCountryText   = v.findViewById(R.id.titleCountryText);
        TextView titlePositiveText  = v.findViewById(R.id.titlePositiveText);
        TextView titleDeathText     = v.findViewById(R.id.titleDeathText);
        TextView titleRecoveredText = v.findViewById(R.id.titleRecoveredText);

        titleCountryText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titleDeathText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titlePositiveText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titleRecoveredText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));

        titleTopCountriesText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titleStatisticText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titleCovidGlobalText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        activeCaseText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        curesText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        deathText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
    }


    public List<DataStats> getTop5(List<DataStats> dataStats){

        List<DataStats> top5 = new ArrayList<DataStats>();

        int max1 = 0 ,max2 = 0 ,max3 = 0,max4 = 0,max5= 0;
        int indexMax1 = 0 ,indexMax2 = 0 ,indexMax3 = 0,indexMax4 = 0,indexMax5= 0;

        for(int x = 0; x < dataStats.size();x++){
            if(dataStats.get(x).getPostive() > max1){
                max1 = dataStats.get(x).getPostive();
                indexMax1 = x;
            }
        }

        for(int x = 0; x < dataStats.size();x++){
            if(dataStats.get(x).getPostive() > max2 && dataStats.get(x).getPostive() < max1){
                max2 = dataStats.get(x).getPostive();
                indexMax2 = x;
            }
        }

        for(int x = 0; x < dataStats.size();x++){
            if(dataStats.get(x).getPostive() > max3 && dataStats.get(x).getPostive() < max2){
                max3 = dataStats.get(x).getPostive();
                indexMax3 = x;
            }
        }

        for(int x = 0; x < dataStats.size();x++){
            if(dataStats.get(x).getPostive() > max4 && dataStats.get(x).getPostive() < max3){
                max4 = dataStats.get(x).getPostive();
                indexMax4 = x;
            }
        }

        for(int x = 0; x < dataStats.size();x++){
            if(dataStats.get(x).getPostive() > max5 && dataStats.get(x).getPostive() < max4){
                max5 = dataStats.get(x).getPostive();
                indexMax5 = x;
            }
        }

        top5.add(dataStats.get(indexMax1));
        top5.add(dataStats.get(indexMax2));
        top5.add(dataStats.get(indexMax3));
        top5.add(dataStats.get(indexMax4));
        top5.add(dataStats.get(indexMax5));

        return top5;
    }

    public void buildStats( DataStats dataStats){
        float maxValue = (float) dataStats.getPostive() + dataStats.getCured() + dataStats.getDeath();
        globalDataStats.setMaxValue(maxValue);
        globalDataStats.setMinValue(0f);

        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float)dataStats.getPostive(), getResources().getColor(R.color.bg_Blue)));
        values.add(new FitChartValue((float)dataStats.getCured(), getResources().getColor(R.color.bg_Green)));
        values.add(new FitChartValue((float)dataStats.getDeath(), getResources().getColor(R.color.bg_Red)));
        globalDataStats.setValues(values);
        System.out.println("Masuk :"+maxValue);
    }
    
    @Override
    public void onTaskComplete(Object[] params) {
        String result = (String)params[0];
        try {
            JSONObject jsonObject = new JSONObject(result);

            String jsonCountries = jsonObject.getString("Countries");
            List<DataStats> dataStatsCountries = Utility.buildDataSummary(jsonCountries);

            String jsonGlobal = jsonObject.getString("Global");
            DataStats dataStatsGlobal =  Utility.buildDataGlobal(jsonGlobal);

            GlobalVar.getInstance().setDataStatsSummary(dataStatsCountries);
            GlobalVar.getInstance().setDataStatsGlobal(dataStatsGlobal);

            statisticList.setAdapter(new StatisticListAdapter(getContext(), dataStatsCountries));

            dataStatsCountries =  getTop5(dataStatsCountries);
            buildStats(dataStatsGlobal);

            activeCaseText.setText(getString(R.string.label_activeCase) +"\n"+ formatter.format(dataStatsGlobal.getPostive()));
            curesText.setText(getString(R.string.label_cured) +"\n"+ formatter.format(dataStatsGlobal.getCured()));
            deathText.setText(getString(R.string.label_death) +"\n"+formatter.format(dataStatsGlobal.getDeath()));

            topCountriesSlider.setAdapter(new TopCountriesAdapter(getActivity().getBaseContext(), (ArrayList<DataStats>) dataStatsCountries));
            ;

        }catch (JSONException e){
            Log.e("JSONException ee", e.getMessage());
        }

    }
}

