package com.example.nofarreshef.contacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Nofar Reshef on 11/03/2018.
 */

public class Person implements Serializable {

    private String name;
    private String phone;
    private String mail;
    private String address;
    private String website;
    private  String date;
    private String time;
    private String days;

    transient private Bitmap photo;



    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getDays() {
        return days;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
   //    if(photo != null) {
           photo.compress(Bitmap.CompressFormat.JPEG, 70, out);
     //  }
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
        photo = BitmapFactory.decodeStream(in);
        in.defaultReadObject();
    }

}
