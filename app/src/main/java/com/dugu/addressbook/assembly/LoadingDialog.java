package com.dugu.addressbook.assembly;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dugu.addressbook.R;

/**
 * 加载对话框（目前已添加到WuYuBaseActivity）
 */

public class LoadingDialog extends DialogFragment {
    private final String TAG = "WuYuLoadingDialog.TAG";

    private TextView tvMessage;

    private static String message;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        tvMessage = (TextView) v.findViewById(R.id.dialog_loading_message);
        tvMessage.setText(message);
        Dialog loadingDialog = new Dialog(getActivity(), R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(v);// 设置布局
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return loadingDialog;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
