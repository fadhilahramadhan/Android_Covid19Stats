package fadhilah.ramadhan.covid19stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.component.ProgresDialog;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.model.GlobalVar;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class StatisticListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<DataStats> dataStats,dataStatsSearch = new ArrayList<>();
    private DecimalFormat formatter = new DecimalFormat("#,###,###");

    public StatisticListAdapter(Context context,List<DataStats> dataStats){
        this.context = context;
        this.dataStats = dataStats;
    }

    public void loadMore(ProgresDialog loading){
        this.dataStatsSearch.addAll(dataStats);
        loading.dismiss();

    }

    @Override
    public int getCount() {
        return  dataStatsSearch.size();
    }


    @Override
    public DataStats getItem(int position) {
        DataStats dataStats = new DataStats();
        if(dataStatsSearch.size() > position){
            dataStats = dataStatsSearch.get(position);
        }
        return dataStats;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = LayoutInflater.from(context).inflate(R.layout.list_row_statistic,null);

        TextView countryText        = v.findViewById(R.id.countryText);
        TextView deathText          = v.findViewById(R.id.deathText);
        TextView positiveText       = v.findViewById(R.id.positiveText);
        TextView recoveredText      = v.findViewById(R.id.recoveredText);

        countryText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        deathText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        positiveText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        recoveredText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));

        countryText.setText(getItem(position).getCountry());
        deathText.setText(formatter.format(getItem(position).getDeath()));
        positiveText.setText(formatter.format(getItem(position).getPostive()));
        recoveredText.setText(formatter.format(getItem(position).getCured()));

        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                dataStatsSearch = (ArrayList<DataStats>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<DataStats> FilteredArrList = new ArrayList<DataStats>();

                if (dataStats == null) {
                    dataStats = new ArrayList<DataStats>(dataStatsSearch);
                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = dataStats.size();
                    results.values = dataStats;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < dataStats.size(); i++) {
                        String data = dataStats.get(i).getCountry();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new DataStats(dataStats.get(i)));
                        }
                    }

                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
