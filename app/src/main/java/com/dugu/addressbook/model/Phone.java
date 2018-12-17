package com.dugu.addressbook.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Phone {
    @Id(autoincrement = true)
    private Long phone_id;

    private String phone;

    private String phone_name;

    @Generated(hash = 830360257)
    public Phone(Long phone_id, String phone, String phone_name) {
        this.phone_id = phone_id;
        this.phone = phone;
        this.phone_name = phone_name;
    }

    @Generated(hash = 429398894)
    public Phone() {
    }

    public Long getPhone_id() {
        return this.phone_id;
    }

    public void setPhone_id(Long phone_id) {
        this.phone_id = phone_id;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_name() {
        return this.phone_name;
    }

    public void setPhone_name(String phone_name) {
        this.phone_name = phone_name;
    }
}
