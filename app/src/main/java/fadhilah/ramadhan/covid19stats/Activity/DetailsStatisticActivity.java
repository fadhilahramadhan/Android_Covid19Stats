package fadhilah.ramadhan.covid19stats.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.adapter.DetailStatisticAdapter;
import fadhilah.ramadhan.covid19stats.model.DataStats;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class DetailsStatisticActivity extends AppCompatActivity implements View.OnClickListener {
    private List<DataStats> dataStats;
    private ListView listDetail;
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_statistic);

        listDetail  = findViewById(R.id.listDetail);
        title       = findViewById(R.id.title);
        back        = findViewById(R.id.back);

        back.setOnClickListener(this);

        listDetail.setDivider(null);
        listDetail.setDividerHeight(0);

        dataStats = getIntent().getParcelableArrayListExtra("dataStats");

        title.setTypeface(FontUtils.loadFontFromAssets(this, Constant.FONT_BOLD));
        title.setText(getIntent().getStringExtra(("country")));

        listDetail.setAdapter(new DetailStatisticAdapter(dataStats,this));

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back){
            finish();
        }
    }
}
