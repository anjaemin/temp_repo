package com.coocha.market.controller.hello;

import com.coocha.market.service.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@Slf4j
public class HelloController {

    @Autowired
    private HelloServiceImpl helloService;


    @Value("${jdbc.coocha.market.driver}")
    private String testurl;

    @Value("${con.static.publish}")
    private String globalStr;

    @Value("${image.server.domain}")
    private String configStr;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/hello")
    public String Hello(Model model) throws Exception {
        log.info("HelloController!!!");
        List<Map<String, String>> result = helloService.selectHello("11");
//        int cnt = helloService.insertTestForTransaction();
//        String testurl = messageSource.getMessage("jdbc.coocha.market.driver", null, Locale.KOREA);
        log.info("testUrl = " + testurl);
        log.info("globalStr = " + globalStr);
        log.info("configStr = " + configStr);
//        helloService.transactionTest();

        //redis test
        redisTemplate.opsForValue().set("1", "test");
        String value = redisTemplate.opsForValue().get("1").toString();
        log.info("value = " + value);
        model.addAttribute("name", helloService.helloString());
        return "hello";
    }
}
