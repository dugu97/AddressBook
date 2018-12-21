package com.dugu.addressbook.model;

import com.dugu.addressbook.db.ContactDao;
import com.dugu.addressbook.db.DaoSession;
import com.dugu.addressbook.db.GroupDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class Group {
    @Id(autoincrement = true)
    private Long group_id;

    private String group_name;

    @Generated(hash = 141093849)
    public Group(Long group_id, String group_name) {
        this.group_id = group_id;
        this.group_name = group_name;
    }

    @Generated(hash = 117982048)
    public Group() {
    }


    public String getGroup_name() {
        return this.group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Long getGroup_id() {
        return this.group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }
}
