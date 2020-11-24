package org.maimeemaineemaicode.register.controllers;

import org.apache.coyote.Response;
import org.maimeemaineemaicode.register.bean.TokenValidateResponseBody;
import org.maimeemaineemaicode.register.model.Account;
import org.maimeemaineemaicode.register.model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@RestController
public class TokenService {
    @RequestMapping(value = "/validate/{tokenBody}", method = RequestMethod.GET)
    public ResponseEntity validateToken(@PathVariable String tokenBody) {
        try {
            Token token = new Token(tokenBody);
            String decrypt = token.decrypt();
            String[] accountPhase = decrypt.split(":");

            String username = accountPhase[0];
            String uid = accountPhase[1];
            String role = accountPhase[2];

            return new ResponseEntity(new TokenValidateResponseBody(uid, role), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}