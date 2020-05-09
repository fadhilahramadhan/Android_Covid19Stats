package fadhilah.ramadhan.covid19stats.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fadhilah.ramadhan.covid19stats.R;

public class StatisticFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_statistic, container, false);
        TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText(getString(R.string.title_statistic));
        return root;
    }
}