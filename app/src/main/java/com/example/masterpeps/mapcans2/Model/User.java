package com.example.masterpeps.mapcans2.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String userEmail;
    public String userName;
    public String userBirthday;
    public String userContact;


    public User(){}

    public User(String email, String name, String birthday, String contact){
        this.userEmail = email;
        this.userName = name;
        this.userBirthday = birthday;
        this.userContact = contact;
    }

}

