package fadhilah.ramadhan.covid19stats.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fadhilah.ramadhan.covid19stats.global.GlobalVar;
import fadhilah.ramadhan.covid19stats.model.DataStats;

public class BaseGlobalVar extends AppCompatActivity {

    private List<DataStats> dataStatsCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataStatsCountry = GlobalVar.getInstance().getDataStatsCountry();
    }
}
