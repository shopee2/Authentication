package org.maimeemaineemaicode.register.bean;

public class TokenValidateResponseBody {
    public String uid;
    public String role;

    public TokenValidateResponseBody() {
        this("", "");
    }

    public TokenValidateResponseBody(String uid, String role) {
        this.uid = uid;
        this.role = role;
    }
}
