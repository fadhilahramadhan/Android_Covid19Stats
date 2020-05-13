package fadhilah.ramadhan.covid19stats.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;
import fadhilah.ramadhan.covid19stats.util.Utility;

public class ChangeLanguageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title, indonesiaText, englishText;
    private ImageView back, checkIndoneisa, checkEnglish;
    private LinearLayout layoutIndonesia, layoutEnglish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        title           = findViewById(R.id.title);
        back            = findViewById(R.id.back);
        checkIndoneisa  = findViewById(R.id.checkIndonesia);
        checkEnglish    = findViewById(R.id.checkEnglish);
        layoutIndonesia = findViewById(R.id.layoutIndonesia);
        layoutEnglish   = findViewById(R.id.layoutEnglish);
        indonesiaText   = findViewById(R.id.indonesiaText);
        englishText     = findViewById(R.id.englishText);

        title.setTypeface(FontUtils.loadFontFromAssets(this, Constant.FONT_BOLD));
        indonesiaText.setTypeface(FontUtils.loadFontFromAssets(this, Constant.FONT_BOLD));
        englishText.setTypeface(FontUtils.loadFontFromAssets(this, Constant.FONT_BOLD));

        back.setOnClickListener(this);
        layoutIndonesia.setOnClickListener(this);
        layoutEnglish.setOnClickListener(this);

        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        String lang= mSettings.getString("lang", "en");
        if(lang.equals(Constant.INDONESIA)){
            checkIndoneisa.setVisibility(View.VISIBLE);
        }else if(lang.equals(Constant.ENGLISH)){
            checkEnglish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back){
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            finish();
        }else if(v.getId() == R.id.layoutIndonesia){
            changeLanguage(Constant.INDONESIA);
        }else if(v.getId() == R.id.layoutEnglish){
            changeLanguage(Constant.ENGLISH);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    public void changeLanguage(String language){
        if(language == Constant.INDONESIA){
            checkIndoneisa.setVisibility(View.VISIBLE);
            checkEnglish.setVisibility(View.GONE);
        }else if(language == Constant.ENGLISH){
            checkIndoneisa.setVisibility(View.GONE);
            checkEnglish.setVisibility(View.VISIBLE);
        }
        Utility.setLocale(language, getBaseContext(), this);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }
}
