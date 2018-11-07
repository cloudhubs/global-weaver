package data.aspect;

import data.domain.HarvesterData;
import data.domain.LocalWeaverResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Configurable
public class DataLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(DataLoggingAspect.class);

    @Around(value="data.aspect.CommonJoinPointConfig.dataServiceGetData()")
    public HarvesterData getDataAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        HarvesterData harvesterData = (HarvesterData) joinPoint.proceed();
        if(harvesterData.getData().size() > 0) {
            for (LocalWeaverResult lr : harvesterData.getData()) {
                logger.info("Getting data from local weaver: " + lr.getModuleName());
            }
        } else {
            logger.info("No modules detected");
        }
        return harvesterData;
    }
}