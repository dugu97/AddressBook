package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.NewOrEditContactActivity;
import com.dugu.addressbook.databinding.FragScanQrCodeBinding;

import cn.bingoogolapple.qrcode.core.QRCodeView;

import static android.content.Context.VIBRATOR_SERVICE;

public class ScanFragment extends BaseFragment implements QRCodeView.Delegate {

    FragScanQrCodeBinding binding;

    public static ScanFragment newInstance(Bundle bundle) {
        ScanFragment fragment = new ScanFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_scan_qr_code, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        binding.zxingview.setDelegate(this);
    }

    @Override
    protected void initData() {
        binding.zxingview.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        binding.zxingview.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void addListener() {

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i("123", "result:" + result);
        vibrate();

        Intent intent = new Intent(getContext(), NewOrEditContactActivity.class);
        intent.putExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, Constants.CONTACT_MODE_NEW_PHONE_CONTACT_WITH_QR_CODE);
        intent.putExtra(Constants.ALLACTIVITY_STRING, result);
        startActivity(intent);

        getActivity().finish();
//        binding.zxingview.startSpot(); // 开始识别
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("123", "打开相机出错");
    }

    @Override
    public void onStop() {
        binding.zxingview.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    public void onDestroy() {
        binding.zxingview.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }
}
