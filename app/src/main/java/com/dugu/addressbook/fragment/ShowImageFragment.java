package com.dugu.addressbook.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dugu.addressbook.AddressBookApplication;
import com.dugu.addressbook.Constants;
import com.dugu.addressbook.R;
import com.dugu.addressbook.databinding.FragShowImageBinding;
import com.dugu.addressbook.db.ContactDao;
import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.viewmodel.ShowImageViewModel;

public class ShowImageFragment extends BaseFragment {

    FragShowImageBinding binding;

    public static ShowImageFragment newInstance(Bundle bundle) {
        ShowImageFragment fragment = new ShowImageFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_show_image, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        Long contact_id = intent.getLongExtra(Constants.ALLACTIVITY_CONTACT_ID, -1);

        if (contact_id == -1)
            makeToast("系统出错");

        Contact contact = AddressBookApplication.getDaoSession().getContactDao().queryBuilder().where(ContactDao.Properties.Contact_id.eq(contact_id)).unique();

        ShowImageViewModel viewModel = new ShowImageViewModel(contact.getBusinessardData());

        binding.setShowImageViewModel(viewModel);

        binding.image.setImageBitmap(BitmapFactory.decodeByteArray(viewModel.getImage(), 0, viewModel.getImage().length));
    }

    @Override
    protected void addListener() {

    }

}
