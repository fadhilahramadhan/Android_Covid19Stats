package fadhilah.ramadhan.covid19stats.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.model.DataStats;

public class DetailsStatisticActivity extends AppCompatActivity {
    private List<DataStats> dataStats;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_detail_statistic);

        dataStats = getIntent().getParcelableExtra("dataStats");


    }

}
