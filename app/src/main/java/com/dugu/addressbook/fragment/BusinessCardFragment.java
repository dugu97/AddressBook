package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.ContactDetailActivity;
import com.dugu.addressbook.activity.NewOrEditContactActivity;
import com.dugu.addressbook.adapter.ContactsAdapter;
import com.dugu.addressbook.contract.BusinessCardContract;
import com.dugu.addressbook.databinding.FragBusinessCardBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.viewmodel.item.ContactItemViewModel;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

public class BusinessCardFragment extends BaseFragment implements BusinessCardContract.Ui, TakePhoto.TakeResultListener, InvokeListener {

    private BusinessCardContract.Presenter presenter;
    private FragBusinessCardBinding binding;
    private ContactsAdapter adapter;

    //    takePhoto
    private TakePhoto takePhoto;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig;  //压缩参数
    private Uri imageUri;  //图片保存路径
    private InvokeParam invokeParam;

    public static BusinessCardFragment newInstance(Bundle bundle) {
        BusinessCardFragment fragment = new BusinessCardFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_business_card, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        adapter = new ContactsAdapter();
        binding.contactList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactList.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        presenter.start();
    }

    @Override
    protected void addListener() {
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog
                        .Builder(getActivity())
                        .setItems(Constants.MODE_TAKE_PHOTO, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == Constants.TAKE_PHOTO_FROM_ALBUM) {
                                    chooseIconFromTakePhoto(which);
                                } else if (which == Constants.TAKE_PHOTO_FROM_CAMERA) {
                                    chooseIconFromTakePhoto(which);
                                }
                            }
                        }).create();
                alertDialog.show();
            }
        });

        adapter.setOnClickListener(new OnItemElementClickListener<ContactItemViewModel>() {
            @Override
            public void onClick(ContactItemViewModel obj, int position) {
                Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                intent.putExtra(Constants.MAINACTIVITY_CONTACT_ID, obj.getContact().getContact_id());
                startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_CONTACT);
            }
        });

        adapter.setOnLongClickListener(new OnItemElementClickListener<ContactItemViewModel>() {
            @Override
            public void onClick(ContactItemViewModel obj, int position) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.start();

        if (adapter.getItemCount() == 0){
            binding.title.setVisibility(View.GONE);
            binding.noContact.setVisibility(View.VISIBLE);
        }else{
            binding.title.setVisibility(View.VISIBLE);
            binding.noContact.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResult() {
        binding.setBusinessCardViewModel(presenter.getBusinessCardViewModel());
        adapter.setmItems(binding.getBusinessCardViewModel().getViewModels());
    }

    @Override
    public void setPresenter(BusinessCardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void takeSuccess(TResult result) {
        //拍照后最终的URL
        String iconPath = result.getImage().getOriginalPath(); //这个才是经过压缩的

        Intent intent = new Intent(getContext(), NewOrEditContactActivity.class);
        intent.putExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, Constants.CONTACT_MODE_NEW_PHONE_CONTACT_WITH_BUSINESS_CARD);
        intent.putExtra(Constants.ALLACTIVITY_STRING, iconPath);
        startActivity(intent);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    private void chooseIconFromTakePhoto(int type) {
        //获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setWithOwnCrop(true).setAspectX(2).setAspectY(3).create();
        //设置压缩参数
        compressConfig = new CompressConfig.Builder().enableQualityCompress(false).setMaxSize(10 * 1024).create();
        takePhoto.onEnableCompress(compressConfig, false);  //设置为需要压缩

        if (type == Constants.TAKE_PHOTO_FROM_ALBUM) {
            imageUri = getImageCropUri();
            //从相册中选取图片并裁剪
            takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
        }
        if (type == Constants.TAKE_PHOTO_FROM_CAMERA) {
            imageUri = getImageCropUri();
            //拍照并裁剪
            takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
        }
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //takePhoto 配置
        getTakePhoto().onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.RESULT_CODE_OK) {
            if (requestCode == Constants.REQUEST_CODE_EDIT_CONTACT) {
                presenter.start();
            }
        }
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
}
