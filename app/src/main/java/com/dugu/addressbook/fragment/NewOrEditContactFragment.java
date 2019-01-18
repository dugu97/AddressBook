package com.dugu.addressbook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.activity.GroupChooseActivity;
import com.dugu.addressbook.adapter.ContactInputMegSortedListAdapter;
import com.dugu.addressbook.adapter.ContactInputMegSortedListCallback;
import com.dugu.addressbook.assembly.ABToolBar;
import com.dugu.addressbook.contract.NewOrEditContactContract;
import com.dugu.addressbook.databinding.FragEditAndNewContactBinding;
import com.dugu.addressbook.listener.OnItemElementClickListener;
import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.util.AppUtil;
import com.dugu.addressbook.viewmodel.item.ContactInputItemViewModel;
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
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewOrEditContactFragment extends BaseFragment implements NewOrEditContactContract.Ui, TakePhoto.TakeResultListener, InvokeListener {

    private FragEditAndNewContactBinding binding;
    private NewOrEditContactContract.Presenter presenter;

    private int mode; //两种显示模式

    //    takePhoto
    private TakePhoto takePhoto;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig;  //压缩参数
    private Uri imageUri;  //图片保存路径
    private InvokeParam invokeParam;

    private ContactInputMegSortedListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static NewOrEditContactFragment newInstance(Bundle bundle) {
        NewOrEditContactFragment fragment = new NewOrEditContactFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_edit_and_new_contact, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {
        //启动presenter
//        presenter.start();

        //-1 系统出错
        if (mode == -1)
            return;

        //配置RecycleView
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.contactInputList.setLayoutManager(linearLayoutManager);
        adapter = new ContactInputMegSortedListAdapter();
        ContactInputMegSortedListCallback sortedListCallback = new ContactInputMegSortedListCallback(adapter);
        SortedList<ContactInputItemViewModel> sortedList = new SortedList<>(ContactInputItemViewModel.class, sortedListCallback);
        adapter.setSortedList(sortedList);
        binding.contactInputList.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        mode = intent.getIntExtra(Constants.ALLACTIVITY_MODE_NEW_OR_EDIT_CONTACT, -1);

        if (mode == Constants.CONTACT_MODE_NEW_PHONE_CONTACT) {
            getABActionBar().setCenterTitleText("新建联系人");
            presenter.createViewModel(mode, null);
//            adapter.setData(presenter.getNewOrEditContactViewModel().getInputList());
//            binding.setNewOrEditContactViewModel(presenter.getNewOrEditContactViewModel());
        } else if (mode == Constants.CONTACT_MODE_EDIT_PHONE_CONTACT) {
            getABActionBar().setCenterTitleText("编辑联系人");
            long contact_id = intent.getLongExtra(Constants.ALLACTIVITY_CONTACT_ID, -1);
            if (contact_id != -1)
                presenter.createViewModel(mode, contact_id);

//            binding.setNewOrEditContactViewModel(presenter.getNewOrEditContactViewModel());
//            adapter.setData(binding.getNewOrEditContactViewModel().getInputList());
        }
    }

    @Override
    public void showResult() {
        binding.setNewOrEditContactViewModel(presenter.getNewOrEditContactViewModel());
        adapter.setData(binding.getNewOrEditContactViewModel().getInputList());

        byte[] icon = binding.getNewOrEditContactViewModel().getIcon();
        //设置联系人头像
        if (icon != null && icon.length > 0) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(AddressBookApplication.getAppContext()).load(icon)
                    .apply(options).into(binding.takeContactIcon);
        }
    }

    @Override
    protected void addListener() {
        getABActionBar().addDuguToolBarCallBack(new ABToolBar.ABToolBarCallBack() {
            @Override
            public void onFallbackClickCallBack() {
                getActivity().finish();
            }

            @Override
            public void onRightButtonClickCallBack() {
                makeToast("确定");
                presenter.createOrUpdateContact(binding.getNewOrEditContactViewModel());
                getActivity().setResult(Constants.RESULT_CODE_OK);
                getActivity().finish();
            }
        });

        binding.takeContactIcon.setOnClickListener(new View.OnClickListener() {
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

        adapter.setOnTitleClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
            @Override
            public void onClick(final ContactInputItemViewModel obj, int position) {
                if (obj.getSortKey() == Constants.SORTKEY_PHONE || obj.getSortKey() == Constants.SORTKEY_EMAIL) {
                    makeToast("title");
                    final String[] strings;
                    if (obj.getSortKey() == Constants.SORTKEY_PHONE)
                        strings = Constants.PHONE_LABEL_PROJECT;
                    else
                        strings = Constants.EMAIL_LABEL_PROJECT;
                    AlertDialog alertDialog = new AlertDialog
                            .Builder(getActivity())
                            .setItems(strings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //选择自定义
                                    if (which == strings.length - 1) {
                                        showInputDialog(obj, strings[which]);
                                        return;
                                    }

                                    String temp = strings[which];
                                    updataInputListItemTitle(temp, obj);
                                }
                            }).create();
                    alertDialog.show();
                }
            }
        });

        adapter.setOnAddMoreInputBtnClickListener(new OnItemElementClickListener<ContactInputItemViewModel>() {
            @Override
            public void onClick(ContactInputItemViewModel obj, int position) {
                Log.d("123", adapter.getItemCount() + "个");
                ContactInputItemViewModel viewModel;
                if (obj.getSortKey() == Constants.SORTKEY_PHONE) {
                    viewModel = new ContactInputItemViewModel(Constants.SORTKEY_PHONE, adapter.getItemCount() + 1, "手机", null);
                    adapter.addData(viewModel);
                    binding.getNewOrEditContactViewModel().getInputList().add(viewModel);
                } else if (obj.getSortKey() == Constants.SORTKEY_EMAIL) {
                    viewModel = new ContactInputItemViewModel(Constants.SORTKEY_EMAIL, adapter.getItemCount() + 1, "私人", null);
                    adapter.addData(viewModel);
                    binding.getNewOrEditContactViewModel().getInputList().add(viewModel);
                }
            }
        });

        binding.addBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        binding.birthdayContent.setText(AppUtil.formatTimeInMillis(date.getTime()));
                    }
                }).setCancelColor(0xff00ACC2).setCancelText("取消").setSubmitColor(0xff00ACC2).setSubmitText("确定").build();
                pvTime.show();
            }
        });

        binding.addGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GroupChooseActivity.class);
                //适配已选择的group
                ArrayList<String> arrayList = new ArrayList<>();
                Log.d("123", binding.getNewOrEditContactViewModel().getGroupList().size() + " all");
                for (Group g : binding.getNewOrEditContactViewModel().getGroupList()) {
                    if (g.getGroup_id() > Constants.GROUP_BLACK) {
                        arrayList.add(g.getGroup_id() + "");
                    }
                }
                Log.d("123", arrayList.size() + " send");

                intent.putStringArrayListExtra(Constants.ALLACTIVITY_GROUPS_CHOOSE, arrayList);
                startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE_GROUP);
            }
        });

        binding.addOtherMeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ContactInputItemViewModel viewModel : binding.getNewOrEditContactViewModel().getInputList()) {
                    Log.d("123", viewModel.toString());
                }
//                    makeToast(binding.getNewOrEditContactViewModel().getJob())
//                    showInputDialog();
            }
        });
    }

    private void updataInputListItemTitle(String newTitle, ContactInputItemViewModel oldItem) {
        ContactInputItemViewModel viewModel = new ContactInputItemViewModel(oldItem.getSortKey(), oldItem.getSerialNumber(), newTitle, oldItem.getContent());
        adapter.addData(viewModel);
        binding.getNewOrEditContactViewModel().getInputList().remove(oldItem);
        binding.getNewOrEditContactViewModel().getInputList().add(viewModel);
    }

    private void showInputDialog(final ContactInputItemViewModel obj, final String defaultContent) {
        new TDialog.Builder(getActivity().getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_input)    //设置弹窗展示的xml布局
//                .setDialogView(view)  //设置弹窗布局,直接传入View
//                .setWidth(600)  //设置弹窗宽度(px)
//                .setHeight(800)  //设置弹窗高度(px)
                .setScreenWidthAspect(getContext(), 0.9f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
//                .setScreenHeightAspect(getContext(), 0.3f)  //设置弹窗高度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(false)     //弹窗在界面外是否可以点击取消
//                .setDialogAnimationRes(R.style.animate_dialog)  //设置弹窗动画
                .setOnDismissListener(new DialogInterface.OnDismissListener() { //弹窗隐藏时回调方法
                    @Override
                    public void onDismiss(DialogInterface dialog) {
//                        Toast.makeText(DiffentDialogActivity.this, "弹窗消失回调", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnBindViewListener(new OnBindViewListener() {   //通过BindViewHolder拿到控件对象,进行修改
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.dialog_common_title, "自定义标签名称");
                        bindViewHolder.setText(R.id.dialog_common_left, "取消");
                        bindViewHolder.setText(R.id.dialog_common_right, "确定");
                        EditText editText = bindViewHolder.getView(R.id.dialog_common_content);
                        InputFilter[] filters = {new InputFilter.LengthFilter(Constants.LABEL_MAX_LENGTH)};
                        editText.setFilters(filters);
                        editText.setHint("标签最长为6个字符");
                    }
                })
                .addOnClickListener(R.id.dialog_common_left, R.id.dialog_common_right)   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.dialog_common_left:
                                updataInputListItemTitle(defaultContent, obj);
                                tDialog.dismiss();
                                break;
                            case R.id.dialog_common_right:
                                EditText editText = viewHolder.getView(R.id.dialog_common_content);
                                String temp = editText.getText().toString().trim();
                                if (!AppUtil.isNullString(temp))
                                    updataInputListItemTitle(temp, obj);
                                else {
                                    updataInputListItemTitle(defaultContent, obj);
                                }
                                tDialog.dismiss();
                                break;
                        }
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                })
                .create()   //创建TDialog
                .show();    //展示

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

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    private void chooseIconFromTakePhoto(int type) {
        //获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setWithOwnCrop(true).setOutputX(150).setOutputY(150).setAspectX(1).setAspectY(1).create();
        //设置压缩参数
        compressConfig = new CompressConfig.Builder().enableQualityCompress(false).setMaxSize(25 * 1024).create();
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

    @Override
    public void takeSuccess(TResult result) {
        //拍照后最终的URL
        String iconPath = result.getImage().getOriginalPath(); //这个才是经过压缩的
        Uri uri = Uri.parse(iconPath);
        File file = new File(iconPath);
        byte[] byt;

        InputStream input = null;
        try {
            input = new FileInputStream(file);
            byt = new byte[input.available()];
            input.read(byt);

            binding.getNewOrEditContactViewModel().setIcon(byt);
            binding.takeContactIcon.setImageURI(uri);
            Log.d("123", byt.length + "");
            Log.d("123", file.length() + "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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

        if (resultCode == Constants.RESULT_CODE_OK)
            if (requestCode == Constants.REQUEST_CODE_CHOOSE_GROUP) {
                ArrayList<String> group = data.getStringArrayListExtra(Constants.ALLACTIVITY_GROUPS_CHOOSE);
                if (group == null)
                    return;
                List<Group> groupList = binding.getNewOrEditContactViewModel().getGroupList();
                List<Group> result = new ArrayList<>();
                for (int i = 0; i < groupList.size(); i++) {
                    if (groupList.get(i).getGroup_id() == Constants.GROUP_PHONE
                            || groupList.get(i).getGroup_id() == Constants.GROUP_CARD)
                        result.add(groupList.get(i));
                }
                for (int i = 0; i < group.size(); i++) {
                    String s = group.get(i);
                    String[] d = s.split(" ");
                    result.add(new Group(Long.parseLong(d[0]), d[1]));
                }
                binding.getNewOrEditContactViewModel().setGroupList(result);
                binding.groupContent.setText(binding.getNewOrEditContactViewModel().getGroupNamesWithData());
                Log.d("123", binding.getNewOrEditContactViewModel().getGroupNamesWithData());
                Log.d("123", binding.getNewOrEditContactViewModel().getGroupList().size() + "");
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    @Override
    public void setPresenter(NewOrEditContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
