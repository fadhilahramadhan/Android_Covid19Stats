package fadhilah.ramadhan.covid19stats.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.model.GlobalVar;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.Utility;
import fadhilah.ramadhan.covid19stats.util.service.AsyncTaskCompleteListener;
import fadhilah.ramadhan.covid19stats.util.service.CallService;

public class WelcomeScreenActivity extends AppCompatActivity implements AsyncTaskCompleteListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        CallService callService = new CallService(this,this, Constant.SERVICE_NO_LOADING);
        callService.execute("dayone/country/indonesia", Constant.METHOD_GET);
    }

    @Override
    public void onTaskComplete(Object[] params) {
        String result = (String) params[0];
        if(Utility.cekValidResult(result, this)){
            GlobalVar.getInstance().setDataStatsCountry(Utility.buildDataStats(result));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
