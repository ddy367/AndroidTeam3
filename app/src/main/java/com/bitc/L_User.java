package com.bitc;

public class L_User {
    String userID;
    String userPassword;
    String userName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public L_User(String userID, String userPassword, String userName) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}