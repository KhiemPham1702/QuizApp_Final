package com.example.quiz_final;

public class User {
    public String email;
    public String pass;
    public String nickname;
    public String phone;
    public String school;
    public String avatar;
    private long score=0;
    public User() {}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public User(String email, String pass, String nickname, String phone, String school) {
        this.email = email;
        this.pass = pass;
        this.nickname = nickname;
        this.phone = phone;
        this.school = school;
    }
}
