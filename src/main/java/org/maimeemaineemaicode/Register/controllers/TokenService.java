package org.maimeemaineemaicode.register.controllers;

import org.maimeemaineemaicode.register.bean.TokenValidateResponseBody;
import org.maimeemaineemaicode.register.model.Account;
import org.maimeemaineemaicode.register.model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@RestController
public class TokenService {
    @RequestMapping(value = "/validate/{tokenBody}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenValidateResponseBody validateToken(@PathVariable String tokenBody) {
        try {
            Token token = new Token(tokenBody);
            String decrypt = token.decrypt();
            String[] accountPhase = decrypt.split(":");

            String username = accountPhase[0];
            String uid = accountPhase[1];
            String role = accountPhase[2];

            return new TokenValidateResponseBody(uid, role);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}