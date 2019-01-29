package com.dugu.addressbook.util;

import com.dugu.addressbook.model.ContactWithPhoneAndEmail;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.Gender;
import ezvcard.property.Kind;
import ezvcard.property.Revision;
import ezvcard.property.StructuredName;
import ezvcard.property.Timezone;
import ezvcard.property.Uid;

public class Test {
    public static void main(String[] args) throws IOException, TransformerException {
//        System.out.println(HanZiToPinYinUtil.getFirstPinyin("1"));
//        Bitmap bmp=BitmapFactory.decodeResource(AddressBookApplication.getAppContext().getResources(), R.drawable.vector_drawable_group_main_icon);
//        Log.d("123", bmp.toString());

//        UUID uuid = UUID.fromString("2");
//        System.out.println(uuid.toString());

//        VCard vcard = createVCard();
//
//        //validate vCard for version 3.0
//        System.out.println("Version 3.0 validation warnings:");
//        System.out.println(vcard.validate(VCardVersion.V3_0));
//        System.out.println();
//
//        //validate vCard for version 4.0 (xCard and jCard use this version)
//        System.out.println("Version 4.0 validation warnings:");
//        System.out.println(vcard.validate(VCardVersion.V4_0));
//
        //write vCard
//        File file = new File("john-doe.vcf");
//        System.out.println("Writing " + file.getName() + "...");
//        Ezvcard.write(vcard).version(VCardVersion.V3_0).go(file);
//
//        //write xCard
//        file = new File("john-doe.xml");
//        System.out.println("Writing " + file.getName() + "...");
//        Ezvcard.writeXml(vcard).indent(2).go(file);
//
//        //write hCard
//        file = new File("john-doe.html");
//        System.out.println("Writing " + file.getName() + "...");
//        Ezvcard.writeHtml(vcard).go(file);
//
//        //write jCard
//        file = new File("john-doe.json");
//        System.out.println("Writing " + file.getName() + "...");
//        Ezvcard.writeJson(vcard).go(file);

        ContactWithPhoneAndEmail contact = VCardUtil.parseVCard("BEGIN:VCARD" +
                "VERSION:4.0" +
                "PRODID:ez-vcard 0.10.5" +
                "FN:156打" +
                "TEL:134" +
                "TEL:135" +
                "END:VCARD");
        System.out.println(contact.toString());
    }

    private static VCard createVCard() throws IOException {
        VCard vcard = new VCard();

        vcard.setKind(Kind.individual());

        vcard.setGender(Gender.male());

        vcard.addLanguage("en-US");

        StructuredName n = new StructuredName();
        n.setFamily("Doe");
        n.setGiven("Jonathan");
        n.getPrefixes().add("Mr");
        vcard.setStructuredName(n);

        vcard.setFormattedName("Jonathan Doe");

        vcard.setNickname("John", "Jonny");

        vcard.addTitle("Widget Engineer");

        vcard.setOrganization("Acme Co. Ltd.", "Widget Department");

        Address adr = new Address();
        adr.setStreetAddress("123 Wall St.");
        adr.setLocality("New York");
        adr.setRegion("NY");
        adr.setPostalCode("12345");
        adr.setCountry("USA");
        adr.setLabel("123 Wall St.\nNew York, NY 12345\nUSA");
        adr.getTypes().add(AddressType.WORK);
        vcard.addAddress(adr);

        adr = new Address();
        adr.setStreetAddress("123 Main St.");
        adr.setLocality("Albany");
        adr.setRegion("NY");
        adr.setPostalCode("54321");
        adr.setCountry("USA");
        adr.setLabel("123 Main St.\nAlbany, NY 54321\nUSA");
        adr.getTypes().add(AddressType.HOME);
        vcard.addAddress(adr);

        vcard.addTelephoneNumber("1-555-555-1234", TelephoneType.WORK);
        vcard.addTelephoneNumber("1-555-555-5678", TelephoneType.WORK, TelephoneType.CELL);

        vcard.addEmail("johndoe@hotmail.com", EmailType.HOME);
        vcard.addEmail("doe.john@acme.com", EmailType.WORK);

        vcard.addUrl("http://www.acme-co.com");

        vcard.setCategories("widgetphile", "biker", "vCard expert");

        vcard.setGeo(37.6, -95.67);

        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("America/New_York");
        vcard.setTimezone(new Timezone(tz));

//        File file = new File("portrait.jpg");
//        Photo photo = new Photo(file, ImageType.JPEG);
//        vcard.addPhoto(photo);
//
//        file = new File("pronunciation.ogg");
//        Sound sound = new Sound(file, SoundType.OGG);
//        vcard.addSound(sound);

        vcard.setUid(Uid.random());

        vcard.setRevision(Revision.now());

        return vcard;
    }
}
