package org.maimeemaineemaicode.register.controllers;

import org.maimeemaineemaicode.register.bean.LoginBody;
import org.maimeemaineemaicode.register.bean.TokenValidateResponseBody;
import org.maimeemaineemaicode.register.model.Account;
import org.maimeemaineemaicode.register.model.Token;
import org.maimeemaineemaicode.register.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private AccountRepository accountRepository;

    public AuthController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginBody body) {
        // validate login
        try {
            if (body.username.equals("username") && body.password.equals("password")) {
                Account account = new Account("username", "password", "Customer");
                return new ResponseEntity(new Token(account), HttpStatus.OK);
            } else {
                return new ResponseEntity("ชื่อผู้ใช้งานหรือรหัสผ่านไม่ถูกต้อง", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

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
            return new ResponseEntity("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }
}
