package com.uwu.study.ddd.mvcdemo.service.impl;

import com.uwu.study.ddd.mvcdemo.dto.SalesRep;
import com.uwu.study.ddd.mvcdemo.dto.User;
import com.uwu.study.ddd.mvcdemo.repository.SalesRepRepository;
import com.uwu.study.ddd.mvcdemo.repository.UserRepository;
import com.uwu.study.ddd.mvcdemo.service.RegistrationService;

import javax.validation.ValidationException;
import java.util.Arrays;

public class RegistrationServiceImpl implements RegistrationService {
    private SalesRepRepository salesRepRepo;
    private UserRepository userRepo;

    @Override
    public User register(String name, String phone, String address) {
        // 校验逻辑
        if (name == null || name.length() == 0) {
            throw new ValidationException("name");
        }
        if (phone == null || !isValidPhoneNumber(phone)) {
            throw new ValidationException("phone");
        }
        // 此处省略address的校验逻辑

        // 取电话号里的区号，然后通过区号找到区域内的SalesRep
        String areaCode = null;
        String[] areas = new String[]{"0571", "021", "010"};
        for (int i = 0; i < phone.length(); i++) {
            String prefix = phone.substring(0, i);
            if (Arrays.asList(areas).contains(prefix)) {
                areaCode = prefix;
                break;
            }
        }
        SalesRep rep = salesRepRepo.findRep(areaCode);

        // 最后创建用户，落盘，然后返回
        User user = new User();
        user.setName(name);
        user.setPhone(phone); ;
        user.setAddress(address);
        if (rep != null) {
            user.setRepId(rep.getRepId());
        }

        return userRepo.save(user);
    }

    private boolean isValidPhoneNumber(String phone) {
        String pattern = "^0[1-9]{2,3}-?\\d{8}$";
        return phone.matches(pattern);
    }
}
