package fadhilah.ramadhan.covid19stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class StatisticListAdapter extends BaseAdapter {
    private Context context;
    private List<DataStats> dataStats = new ArrayList<DataStats>();
    private DecimalFormat formatter = new DecimalFormat("#,###,###");

    public StatisticListAdapter(Context context, List<DataStats> dataStats){
        this.context = context;
        this.dataStats = dataStats;
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
}
