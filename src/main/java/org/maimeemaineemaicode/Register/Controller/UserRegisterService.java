package org.maimeemaineemaicode.register.Controller;

import org.maimeemaineemaicode.register.bean.Account;
import org.maimeemaineemaicode.register.bean.DateOfBirth;
import org.maimeemaineemaicode.register.bean.UserProfile;
import org.maimeemaineemaicode.register.bean.UserRegisterBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/register")
public class UserRegisterService {
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @RequestMapping(value="/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethod(@RequestBody UserRegisterBody body) {
        String isValid = validate(body);
        if (isValid == "Success") {
            String firstName = body.firstName;
            String lastName = body.lastName;
            String address = body.address;
            String phoneNumber = body.phoneNumber;
            String gender = body.gender;
            String[] DoB = body.dateOfBirth.split("-");

            // to-do: uid
            // auto increment and change datatype to integer
            String uid = "1";

            // CreateUserProfile
            DateOfBirth dateOfBirth = new DateOfBirth(DoB[0], DoB[1], DoB[2]);
            UserProfile userProfile = new UserProfile(uid, firstName, lastName, address, phoneNumber, gender, dateOfBirth);

            // CreateAccount
            String username = body.username;
            String password = body.password;
            String role = body.role;

            Account account = new Account(uid, username, password, role);
            return "Success";
        } else {
            return "Fail" + isValid;
        }
    }

    private String validate(UserRegisterBody body) {
        String username = body.username;
        String password = body.password;
        String role = body.role;
        String firstName = body.firstName;
        String lastName = body.lastName;
        String address = body.address;
        String phoneNumber = body.phoneNumber;
        String gender = body.gender;
        String[] DoB = body.dateOfBirth.split("-");

        if (username.length() <= 5) {
            return "Username length > 5";
        }
        if (password.length() <= 5) {
            return "Password length > 5";
        }

        List<String> availableRole = new ArrayList<String>();
        availableRole.add("Customer");
        availableRole.add("Sale");
        if (!availableRole.contains(role)) {
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

        List<String> availableGender = new ArrayList<String>();
        availableGender.add("male");
        availableGender.add("female");
        availableGender.add("other");

        if (!availableGender.contains(gender)) {
            return "Gender not in available";
        }
        if (DoB[0].length() == 4 && (Integer.parseInt(DoB[1]) >= 1 && Integer.parseInt(DoB[1]) <= 12) &&
                Integer.parseInt(DoB[2]) <= 31) {
            return "Datetime format error";
        }
        return "Success";
    }
}