package com.uwu.study.ddd.mvcdemo.service;

import com.uwu.study.ddd.mvcdemo.dto.User;

public interface RegistrationService {
    User register(String name, String phone, String address);
}
