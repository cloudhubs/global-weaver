package data.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Configurable
public class DataLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(DataLoggingAspect.class);

    @Around(value="data.aspect.CommonJoinPointConfig.dataService()")
    public Object processModelDataInconsistenciesAspectMethodBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

        String methodName = joinPoint.getSignature().getName();
        Class source = joinPoint.getSourceLocation().getWithinType();

        logger.info("[Entering " + methodName + " from " + source + "]");
        Object j = joinPoint.proceed();
        logger.info("[Leaving " + methodName + " from " + source + "]");

        return j;
    }
}