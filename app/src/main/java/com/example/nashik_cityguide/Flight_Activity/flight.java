package com.example.nashik_cityguide.Flight_Activity;

public class flight {

    String arr, cname, dept, dest, fno, freq, origin;

    public flight() {
    }

    public flight(String arr, String cname, String dept, String dest, String fno, String freq, String origin) {
        this.arr = arr;
        this.cname = cname;
        this.dept = dept;
        this.dest = dest;
        this.fno = fno;
        this.freq = freq;
        this.origin = origin;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getFno() {
        return fno;
    }

    public void setFno(String fno) {
        this.fno = fno;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
