package com.coocha.market.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

//@Aspect
@Configuration
public class DatasourceTransactionConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatasourceTransactionConfig.class);

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName("*");
        transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        source.setTransactionAttribute(transactionAttribute);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.coocha.market..*ServiceImpl.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

    @Before("execution(* com.coocha.market..*ServiceImpl.*(..))")
    public void logBefore() {
        logger.debug("--------------------------------- logBefore!!!!!!!!");
    }

}
