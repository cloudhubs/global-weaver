package harvester.aspect;

import harvester.controller.HarvesterController;
import harvester.domain.HarvesterData;
import harvester.domain.LocalWeaverResult;
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
public class HarvesterLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(HarvesterLoggingAspect.class);

    @Around(value="harvester.aspect.CommonJoinPointConfig.harvesterServiceGetData()")
    public HarvesterData getDataAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        HarvesterData harvesterData = (HarvesterData) joinPoint.proceed();
        for (LocalWeaverResult lr: harvesterData.getData()
             ) {
            //ToDo name is null!
            logger.info("Getting data from local weaver: " + lr.getModuleName());
        }
        return harvesterData;
    }

    @Before(value="harvester.aspect.CommonJoinPointConfig.harvesterControllerMethods()")
    public void logHarvesterController(JoinPoint joinPoint){
        logger = LoggerFactory.getLogger(HarvesterController.class);
        logger.info(joinPoint.getSignature().getName() + "method call");
    }

}