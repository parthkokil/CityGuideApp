package com.example.nashik_cityguide.PoliceStation_Activity;

public class policeStation {

    String contact,gmlink,name;

    public policeStation() {
    }

    public policeStation(String contact, String gmlink, String name) {
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
