package org.maimeemaineemaicode.register.model;

import com.sun.xml.bind.v2.runtime.output.Encoded;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long userID;

    @Column(name="username", unique = true)
    private String username;


    @Column(name="password", length=512, columnDefinition="TEXT")
    private String password;

    @Column(name="role")
    private String role;

    public Account() {
        this("", "", "");
    }

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = hashPassword(password);
        this.role = role;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
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

    public static String hashPassword(String password) {
        String salt = "c2FsdGFuZHNhbHRzYW5kYW1vcmVzYWx0c3k=";
        return DigestUtils.sha256Hex(password + salt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        return userID != null && userID.equals(((Account) o).getUserID());
    }

}
