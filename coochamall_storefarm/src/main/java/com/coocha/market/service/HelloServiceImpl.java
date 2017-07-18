package com.coocha.market.service;

import com.coocha.market.mapper.HelloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mbp123 on 2017. 7. 13..
 */
@Service
public class HelloServiceImpl/* implements HelloService*/ {

    private Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Autowired
    private HelloMapper helloMapper;

    public String helloString() throws Exception {
        String hello = "testhello!!!123123123";

        return hello;
    }

    public List<Map<String, String>> selectHello(String productKey) throws Exception {
        return helloMapper.selectHello(productKey);
    }

    public int insertTestForTransaction() throws Exception {
        return helloMapper.insertTestForTransaction();
    }

    public void transactionTest() throws Exception {
        for(int i=0; i<5; i++) {
            helloMapper.insertTestForTransaction();
        }
    }
}
