package data.aspect;

import data.domain.HarvesterData;
import data.domain.LocalWeaverResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Configurable
public class DataLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(DataLoggingAspect.class);

    @Before(value="data.aspect.CommonJoinPointConfig.dataServiceGetData()")
    public void getDataAspectMethod(JoinPoint joinPoint) throws Throwable {

        System.out.printf("ahoj");
    }
}