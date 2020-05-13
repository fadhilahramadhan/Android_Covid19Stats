package fadhilah.ramadhan.covid19stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class SysmptomsAdapter extends BaseAdapter {
    Context context;
    String[] list;
    LayoutInflater inflter;

    public SysmptomsAdapter(Context applicationContext, String[] list) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return list.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.layout_covid_sysmptoms, null);

        ImageView imageView  = (ImageView) view.findViewById(R.id.image);
        TextView title  = (TextView) view.findViewById(R.id.title);
        title.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));

        title.setText(list[i]);

        switch (i){
            case 0:
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.fever));
                break;
            case 1:
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.sore_throat));
                break;
            case 2:
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.headache));
                break;
            case 3:
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.breathing));
                break;
        }
        return view;
    }
}