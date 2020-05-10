package fadhilah.ramadhan.covid19stats.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderAdapter;
import com.github.islamkhsh.CardSliderViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.Utility;
import fadhilah.ramadhan.covid19stats.util.service.AsyncTaskCompleteListener;
import fadhilah.ramadhan.covid19stats.util.service.CallService;

public class StatisticFragment extends Fragment implements AsyncTaskCompleteListener {
    private TextView topCountriesText;
    private RecyclerView topCountriesSlider;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_statistic, container, false);

        topCountriesText    = v.findViewById(R.id.textTopCountries);
        topCountriesSlider  = v.findViewById(R.id.topCountriesSlider);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(topCountriesSlider.getContext(), LinearLayoutManager.HORIZONTAL, false);
        topCountriesSlider.setLayoutManager(horizontalLayoutManager);

        CallService callService = new CallService(getContext(),this);
        callService.execute("summary", Constant.METHOD_GET);

        return v;
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
        String result = (String)params[0];
        try {
            JSONObject jsonObject = new JSONObject(result);
            String json = jsonObject.getString("Countries");
            List<DataStats>  dataStats = Utility.buildDataSummary(json);
            dataStats =  getTop5(dataStats);

            topCountriesSlider.setAdapter(new TopCountryAdapter(getActivity().getBaseContext(), (ArrayList<DataStats>) dataStats));
        }catch (JSONException e){
            Log.e("JSONException ee", e.getMessage());
        }

    }

    public class TopCountryAdapter extends RecyclerView.Adapter<TopCountryAdapter.ViewHolder> {

        private ArrayList<DataStats> dataStats;
        private LayoutInflater mInflater;
        private TextView countryNameText,positiveCovidText,deathTotalText,curedTotalText;
        private LinearLayout layout;

        TopCountryAdapter(Context context, ArrayList<DataStats> dataStats) {
            this.mInflater = LayoutInflater.from(context);
            this.dataStats = dataStats;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.layout_top_countries, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            dataStats.get(position);

            DecimalFormat formatter = new DecimalFormat("#,###,###");

            countryNameText.setText(dataStats.get(position).getCountry().toString());
            positiveCovidText.setText(String.valueOf(formatter.format(dataStats.get(position).getPostive())));
            deathTotalText.setText(String.valueOf(formatter.format(dataStats.get(position).getDeath())));
            curedTotalText.setText(String.valueOf(formatter.format(dataStats.get(position).getCured())));

            if(position == 0 || position == 3){
                layout.setBackgroundColor(getResources().getColor(R.color.bg_Green));
            }else if(position == 1 || position == 4){
                layout.setBackgroundColor(getResources().getColor(R.color.bg_Blue));
            }else if(position == 2){
                layout.setBackgroundColor(getResources().getColor(R.color.bg_Red));
            }
        }

        @Override
        public int getItemCount() {
            return dataStats.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View myView;
            TextView myTextView;

            ViewHolder(View view) {
                super(view);
                countryNameText    = view.findViewById(R.id.countryNameText);
                positiveCovidText  = view.findViewById(R.id.postiveCovidText);
                deathTotalText     = view.findViewById(R.id.deathTotalText);
                curedTotalText     = view.findViewById(R.id.CuredTotalText);
                layout             = view.findViewById(R.id.layoutTopCountries);
            }

        }

        public DataStats getItem(int id) {
            return dataStats.get(id);
        }

    }
}

