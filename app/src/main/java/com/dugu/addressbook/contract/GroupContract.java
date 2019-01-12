package com.dugu.addressbook.contract;

import com.dugu.addressbook.model.Group;
import com.dugu.addressbook.viewmodel.GroupViewModel;

import java.util.List;

public interface GroupContract {

    interface Ui extends BaseView<Presenter>{
        void showResult();
        void addGroup();
    }

    interface Presenter extends BasePresenter{
        void deleteGroup(List<Group> groupIds);
        void createGroup(String group_name);
        GroupViewModel getGroupViewModel();
    }
}
