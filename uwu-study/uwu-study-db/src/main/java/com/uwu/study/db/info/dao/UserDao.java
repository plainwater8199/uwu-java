package com.uwu.study.db.info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.db.info.entity.UserDo;

public interface UserDao extends BaseMapper<UserDo> {

    int count();
}
