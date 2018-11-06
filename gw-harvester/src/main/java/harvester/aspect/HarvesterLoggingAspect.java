package harvester.aspect;

import harvester.domain.HarvesterData;
import harvester.domain.LocalWeaverResult;
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
public class HarvesterServiceAspect {

    private Logger logger = LoggerFactory.getLogger(HarvesterServiceAspect.class);

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
}