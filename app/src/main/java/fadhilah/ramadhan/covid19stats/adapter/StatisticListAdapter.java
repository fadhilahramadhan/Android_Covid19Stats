package fadhilah.ramadhan.covid19stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.component.ProgresDialog;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class StatisticListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<DataStats> dataStats,dataStatsSearch = new ArrayList<>();
    private DecimalFormat formatter = new DecimalFormat("#,###,###");

    public StatisticListAdapter(Context context,List<DataStats> dataStats){
        this.context = context;
        this.dataStats = dataStats;
        this.dataStatsSearch.add(dataStats.get(0));
    }

    public void loadMore(ProgresDialog loading){
        this.dataStatsSearch.addAll(dataStats);
        loading.dismiss();
    }

    @Override
    public int getCount() {
        return  dataStatsSearch.size();
    }

    public int getSize(){
        return  dataStats.size();
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

                dataStatsSearch = (ArrayList<DataStats>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<DataStats> FilteredArrList = new ArrayList<DataStats>();

                if (dataStats == null) {
                    dataStats = new ArrayList<DataStats>(dataStatsSearch); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = dataStats.size();
                    results.values = dataStats;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < dataStats.size(); i++) {
                        String data = dataStats.get(i).getCountry();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new DataStats(dataStats.get(i).getCountry(), dataStats.get(i).getPostive(), dataStats.get(i).getCured(), dataStats.get(i).getDeath()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
