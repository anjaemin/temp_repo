package com.coocha.market.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class PropertyConfig {
    final static String ACTIVE_PROFILE_PROPERTY_NAME = "spring.profiles.active";

    @Value("#{environment.getActiveProfiles()[0]}")
    private String profile;

    /*@Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("file:/prop/" + profile + "/jdbc", "classpath:/prop/" + profile + "/jdbc.properties");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }*/

    @Bean
    public static PropertyPlaceholderConfigurer properties() throws Exception {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreUnresolvablePlaceholders(true);
        String deploy = System.getProperty(ACTIVE_PROFILE_PROPERTY_NAME);
        if(deploy == null || "".equals(deploy)) {
            deploy = "dev";
        }

        // default????
        /*Resource[] prodResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/prop/dev/jdbc.properties");*/

        Resource[] jdbcResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/prop/"+ deploy +"/jdbc.properties");

        // global properties
        Resource[] globalResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/prop/"+ deploy +"/global.properties");

        // config properties
        Resource[] configResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/prop/"+ deploy +"/config.properties");

        Resource[] allResources = ArrayUtils.addAll(jdbcResources);
        allResources = ArrayUtils.addAll(allResources);
        allResources = ArrayUtils.addAll(allResources, globalResources);
        allResources = ArrayUtils.addAll(allResources, configResources);

        ppc.setLocations(allResources);

        return ppc;
    }

    /*@Bean
    public static PropertyPlaceholderConfigurer globalProperties() throws Exception {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreUnresolvablePlaceholders(true);
        String deploy = System.getProperty(ACTIVE_PROFILE_PROPERTY_NAME);

        // default????
        Resource[] prodResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/prop/dev/global.properties");

        Resource[] overrideResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/prop/"+ deploy +"/global.properties");

        Resource[] allResources = ArrayUtils.addAll(prodResources);
        allResources = ArrayUtils.addAll(allResources, overrideResources);

        ppc.setLocations(allResources);

        return ppc;
    }*/
}
