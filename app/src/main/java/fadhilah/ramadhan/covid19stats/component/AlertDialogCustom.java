package fadhilah.ramadhan.covid19stats.component;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import fadhilah.ramadhan.covid19stats.R;

public class AlertDialogCustom {

    public interface AlertDialogListener{
        public void onDismmisClick();
    }

    private static AlertDialogListener listener;

    public void setListener(AlertDialogListener listener) {
        this.listener = listener;
    }

    Context context;
    private Dialog dialogSpass;
    protected TextView yesTextBtn;
    protected TextView alert_content_text;
    protected TextView txt_title;
    protected String title;
    protected String content;
    protected String btns;

    public void setTitleandContent(String title, String content, String btns) {
        this.title = title;
        this.content = content;
        this.btns = btns;
        updateUi();
    }

    private void updateUi(){
        txt_title.setText(title);
        alert_content_text.setText(content);
        yesTextBtn.setText(btns);
    }

    public AlertDialogCustom(Context context) {
        this.context = context;
        dialogSpass = new Dialog(context);
        dialogSpass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSpass.setCanceledOnTouchOutside(false);
        dialogSpass.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSpass.setContentView(R.layout.dialog_alert);

        dialogSpass.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent _dialog close on back press button
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (listener != null) {
                        listener.onDismmisClick();
                    }
                }

                return false;
            }
        });

        dialogSpass.show();

        initViews();

        yesTextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
                if (listener != null) {
                    listener.onDismmisClick();
                }
            }
        });


    }

    public final void show() {
        dialogSpass.show();
    }

    public final void dismiss() {
        dialogSpass.dismiss();
    }

    public final boolean isShowing() {
        return dialogSpass.isShowing();
    }

    private void initViews() {
        txt_title = (TextView) dialogSpass.findViewById(R.id.txt_title);
        yesTextBtn = (TextView) dialogSpass.findViewById(R.id.yesTextBtn);
        alert_content_text = (TextView) dialogSpass.findViewById(R.id.alert_content_text);
    }

}