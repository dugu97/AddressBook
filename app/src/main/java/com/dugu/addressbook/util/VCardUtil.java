package com.dugu.addressbook.util;

import com.dugu.addressbook.model.Contact;
import com.dugu.addressbook.model.ContactWithPhoneAndEmail;
import com.dugu.addressbook.model.Email;
import com.dugu.addressbook.model.Phone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.ImageType;
import ezvcard.property.Address;
import ezvcard.property.Birthday;
import ezvcard.property.Photo;
import ezvcard.property.Telephone;

public class VCardUtil {

    public static void createVCard(List<Contact> contactList, File file) throws IOException {

        List<VCard> vCardList = new ArrayList<>();

        for (Contact contact : contactList) {

            VCard vcard = new VCard(VCardVersion.V4_0);

            //头像
            if (contact.getIcon() != null && contact.getIcon().length > 0)
                vcard.addPhoto(new Photo(contact.getIcon(), ImageType.PNG));

            //生日
            if (!AppUtil.isNullString(contact.getBirthday()))
                vcard.setBirthday(new Birthday(contact.getBirthday()));

            //名字
            if (!AppUtil.isNullString(contact.getName()))
                vcard.setFormattedName(contact.getName());

            //昵称
            if (!AppUtil.isNullString(contact.getNickname()))
                vcard.setNickname(contact.getNickname());

            //备注
            if (!AppUtil.isNullString(contact.getRemark()))
                vcard.addNote(contact.getRemark());

            //职位
            if (!AppUtil.isNullString(contact.getJob()))
                vcard.addTitle(contact.getJob());

            //公司
            if (!AppUtil.isNullString(contact.getOrganization()))
                vcard.setOrganization(contact.getOrganization());

            //地址
            if (!AppUtil.isNullString(contact.getAddress())) {
                Address adr = new Address();
                adr.setStreetAddress(contact.getAddress());
                vcard.addAddress(adr);
            }

            //电话
            List<Phone> phoneList = contact.getPhoneList();
            for (int i = 0; i < phoneList.size(); i++) {
                Phone phone = phoneList.get(i);
                vcard.addTelephoneNumber(phone.getPhone());
            }

            //邮件
            List<Email> emailList = contact.getEmailList();
            for (int i = 0; i < emailList.size(); i++) {
                Email email = emailList.get(i);
                vcard.addEmail(email.getEmail());
            }

            vCardList.add(vcard);
        }

        Ezvcard.write(vCardList).version(VCardVersion.V4_0).go(file);
    }

    public static List<ContactWithPhoneAndEmail> parseVCard(File file) throws IOException {

        List<ContactWithPhoneAndEmail> contactList = new ArrayList<>();
        List<VCard> vCards = Ezvcard.parse(file).all();

        for (VCard v : vCards) {

            ContactWithPhoneAndEmail contact = new ContactWithPhoneAndEmail();

            //头像
            if (!v.getPhotos().isEmpty())
                contact.setIcon(v.getPhotos().get(0).getData());

            //生日
            if (v.getBirthday() != null)
                contact.setBirthday(v.getBirthday().getText());

            //名字
            if (v.getFormattedName() != null)
                contact.setName(v.getFormattedName().getValue());

            //昵称
            if (v.getNickname() != null)
                contact.setNickname(v.getNickname().getValues().get(0));

            //备注
            if (!v.getNotes().isEmpty())
                contact.setRemark(v.getNotes().get(0).getValue());

            //职位
            if (!v.getTitles().isEmpty())
                contact.setJob(v.getTitles().get(0).getValue());

            //公司
            if (v.getOrganization() != null)
                contact.setOrganization(v.getOrganization().getValues().get(0));

            //地址
            if (!v.getAddresses().isEmpty())
                contact.setAddress(v.getAddresses().get(0).getStreetAddress());

            if (!v.getTelephoneNumbers().isEmpty()) {
                List<String> phoneList = new ArrayList<>();
                for (Telephone phone : v.getTelephoneNumbers()) {
                    String string = phone.getText();
                    string.replace(" ", "");
                    phoneList.add(string);
                }
                contact.setPhoneList(phoneList);
            }

            if (!v.getEmails().isEmpty()) {
                List<String> emailList = new ArrayList<>();
                for (ezvcard.property.Email email : v.getEmails()) {
                    emailList.add(email.getValue());
                }
                contact.setEmailList(emailList);
            }

            System.out.println(contact.toString());

            contactList.add(contact);
        }
        return contactList;
    }

}
