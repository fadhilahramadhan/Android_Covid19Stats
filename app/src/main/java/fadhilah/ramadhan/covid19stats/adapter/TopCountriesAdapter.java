package fadhilah.ramadhan.covid19stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class TopCountriesAdapter extends RecyclerView.Adapter<TopCountriesAdapter.ViewHolder> {

    private ArrayList<DataStats> dataStats;
    private LayoutInflater mInflater;
    private TextView countryNameText,positiveCovidText,deathTotalText,curedTotalText, deathText, curedText;
    private LinearLayout layout;
    private DecimalFormat formatter = new DecimalFormat("#,###,###");
    private Context context;

    public TopCountriesAdapter(Context context, ArrayList<DataStats> dataStats) {
        this.mInflater = LayoutInflater.from(context);
        this.dataStats = dataStats;
        this.context = context;
    }

    @Override
    @NonNull
    public TopCountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_top_countries, parent, false);
        return new TopCountriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCountriesAdapter.ViewHolder holder, int position) {
        dataStats.get(position);

        countryNameText.setText(dataStats.get(position).getCountry());
        positiveCovidText.setText(formatter.format(dataStats.get(position).getPostive()));
        deathTotalText.setText(formatter.format(dataStats.get(position).getDeath()));
        curedTotalText.setText(formatter.format(dataStats.get(position).getCured()));

        if(position == 0 || position == 3){
            layout.setBackgroundColor(context.getResources().getColor(R.color.bg_Green));
        }else if(position == 1 || position == 4){
            layout.setBackgroundColor(context.getResources().getColor(R.color.bg_Blue));
        }else if(position == 2){
            layout.setBackgroundColor(context.getResources().getColor(R.color.bg_Red));
        }
    }

    @Override
    public int getItemCount() {
        return dataStats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            countryNameText    = view.findViewById(R.id.countryNameText);
            positiveCovidText  = view.findViewById(R.id.postiveCovidText);
            deathTotalText     = view.findViewById(R.id.deathTotalText);
            curedTotalText     = view.findViewById(R.id.CuredTotalText);
            curedText          = view.findViewById(R.id.curedText);
            deathText          = view.findViewById(R.id.deathText);
            layout             = view.findViewById(R.id.layoutTopCountries);

            countryNameText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
            positiveCovidText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
            deathTotalText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
            curedTotalText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
            curedText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
            deathText.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_NORMAL));
        }

    }

    public DataStats getItem(int id) {
        return dataStats.get(id);
    }

}
