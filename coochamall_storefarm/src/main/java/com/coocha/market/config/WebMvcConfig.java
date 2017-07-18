package com.coocha.market.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@Import({DatasourceConfig.class, DatasourceTransactionConfig.class, PropertyConfig.class})
@ComponentScan(basePackages = "com.coocha.market", includeFilters = @Filter({Service.class, Repository.class}))
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("#{environment.getActiveProfiles()[0]}")
    private String profile;

    @Value("${jdbc.coocha.market.driver}")
    private String dbUrl;

    // 정적리소스를 처리해주는 핸들러를 등록
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").resourceChain(true);
    }

    // interceptor 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new Interceptor)
        super.addInterceptors(registry);
    }

    // viewResolver 등록
    @Bean
    public ViewResolver viewResolver() {
        log.debug("WebMvcConfig dbUrl = " + dbUrl);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }



    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxInMemorySize();
        return resolver;
    }
}
