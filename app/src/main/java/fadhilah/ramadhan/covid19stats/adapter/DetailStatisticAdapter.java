package fadhilah.ramadhan.covid19stats.adapter;


import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;
import fadhilah.ramadhan.covid19stats.util.Utility;

public class DetailStatisticAdapter extends BaseAdapter {
    private List<DataStats> dataStats;
    private Context context;
    private TextView dateHeader, titleActiveCaseText, titlePositiveText, titleRecoveredText, titleDeathText, activeCaseText, positiveText, recoveredText, deathText;
    private FitChart detailDataStats;
    private LinearLayout dateLayout;
    private List<Boolean> dateShow = new ArrayList<Boolean>();
    private TextView postiveUpDown, activeUpDown, recoveredUpDown, deathUpDown;
    private ImageView postiveUpDownImg, activeUpDownImg, recoveredUpDownImg, deathUpDownImg;
    private LinearLayout postiveUpDownLayout, activeUpDownLayout, recoveredUpDownLayout, deathUpDownLayout;

    public DetailStatisticAdapter(List<DataStats> dataStats, Context context) {
        this.dataStats = descending(dataStats);
        this.context = context;
    }

    public List<DataStats> descending(List<DataStats> dataStats){
        List<DataStats> data = new ArrayList<>();
        for(int x = dataStats.size()-1; x >= 0; x--){
            data.add(dataStats.get(x));
        }
        return data;
    }

    @Override
    public int getCount() {
        return dataStats.size();
    }

    @Override
    public DataStats getItem(int position) {
        return dataStats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = LayoutInflater.from(context).inflate(R.layout.list_row_detail_statistic,null);

        dateHeader          = v.findViewById(R.id.dateHeader);
        titleActiveCaseText = v.findViewById(R.id.titleActiveCaseText);
        titlePositiveText   = v.findViewById(R.id.titlePositiveText);
        titleRecoveredText  = v.findViewById(R.id.titleRecoveredText);
        titleDeathText      = v.findViewById(R.id.titleDeathText);
        activeCaseText      = v.findViewById(R.id.activeCaseText);
        positiveText        = v.findViewById(R.id.positiveText);
        recoveredText       = v.findViewById(R.id.recoveredText);
        deathText           = v.findViewById(R.id.deathText);
        dateLayout          = v.findViewById(R.id.dateLayout);
        detailDataStats     = v.findViewById(R.id.detailDataStats);

        postiveUpDown       = v.findViewById(R.id.postiveUpDown);
        activeUpDown        = v.findViewById(R.id.activeUpDown);
        recoveredUpDown     = v.findViewById(R.id.recoveredUpDown);
        deathUpDown         = v.findViewById(R.id.deathUpDown);
        postiveUpDownImg    = v.findViewById(R.id.postiveUpDownImg);
        activeUpDownImg     = v.findViewById(R.id.activeUpDownImg);
        recoveredUpDownImg  = v.findViewById(R.id.recoveredUpDownImg);
        deathUpDownImg      = v.findViewById(R.id.deathUpDownImg);
        postiveUpDownLayout       = v.findViewById(R.id.positiveUpDownLayout);
        activeUpDownLayout        = v.findViewById(R.id.activeUpDownLayout);
        recoveredUpDownLayout     = v.findViewById(R.id.recoveredUpDownLayout);
        deathUpDownLayout         = v.findViewById(R.id.deathUpDownLayout);

        positiveText.setText(String.valueOf(getItem(position).getPostive()));
        activeCaseText.setText(String.valueOf(getItem(position).getActiveCases()));
        recoveredText.setText(String.valueOf(getItem(position).getCured()));
        deathText.setText(String.valueOf(getItem(position).getDeath()));

        buildStats(position);

        dateHeader.setText(Utility.dateFormat(Constant.SIMPLE_DATE, getItem(position).getDate()));

        if(position < getCount()-1){
            if(getItem(position).getPostive() != getItem(position+1).getPostive()){
                int total =   getItem(position).getPostive()- getItem(position+1).getPostive();
                postiveUpDown.setText(String.valueOf(total));
                postiveUpDownLayout.setVisibility(View.VISIBLE);
            }
            if(getItem(position).getActiveCases() != getItem(position+1).getActiveCases()){
                int total =   getItem(position).getActiveCases()-getItem(position+1).getActiveCases() ;
                if(total < 0){
                    total =   getItem(position+1).getActiveCases() - getItem(position).getActiveCases();
                }
                activeUpDown.setText(String.valueOf(total));
                activeUpDownLayout.setVisibility(View.VISIBLE);
            }
            if(getItem(position).getCured() != getItem(position+1).getCured()){
                int total =  getItem(position).getCured()- getItem(position+1).getCured();
                recoveredUpDown.setText(String.valueOf(total));
                recoveredUpDownLayout.setVisibility(View.VISIBLE);
            }
            if(getItem(position).getDeath() != getItem(position+1).getDeath()){
                int total =   getItem(position).getDeath()-getItem(position+1).getDeath();
                deathUpDown.setText(String.valueOf(total));
                deathUpDownLayout.setVisibility(View.VISIBLE);
            }
        }


        setFont();
        return v;
    }
    
    private void setFont(){
        titleDeathText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
        titleRecoveredText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
        titlePositiveText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
        titleActiveCaseText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
        activeCaseText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        positiveText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        recoveredText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        deathText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        postiveUpDown.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        activeUpDown.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        recoveredUpDown.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        deathUpDown.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
    }

    public void buildStats(int position){
        Float maxValue = (float) getItem(position).getPostive() + getItem(position).getActiveCases() + getItem(position).getCured() +getItem(position).getDeath();
        detailDataStats.setMaxValue(maxValue);
        detailDataStats.setMinValue(0f);

        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float)getItem(position).getPostive(), context.getResources().getColor(R.color.colorTextDark)));
        values.add(new FitChartValue((float)getItem(position).getActiveCases(), context.getResources().getColor(R.color.bg_Blue)));
        values.add(new FitChartValue((float)getItem(position).getCured(), context.getResources().getColor(R.color.bg_Green)));
        values.add(new FitChartValue((float)getItem(position).getDeath(), context.getResources().getColor(R.color.bg_Red)));
        detailDataStats.setValues(values);
    }
}
