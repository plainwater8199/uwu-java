package com.uwu.study.db.shardingspheredemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.db.shardingspheredemo.entity.User;

public interface UserMapper extends BaseMapper<User> {

    int count();
}
