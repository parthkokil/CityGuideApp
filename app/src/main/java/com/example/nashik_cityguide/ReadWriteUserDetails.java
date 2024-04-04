package com.example.nashik_cityguide;

public class ReadWriteUserDetails {
    public String username,email, dob, gender, mobile, pass;
    ReadWriteUserDetails(){

    }
    public ReadWriteUserDetails(String text_name , String text_email, String text_mobile, String text_dob, String text_gender, String text_pass){
        this.username = text_name;
        this.email = text_email;
        this.dob = text_dob;
        this.gender = text_gender;
        this.mobile = text_mobile;
        this.pass = text_pass;
    }
}
