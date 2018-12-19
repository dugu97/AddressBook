//package com.dugu.addressbook.assembly;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//
//import com.dugu.addressbook.R;
//import com.dugu.addressbook.databinding.AddressbookToolBarBinding;
//
//public class ABToolBar {
//
//    AddressbookToolBarBinding binding;
//
//    Toolbar toolbar;
//
//    private ABToolBarCallBack mBarCallBack;
//
//    public ABToolBar(Context context) {
//        init(context);
//    }
//
//    private void init(Context context) {
////        if (isInEditMode())
////            return;
//        LayoutInflater inflater = LayoutInflater.from(context);
////        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        binding = DataBindingUtil.inflate(inflater, R.layout.addressbook_tool_bar, null, true);
////        binding = DataBindingUtil.findBinding(R.layout.addressbook_tool_bar);
////        toolbar = findViewById(R.id.toolbar);
//        binding.toolbar.inflateMenu(R.menu.navigation);
//
//
//        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mBarCallBack == null) {
////                    Toast.makeText(Address, "未设置", Toast.LENGTH_SHORT)
////                            .show();
//                } else {
//                    mBarCallBack.onFallbackClickCallBack();
//                }
//            }
//        });
//
//        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (mBarCallBack == null) {
////                    Toast.makeText(context, "未设置", Toast.LENGTH_SHORT)
////                            .show();
//                } else {
//                    mBarCallBack.onRightButtonClickCallBack();
//                }
//                return true;
//            }
//        });
//    }
//
//    public void addDuguToolBarCallBack(ABToolBarCallBack callBack) {
//        this.mBarCallBack = callBack;
//    }
//
//    public interface ABToolBarCallBack {
//        void onFallbackClickCallBack();
//
//        void onRightButtonClickCallBack();
//    }
//}
