package fadhilah.ramadhan.covid19stats.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import fadhilah.ramadhan.covid19stats.R;

public class ProgresDialog {

    private ProgressDialog mProgressDialog;

    protected TextView titleView;
    protected TextView contentView;
    protected ProgressBar progress;

    private String mMessage;
    private String mTitle;

    private Context context;

    public void setTitleAndContent(String title, String content){
        mMessage = content;
        mTitle = title;
    }

    public ProgresDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mMessage = context.getResources().getString(R.string.label_pleaseWait);
        mTitle = context.getResources().getString(R.string.app_name);

        this.context = context;

    }

    public final ProgresDialog setOnCancelListener(DialogInterface.OnCancelListener p_listener) {
        mProgressDialog.setOnCancelListener(p_listener);
        return this;
    }

    private void initView(){
        titleView = (TextView) mProgressDialog.findViewById(R.id.loading_dialog_title);
        contentView = (TextView) mProgressDialog.findViewById(R.id.loading_dialog_content);
        //contentView.setTypeface(FontUtils.loadFontFromAssets(context, Constant.FONT_BOLD));
        progress = (ProgressBar) mProgressDialog.findViewById(R.id.avLoading);
    }

    public final void show() {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.dialog_loading);

        initView();

        titleView.setText(mTitle);
        contentView.setText(mMessage);
        //progress.show();
    }

    public final void dismiss() {
        mProgressDialog.dismiss();
    }

    public final boolean isShowing() {
        return mProgressDialog.isShowing();
    }
}
