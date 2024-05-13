package com.uwu.study.ddd.mvcdemo.repository;

import com.uwu.study.ddd.mvcdemo.dto.User;

public interface UserRepository {
    User save(User user);
}
