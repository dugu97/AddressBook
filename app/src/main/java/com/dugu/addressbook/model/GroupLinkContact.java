package com.dugu.addressbook.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GroupLinkContact {

    @Id(autoincrement = true)
    private Long id;

    private Long contact_id;

    private Long group_id;

    @Generated(hash = 347659237)
    public GroupLinkContact(Long id, Long contact_id, Long group_id) {
        this.id = id;
        this.contact_id = contact_id;
        this.group_id = group_id;
    }

    @Generated(hash = 801779112)
    public GroupLinkContact() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContact_id() {
        return this.contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public Long getGroup_id() {
        return this.group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

}
