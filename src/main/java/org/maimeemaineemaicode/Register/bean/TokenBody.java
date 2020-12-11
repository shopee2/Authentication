package org.maimeemaineemaicode.register.bean;

import org.apache.commons.codec.binary.Base64;
import org.maimeemaineemaicode.register.model.Account;

public class TokenBody {

    private String token;

    public TokenBody(String token) {
        this.token =  token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
