package com.example.nashik_cityguide.FireBrigade_Activity;

public class firebrigade {

    String contact,gmlink,name;

    public firebrigade() {
    }

    public firebrigade(String contact, String gmlink, String name) {
        this.contact = contact;
        this.gmlink = gmlink;
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGmlink() {
        return gmlink;
    }

    public void setGmlink(String gmlink) {
        this.gmlink = gmlink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
