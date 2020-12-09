package com.app.dailyexpenses;

class Member {
    public String fullname;
    public String email;
    public String gender;
    public String mobile;
    public Member(){}
    public Member(String fullname, String email, String gender, String mobile) {
        this.fullname = fullname;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }
}
