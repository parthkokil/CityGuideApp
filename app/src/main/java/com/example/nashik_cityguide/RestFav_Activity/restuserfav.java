package com.example.nashik_cityguide.RestFav_Activity;

public class restuserfav {

    String add,cat1,cat2,desc,gmlink,img1,img2,img3,name,rating,timing;

    public restuserfav() {
    }

    public restuserfav(String add, String cat1, String cat2, String desc, String gmlink, String img1, String img2, String img3, String name, String rating, String timing) {
        this.add = add;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.desc = desc;
        this.gmlink = gmlink;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.name = name;
        this.rating = rating;
        this.timing = timing;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGmlink() {
        return gmlink;
    }

    public void setGmlink(String gmlink) {
        this.gmlink = gmlink;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
