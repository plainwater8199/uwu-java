package com.uwu.study.ddd.mvcdemo.repository;

import com.uwu.study.ddd.mvcdemo.dto.SalesRep;

public interface SalesRepRepository {
    SalesRep findRep(String areaCode);
}
