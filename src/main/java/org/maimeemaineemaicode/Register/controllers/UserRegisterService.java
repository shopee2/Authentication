package org.maimeemaineemaicode.register.controllers;

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
            String role = "Customer";

            Account account = new Account(uid, username, password, role);

            return "เสร็จสิ้น";
        } else {
            return "เกิดข้อผิดพลาด: " + isValid;
        }
    }

    private String validate(UserRegisterBody body) {
        String username = body.username;
        String password = body.password;
        String firstName = body.firstName;
        String lastName = body.lastName;
        String address = body.address;
        String phoneNumber = body.phoneNumber;
        String gender = body.gender;
        String[] DoB = body.dateOfBirth.split("-");

        List<String> availableGender = new ArrayList<String>();
        availableGender.add("male");
        availableGender.add("female");
        availableGender.add("other");

//        username
        if (username.length() < 2 || username.length() > 20) return "ชื่อผู้ใช้ต้องมีอักขระ 20 ตัวหรือน้อยกว่า";
        else if (username == "") return "ชื่อผู้ใช้ไม่สามารถเว้นว่างได้";

//        password
        if (password.length() < 8) return "รหัสผ่านต้องมี 8 ตัวอักษรขึ้นไป";

//        firstName
        if (firstName.length() < 2 || firstName.length() > 35) return "ชื่อจริงต้องมีอักขระ 35 ตัวหรือน้อยกว่า";
        else if (firstName == "") return  "ชื่อไม่สามารถเว้นว่างได้";

//        lastName
        if (lastName.length() < 2 || lastName.length() > 35) return "นามสกุลต้องมีอักขระ 35 ตัวหรือน้อยกว่า";
        else if (lastName == "") return  "นามสกุลไม่สามารถเว้นว่างได้";

//        address
        if (address.length() < 2 || address.length() > 200) return "ที่อยู่ต้องมีอักขระ 200 ตัวหรือน้อยกว่า";
        else if (address == "") return "ี่อยู่ไม่สามารถเว้นว่างได้";

//        phoneNumber
        if (phoneNumber.length() < 9 || phoneNumber.length() > 10) return "เบอร์โทรศัพท์ต้องมีตัวเลข 9 หรือ 10 ตัว";
        else if (phoneNumber == "") return "เบอร์โทรไม่สามารถเว้นว่างได้";

//        gender
        if (!availableGender.contains(gender)) return "ระบุเพศตามที่กำหนดให้เท่านั้น";

//        DoB
        if (DoB[0].length() != 4 && (Integer.parseInt(DoB[1]) < 1 || Integer.parseInt(DoB[1]) > 12) &&
                (Integer.parseInt(DoB[2]) < 1 || Integer.parseInt(DoB[2]) > 31)) {
            return "วันเกิดไม่ถูกต้อง";
        }

        return "Success";
    }
}