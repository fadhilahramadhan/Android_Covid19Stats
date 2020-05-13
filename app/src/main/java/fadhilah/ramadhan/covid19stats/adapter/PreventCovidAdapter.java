package fadhilah.ramadhan.covid19stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderAdapter;

import fadhilah.ramadhan.covid19stats.R;
import fadhilah.ramadhan.covid19stats.util.Constant;
import fadhilah.ramadhan.covid19stats.util.FontUtils;

public class PreventCovidAdapter extends CardSliderAdapter<PreventCovidAdapter.PrevetCovidViewHolder> {
    private String arrayList[];
    private ImageView imageView;
    private TextView title;
    private Context context;

    public PreventCovidAdapter(Context context, String arrayList[]){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PrevetCovidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_prevent_covid, parent, false);

        imageView   = view.findViewById(R.id.image);
        title       = view.findViewById(R.id.title);
        title.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));

        return new PrevetCovidViewHolder(view);
    }

    @Override
    public int getItemCount(){
        return arrayList.length;
    }


    @Override
    public void bindVH(PrevetCovidViewHolder prevetCovidViewHolder, int i) {
        title.setText(arrayList[i]);
        switch (i){
            case 0:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hand_wash));
                break;
            case 1:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.distance));
                break;
            case 2:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.coronavirus));
                break;
            case 3:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.quarantine));
                break;
            case 4:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.nurse));
                break;
            case 5:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.form));
                break;
        }
    }

    class PrevetCovidViewHolder extends RecyclerView.ViewHolder {

        public PrevetCovidViewHolder(View view){
            super(view);
        }
    }
}