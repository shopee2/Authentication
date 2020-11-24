package org.maimeemaineemaicode.register.model;

import org.apache.commons.codec.binary.Base64;

public class Token {

    private String token;

    public Token() {
        this("");
    }

    public Token(Account account) {
        // generate token string
        String originalInput = account.getUserName() + ":" + account.getUserID() + ":" +  account.getRole();
        String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));
        this.token = encodedString;
    }

    public Token(String token) {
        this.token =  token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String decrypt() {
        String decodedString = new String(Base64.decodeBase64(this.token.getBytes()));
        return decodedString;
    }
}
