package org.maimeemaineemaicode.register.controller;

import org.maimeemaineemaicode.register.bean.Account;
import org.maimeemaineemaicode.register.bean.DateOfBirth;
import org.maimeemaineemaicode.register.bean.SaleProfile;
import org.maimeemaineemaicode.register.bean.SaleRegisterBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/register")
public class SaleRegisterService {

    @RequestMapping(value="/sale", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethod(@RequestBody SaleRegisterBody body) {
        String isValid = validate(body);
        if (isValid == "Success") {
            String firstName = body.firstName;
            String lastName = body.lastName;
            String email = body.email;
            String address = body.address;
            String phoneNumber = body.phoneNumber;
            String shopName = body.shopName;
            String detail = body.detail;
            String shopType = body.shopType;
            // to-do: uid
            // auto increment and change datatype to integer
            String uid = "1";

            // CreateSaleProfile
            SaleProfile userProfile = new SaleProfile(uid, firstName, lastName, email, address, phoneNumber, shopName, detail, shopType);

            // CreateAccount
            String username = body.username;
            String password = body.password;
            String role = "Sale";

            Account account = new Account(uid, username, password, role);
            return "เสร็จสิ้น";
        } else {
            return "เกิดข้อผิดพลาด:  " + isValid;
        }
    }

    private String validate(SaleRegisterBody body) {
        String username = body.username;
        String password = body.password;
        String role = "Sale";
        String firstName = body.firstName;
        String lastName = body.lastName;
        String email = body.email;
        String address = body.address;
        String phoneNumber = body.phoneNumber;
        String shopName = body.shopName;
        String detail = body.detail;
        String shopType = body.shopType;

        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        if (username.length() <= 2) {
            return "ชื่อผู้ใช้ต้องมีอักขระไม่ต่ำกว่า 2 ตัวอักษร";
        }
        else if (username.length() >= 20){
            return "ชื่อผู้ใช้ต้องมีอักขระ 20  ตัวหรือน้อยกว่า";
        }
        else if (username.length() == 0){
            return "ชื่อผู้ใช้ไม่สามารถเว้นว่างได้";
        }

        if (password.length() < 8) {
            return "รหัสผ่านต้องมีความยาวไม่ต่ำกว่า 8 ตัวอักษร";
        }

        List<String> availableRole = new ArrayList<String>();
        availableRole.add("Sale");
        if (!availableRole.contains(role)) {
            return "Role not in available";
        }
        if (firstName.length() <= 2 && firstName instanceof String) {
            return "ชื่อจริงใช้ต้องมีอักขระไม่ต่ำกว่า 2 ตัวอักษร";
        }
        else if (firstName.length() >= 35 && firstName instanceof String) {
            return "ชื่อต้องมีอักขระ 35 ตัวหรือน้อยกว่า";
        }
        else if (firstName.length() == 0) {
            return "ชื่อจริงไม่สามารถเว้นว่างได้";
        }
        if (lastName.length() <= 2 && firstName instanceof String) {
            return "ชื่อจริงใช้ต้องมีอักขระไม่ต่ำกว่า 2 ตัวอักษร";
        }
        else if (lastName.length() >= 35 && firstName instanceof String) {
            return "ชื่อต้องมีอักขระ 35 ตัวหรือน้อยกว่า";
        }
        else if (lastName.length() == 0) {
            return "ชื่อจริงไม่สามารถเว้นว่างได้";
        }
        if (address.length() < 2 || address.length() > 200) 
            return "ที่อยู่ต้องมีอักขระ 35 ตัวหรือน้อยกว่า";
        else if (address == "")
             return "ที่อยู่ไม่สามารถเว้นว่างได้";
        if (phoneNumber.length() < 9 || phoneNumber.length() > 10) 
            return "เบอร์โทรศัพท์ต้องมีตัวเลข 9 หรือ 10 ตัว";
        else if (phoneNumber == "") 
            return "เบอร์โทรไม่สามารถเว้นว่างได้";
        if (shopName.length() <= 5) {
            return "ชื่อร้านค้าควรยาวกว่า 5 ตัวอักษร";
        }
        if (detail.length() <= 10) {
            return "ควรมีรายละเอียดร้านค้ามากกว่านี้";
        }
        if (shopType.length() <= 1) {
            return "ประเภทร้านค้าไม่ถูกต้อง";
        }
        return "Success";
    }
}