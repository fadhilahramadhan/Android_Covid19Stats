package fadhilah.ramadhan.covid19stats.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderAdapter;
import com.github.islamkhsh.CardSliderViewPager;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fadhilah.ramadhan.covid19stats.Activity.ChangeLanguageActivity;
import fadhilah.ramadhan.covid19stats.Activity.DetailsStatisticActivity;
import fadhilah.ramadhan.covid19stats.Activity.MainActivity;
import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.adapter.PreventCovidAdapter;
import fadhilah.ramadhan.covid19stats.adapter.SysmptomsAdapter;
import fadhilah.ramadhan.covid19stats.base.BaseGlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;
import fadhilah.ramadhan.covid19stats.util.Utility;

public class HomeFragment extends BaseGlobalVar implements View.OnClickListener {
    private TextView titleStatText,titleSymptoms, titlePreventCovid, activeCaseText, curesText, deathText, dateStatsText,moreDetail, modeChangeText;
    private DataStats dataStats;
    private FitChart homeDataStats;
    private CardSliderViewPager preventCovidSlider;
    private GridView symptomsGrid;
    private ScrollView scrollView;
    private DecimalFormat formatter = new DecimalFormat("#,###,###");
    private ImageView changeLanguage;
    private SwitchCompat modeChange;
    private SharedPreferences.Editor editor;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        titleStatText       = v.findViewById(R.id.titleStatsText);
        titleSymptoms       = v.findViewById(R.id.titleSymptoms);
        titlePreventCovid   = v.findViewById(R.id.titlePreventCovid);
        activeCaseText      = v.findViewById(R.id.activeCaseText);
        curesText           = v.findViewById(R.id.curedText);
        deathText           = v.findViewById(R.id.deathText);
        homeDataStats       = v.findViewById(R.id.homeDataStats);
        dateStatsText       = v.findViewById(R.id.dateStatsText);
        moreDetail          = v.findViewById(R.id.moreDetail);
        scrollView          = v.findViewById(R.id.scroll);
        changeLanguage      = v.findViewById(R.id.changeLanguage);
        modeChange          = v.findViewById(R.id.modeChange);
        modeChangeText      = v.findViewById(R.id.modeChangeText);

        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = mSettings.edit();
        boolean darkMode = mSettings.getBoolean("darkMode", false);

        if(darkMode){
            modeChange.setChecked(true);

        }

        dataStats = dataStatsCountry.get(dataStatsCountry.size() - 1);

        titleStatText.append(" Indonesia");

        activeCaseText.setText(getString(R.string.label_positive) +"\n"+formatter.format(dataStats.getPostive()));
        curesText.setText(getString(R.string.label_cured) +"\n"+ formatter.format(dataStats.getCured()));
        deathText.setText(getString(R.string.label_death) +"\n"+formatter.format(dataStats.getDeath()));
        dateStatsText.setText(Utility.dateFormat(Constant.SIMPLE_DATE, dataStats.getDate()));

        buildStats((float) dataStats.getPostive() + dataStats.getCured() + dataStats.getDeath());

        symptomsGrid = v.findViewById(R.id.symptomsGrid);
        SysmptomsAdapter customAdapter = new SysmptomsAdapter(getContext(), getResources().getStringArray(R.array.array_covidSysmptoms));
        symptomsGrid.setAdapter(customAdapter);

        preventCovidSlider = v.findViewById(R.id.preventCovidSlider);
        preventCovidSlider.setAdapter(new PreventCovidAdapter(getContext(), getResources().getStringArray(R.array.array_preventCovid)));

        moreDetail.setOnClickListener(this);
        changeLanguage.setOnClickListener(this);
        modeChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("darkMode", true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    editor.putBoolean("darkMode", false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                editor.apply();
                getActivity().recreate();
            }
        });

        scrollView.smoothScrollTo(0,0);
        setFont();
        return v;
    }

    public void setFont(){
        titleStatText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titleSymptoms.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        titlePreventCovid.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_BOLD));
        activeCaseText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        curesText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        deathText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        dateStatsText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
        modeChangeText.setTypeface(FontUtils.loadFontFromAssets(getContext(), Constant.FONT_NORMAL));
    }

    public void buildStats(Float maxValue){
        homeDataStats.setMaxValue(maxValue);
        homeDataStats.setMinValue(0f);

        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float)dataStats.getPostive(), getResources().getColor(R.color.bg_Blue)));
        values.add(new FitChartValue((float)dataStats.getCured(), getResources().getColor(R.color.bg_Green)));
        values.add(new FitChartValue((float)dataStats.getDeath(), getResources().getColor(R.color.bg_Red)));
        homeDataStats.setValues(values);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.moreDetail){
            Intent intent = new Intent(getActivity(), DetailsStatisticActivity.class);
            intent.putParcelableArrayListExtra("dataStats", (ArrayList<? extends DataStats>) dataStatsCountry);
            intent.putExtra("country", dataStats.getCountry());
            startActivityForResult(intent,1);
        }else if(v.getId() == R.id.changeLanguage){
            Intent intent = new Intent(getActivity(), ChangeLanguageActivity.class);
            getActivity().finish();
            startActivity(intent);
        }
    }
}