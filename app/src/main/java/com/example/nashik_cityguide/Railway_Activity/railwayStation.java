package com.example.nashik_cityguide.Railway_Activity;

public class railwayStation {

    String contact,name;

    public railwayStation() {
    }

    public railwayStation(String contact, String name) {
        this.contact = contact;
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
