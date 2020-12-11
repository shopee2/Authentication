package org.maimeemaineemaicode.register.controllers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.maimeemaineemaicode.register.bean.LoginBody;
import org.maimeemaineemaicode.register.bean.TokenBody;
import org.maimeemaineemaicode.register.model.Account;
import org.maimeemaineemaicode.register.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.*;

import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;
import static org.maimeemaineemaicode.register.model.Account.hashPassword;

@RestController
public class AuthController {

    private AccountRepository accountRepository;
    private SecretKey secretKey;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginBody body) {
        try {
            // Find account by username
            List<Account> accounts = accountRepository.findAccountByUsername(body.username);

            // If account can be find
            if (accounts.size() > 0) {
                // Get account
                Account account = accounts.get(0);
                if (account.getPassword().equals(hashPassword(body.password))) {
                    // Get current date
                    Calendar c = Calendar.getInstance();
                    // Set expire date
                    c.add(Calendar.SECOND, 30);
                    Date expireDate = c.getTime();

                    // Create JWS token
                    String jws = Jwts.builder()
                            .claim("uid", account.getUserID())
                            .claim("username", account.getUserName())
                            .claim("role", account.getRole())
                            .setExpiration(expireDate)
                            .signWith(this.secretKey)
                            .compact();

                    // Response JWS token with 200 (OK) status
                    return new ResponseEntity(new TokenBody("Bearer " + jws), HttpStatus.OK);
                }
            }
            // Response error with 400 (Bad Request) status
            return new ResponseEntity("ชื่อผู้ใช้งานหรือรหัสผ่านไม่ถูกต้อง", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.warn(e.toString());

            // Response error with 400 (Bad Request) status
            return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ResponseEntity validateToken(@RequestHeader(value = "authorization") String authToken) {
        try {
            // Check token is start with Bearer ?
            if (authToken.startsWith("Bearer ")) {
                // Remove Bearer from token
                authToken = authToken.replaceFirst("Bearer ", "");

                // Check signature of token
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(this.secretKey)
                        .build()
                        .parseClaimsJws(authToken).getBody();

                // Response claims with 200 (OK) status
                return new ResponseEntity(claims, HttpStatus.OK);
            } else {
                // Invalid token
                throw new SignatureException("Not a Bearer token");
            }
        } catch (ExpiredJwtException e) {
            // Response token expired error with 401 (Unauthorized) status
            return new ResponseEntity("Token expired", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            // Response token error with 401 (Unauthorized) status
            return new ResponseEntity("Invalid token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.warn(e.toString());
            return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
