package harvester.aspect;

import harvester.domain.HarvesterData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import java.security.Identity;
import java.util.ArrayList;

@Aspect
@Configuration
@Configurable
public class HarvesterServiceAspect {

    private Logger logger = LoggerFactory.getLogger(HarvesterServiceAspect.class);

    @Around(value="harvester.aspect.CommonJoinPointConfig.harvesterServiceGetData()")
    public void getDataAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around");
        Object object = joinPoint.proceed();
        //logger.info(object.toString());
        System.out.println("around");
    }
}