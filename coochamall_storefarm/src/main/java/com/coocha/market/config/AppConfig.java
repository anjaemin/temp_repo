package com.coocha.market.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "com.coocha.market", includeFilters = @Filter({Controller.class}))
@EnableAspectJAutoProxy
@Import(PropertyConfig.class)
public class AppConfig {


}
