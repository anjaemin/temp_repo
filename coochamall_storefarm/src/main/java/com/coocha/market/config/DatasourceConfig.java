package com.coocha.market.config;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Slf4j
@MapperScan(basePackages = "com.coocha.market.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class DatasourceConfig {

    /*@Value("#{environment.getActiveProfiles()[0]}")
    private String profile;

    @Bean
    public PropertyPlaceholderConfigurer placeholderConfigurer() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("/prop/" + profile + "/jdbc.properties"));
        return placeholderConfigurer;
    }*/

    @Value("${jdbc.coocha.market.driver}")
    private String driverName;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        log.debug("dbUrl = " + driverName);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl("jdbc:mysql://CCMALL-DEV01:3306/COOCHA_MARKET");
        dataSource.setUsername("woutlet_api");
        dataSource.setPassword("bwTNL3N4");
        return dataSource;
    }

   /* @Bean(destroyMethod = "close")
    public DataSource chartDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        return dataSource;
    }*/

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setConfigLocation();
        // spring.examples.model 패키지 이하의 model 클래스 이름을 짧은 별칭으로 등록
//        factoryBean.setTypeAliasesPackage("spring.examples.model");

        // META-INF/mybatis/mappers 패키지 이하의 모든 XML을 매퍼로 등록
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
