package com.mindata;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Order(0)
public class LogInfo {

    static Logger logger = LoggerFactory.getLogger(LogInfo.class);

    @Pointcut("target(com.mindata.controllers.SpaceshipController) && execution(String getSpaceshipById(*))")
    public void methodPointcut() {}

    @Before("methodPointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        int spaceshipId = Integer.parseInt(joinPoint.getArgs()[0].toString());
        if(spaceshipId<0) {
            logger.info(
                    "Method {} executed with invalid negative SpaceshipID: {}",
                    joinPoint.getStaticPart().getSignature(),
                    spaceshipId
            );
        }
    }

   @AfterThrowing(
      pointcut = "methodPointcut()",
      throwing = "e")
    public void logExceptions(JoinPoint jp, Exception e) {
        logger.error(e.getMessage(), e);
    }
}