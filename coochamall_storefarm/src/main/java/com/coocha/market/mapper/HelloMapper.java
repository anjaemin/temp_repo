package com.coocha.market.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HelloMapper {

    List<Map<String, String>> selectHello(String productKey) throws Exception;

    int insertTestForTransaction() throws Exception;
}
