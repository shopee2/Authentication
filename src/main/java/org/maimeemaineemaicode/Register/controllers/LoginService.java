package org.maimeemaineemaicode.register.controllers;

import org.maimeemaineemaicode.register.bean.LoginBody;
import org.maimeemaineemaicode.register.bean.SaleRegisterBody;
import org.maimeemaineemaicode.register.model.Account;
import org.maimeemaineemaicode.register.model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginService {
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Token login(@RequestBody LoginBody body) {
        // validate login
        try {
            if (body.username.equals("username") && body.password.equals("password")) {
                Account account = new Account("00001", "username", "password", "Customer");
                return new Token(account);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
