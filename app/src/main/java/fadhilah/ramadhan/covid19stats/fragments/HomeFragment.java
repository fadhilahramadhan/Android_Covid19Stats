package fadhilah.ramadhan.covid19stats.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.service.AsyncTaskCompleteListener;
import fadhilah.ramadhan.covid19stats.util.service.CallService;

public class HomeFragment extends Fragment implements AsyncTaskCompleteListener {
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        textView.setText(getString(R.string.title_home));
        getDataSummary();
        return root;
    }

    public void getDataSummary(){
        CallService callService = new CallService(getContext(),this);
        callService.execute("summary", Constant.METHOD_GET);
    }

    @Override
    public void onTaskComplete(Object[] params) {
        String result = (String) params[0];
        textView.setText(result);
    }
}