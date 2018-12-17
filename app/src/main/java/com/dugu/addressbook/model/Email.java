package com.dugu.addressbook.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Email {
    @Id(autoincrement = true)
    private Long email_id;

    private String email;

    private String email_name;

    @Generated(hash = 768876893)
    public Email(Long email_id, String email, String email_name) {
        this.email_id = email_id;
        this.email = email;
        this.email_name = email_name;
    }

    @Generated(hash = 272676561)
    public Email() {
    }

    public Long getEmail_id() {
        return this.email_id;
    }

    public void setEmail_id(Long email_id) {
        this.email_id = email_id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_name() {
        return this.email_name;
    }

    public void setEmail_name(String email_name) {
        this.email_name = email_name;
    }
}
