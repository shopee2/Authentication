package org.maimeemaineemaicode.Register.controller;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.simple.JSONObject;

@SpringBootApplication
@RestController
public class UserRegisterService extends UserProfile {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootService.class, args);
    }

    @RequestMapping(value="/register/user/", method = RequestMethod.POST)
    public String requestMethod(@RequestBody body) {
        String isValid = validate(body);
        if (isValid == "Success") {
            String firstName = body.firstName;
            String lastName = body.lastName;
            String address = body.address;
            String phoneNumber = body.phoneNumber;
            String gender = body.gender;
            String DoB = body.dateOfBirth.split("-");

//            CreateUserProfile
            DateOfBirth DoB = new DateOfBirth(DoB[0], DoB[1], DoB[2]);
            UserProfile = new UserProfile(uid, firstName, lastName, address, phoneNumber, gender, DoB);

            String username = body.username;
            String password = body.password;
            String role = body.role;

//            CreateAccount
            Account = new Account(uid, username, password, role);

            return "Success";
        } else {
            return "Fail" + isValid;
        }
    }

    private String validate(RequestBody body) {
        String username = body.username;
        String password = body.password;
        String role = body.role;
        String firstName = body.firstName;
        String lastName = body.lastName;
        String address = body.address;
        String phoneNumber = body.phoneNumber;
        String gender = body.gender;
        String DoB = body.dateOfBirth.split("-");

        if (username.length() <= 5) {
            return "Username length > 5";
        }
        if (password.length() <= 5) {
            return "Password length > 5";
        }
        if (!["Customer", "Sale"].contains(role)) {
            return "Role not in available";
        }
        if (firstName.length() <= 5 && firstName instanceof String) {
            return "Please input your firstName";
        }
        if (lastName.length() <= 5 && lastName instanceof String) {
            return "Please input your lastName";
        }
        if (address.length() <= 20) {
            return "Address length > 20";
        }
        if (phoneNumber.length() <= 10) {
            return "Address length > 10";
        }
        if (!["male", "female", "other"].contains(gender) {
            return "Gender not in available";
        }
        if (DoB[0].length() == 4 && (Integer.parseInt(DoB[1]) >= 1 && Integer.parseInt(DoB[1]) <= 12) &&
                Integer.parseInt(DoB[2]) <= 31) {
            return "Datetime format error";
        }
        return "Success";
    }
}