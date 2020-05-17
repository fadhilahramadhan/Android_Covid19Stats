package fadhilah.ramadhan.covid19stats.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import fadhilah.ramadhan.covid19stats.Activity.DetailsStatisticActivity;
import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.adapter.StatisticListAdapter;
import fadhilah.ramadhan.covid19stats.adapter.TopCountriesAdapter;
import fadhilah.ramadhan.covid19stats.base.BaseGlobalVar;
import fadhilah.ramadhan.covid19stats.component.ProgresDialog;
import fadhilah.ramadhan.covid19stats.model.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;
import fadhilah.ramadhan.covid19stats.util.Utility;
import fadhilah.ramadhan.covid19stats.util.service.AsyncTaskCompleteListener;
import fadhilah.ramadhan.covid19stats.util.service.CallService;

public class StatisticFragment extends BaseGlobalVar implements  AsyncTaskCompleteListener, AdapterView.OnItemClickListener  {
    private TextView titleTopCountriesText, titleCovidGlobalText, titleStatisticText, activeCaseText, curesText, deathText, dateText;
    private RecyclerView topCountriesSlider;
    private FitChart globalDataStats;
    private ListView statisticList;
    private int requestTask;
    private EditText search;
    private StatisticListAdapter adapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout layoutTop;
    private RelativeLayout statsitiFragmentLayout;
    private String result;
    private ProgresDialog loading;

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
        dateText                = v.findViewById(R.id.dateText);
        search                  = v.findViewById(R.id.search);
        layoutTop               = v.findViewById(R.id.layoutTop);
        statsitiFragmentLayout  = v.findViewById(R.id.statsitiFragmentLayout);

        RelativeLayout bottomSheetLayout
                = (RelativeLayout) v.findViewById(R.id.layoutStatisticGlobal);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(topCountriesSlider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        topCountriesSlider.setLayoutManager(horizontalLayoutManager);

        if(GlobalVar.getInstance().getDataStatsSummary().size() != 0){
            LoadingStatisticFragment loading = new LoadingStatisticFragment();
            loading.execute(null, dataStatsGlobal, dataStatsSummary);
            bottomSheetBehavior.setPeekHeight(layoutStatisticHeight);
        }else{
            requestTask = 1;
            CallService callService = new CallService(getContext(),this);
            callService.execute("summary", Constant.METHOD_GET);
        }
        statisticList.setTextFilterEnabled(true);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        layoutTop.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                int height = (int) statsitiFragmentLayout.getHeight() - (int)layoutTop.getHeight();
                GlobalVar.getInstance().setLayoutStatisticHeight(height);
                bottomSheetBehavior.setPeekHeight(height);
            }
        });


        statisticList.setOnItemClickListener(this);
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
        dateText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));

    }

    public void buildStats( DataStats dataStats){
        float maxValue = (float) dataStats.getPostive() + dataStats.getCured() + dataStats.getDeath();
        globalDataStats.setMaxValue(maxValue);
        globalDataStats.setMinValue(0f);

        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float)dataStats.getPostive(), getContext().getResources().getColor(R.color.bg_Blue)));
        values.add(new FitChartValue((float)dataStats.getCured(), getContext().getResources().getColor(R.color.bg_Green)));
        values.add(new FitChartValue((float)dataStats.getDeath(), getContext().getResources().getColor(R.color.bg_Red)));
        globalDataStats.setValues(values);
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

    @Override
    public void onTaskComplete(Object[] params) {

           result = (String) params[0];
            if(Utility.cekValidResult(result, getActivity())){
                if(requestTask == 1){
                    if(result.equals("You have reached maximum request limit.")){
                        requestTask = 1;
                        CallService callService = new CallService(getContext(),this);
                        callService.execute("summary", Constant.METHOD_GET);
                    }else{
                        LoadingStatisticFragment loading = new LoadingStatisticFragment();
                        loading.execute(result);
                    }
                }else if(requestTask == 2){
                    List<DataStats> dataStats = Utility.buildDataStats(result);
                    Intent intent = new Intent(getActivity(), DetailsStatisticActivity.class);
                    intent.putParcelableArrayListExtra("dataStats", (ArrayList<? extends DataStats>) dataStats);
                    intent.putExtra("country", dataStats.get(0).getCountry());
                    startActivityForResult(intent,1);
                }
            }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DataStats dataStats = new DataStats();
            dataStats = (DataStats) statisticList.getItemAtPosition(position);

            requestTask = 2;
            CallService callService = new CallService(getContext(),this);
        callService.execute("dayone/country/"+dataStats.getCountryCode(), Constant.METHOD_GET);
    }

    public class LoadingStatisticFragment  extends AsyncTask<Object, List<DataStats>, List<DataStats>> {

        private boolean withLoading = true;

        private List<DataStats> dataStatsCountries;
        private DataStats dataStatsGlobal;
        private DecimalFormat formatter = new DecimalFormat("#,###,###");


        @Override
        protected void onPreExecute() {
            if(withLoading){
                loading = new ProgresDialog(getContext());
                loading.show();
            }
        }

        @Override
        protected List<DataStats> doInBackground(Object... params) {

            String result = (String) params[0];
            if(result != null){
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonCountries = jsonObject.getString("Countries");
                    dataStatsCountries = Utility.buildDataSummary(jsonCountries);

                    String jsonGlobal = jsonObject.getString("Global");
                    dataStatsGlobal =  Utility.buildDataGlobal(jsonGlobal);
                }catch (JSONException e){
                    Log.e("JSONException", e.getMessage());
                }
            }else{
                dataStatsGlobal     = (DataStats) params[1];
                dataStatsCountries  = (List<DataStats>) params[2];
            }


            GlobalVar.getInstance().setDataStatsSummary(dataStatsCountries);
            GlobalVar.getInstance().setDataStatsGlobal(dataStatsGlobal);


            return dataStatsCountries;
        }


        @Override
        protected void onPostExecute(List<DataStats> result) {

            activeCaseText.setText( getContext().getString(R.string.label_positive) +"\n"+ formatter.format(dataStatsGlobal.getPostive()));
            curesText.setText( getContext().getString(R.string.label_cured) +"\n"+ formatter.format(dataStatsGlobal.getCured()));
            deathText.setText( getContext().getString(R.string.label_death) +"\n"+formatter.format(dataStatsGlobal.getDeath()));

            dateText.setText(Utility.dateFormat(Constant.SIMPLE_DATE, dataStatsCountries.get(0).getDate()));
            buildStats(dataStatsGlobal);
            List<DataStats> top5 = getTop5(result);
            topCountriesSlider.setAdapter(new TopCountriesAdapter(getContext(), (ArrayList<DataStats>) top5));

            loadList(result);
        }



    }


    public void loadList(List<DataStats> dataStatsCountries){
        adapter = new StatisticListAdapter(getContext(), dataStatsCountries);
        statisticList.setAdapter(adapter);
        adapter.loadMore(loading);

    }


}

