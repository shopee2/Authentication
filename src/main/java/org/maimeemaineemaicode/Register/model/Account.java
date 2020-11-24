package org.maimeemaineemaicode.register.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class Account {
//    @Id
    private String userID;
//    @Column(name="username")
    private String username;
//    @Column(name="password")
    private String password;
//    @Column(name="role")
    private String role;

    public Account() {}

    public Account(Account account) {
        this(account.getUserID(), account.getUserName(), account.getPassword(), account.getRole());
    }

    public Account(String userID, String username, String password, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
