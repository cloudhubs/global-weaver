package security.aspect;

import edu.baylor.ecs.seer.common.domain.LocalWeaverResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * This aspect will handle all info logging for this module.
 */
@Aspect
@Configuration
@Configurable
public class SecurityInfoLoggingAspect {

    /**
     * This logger will be used for all necessary logging.
     */
    private Logger logger = LoggerFactory.getLogger(SecurityInfoLoggingAspect.class);

    private ArrayList<String> getModuleInfoFromJoinPoint(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        LocalWeaverResult result = null;
        for (Object arg : args) {
            if (arg.getClass().equals(LocalWeaverResult.class)) {
                result = (LocalWeaverResult) arg;
                break;
            }
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(result.getModuleId()));
        list.add(result.getModuleName());
        return list;

    }

    /**
     * This will log whenever the security service begins to process module data from a local weaver.
     *
     * @param joinPoint The join point to the method being handled
     */
    @Before(value = "security.aspect.CommonJoinPointConfig.securityServiceImplProcessLocalWeaverResult()")
    public void logBeforeProcessLocalWeaverResult(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

        ArrayList<String> info = getModuleInfoFromJoinPoint(joinPoint);

        logger.info("Now processing Module " + info.get(0) + " - " + info.get(1) + ":\n");
    }

    /**
     * This will log whenever the security service finishes processing module data from the local weaver.
     *
     * @param joinPoint The join point to the method being handled
     */
    @After(value = "security.aspect.CommonJoinPointConfig.securityServiceImplProcessLocalWeaverResult()")
    public void logAfterProcessLocalWeaverResult(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

        ArrayList<String> info = getModuleInfoFromJoinPoint(joinPoint);

        logger.info("Done processing Module " + info.get(0) + " - " + info.get(1) + "!\n\n");
    }

    /**
     * This will log the result of processing local weaver data.
     *
     * @param joinPoint The join point to the method being handled
     */
    @AfterReturning(pointcut = "security.aspect.CommonJoinPointConfig.securityServiceImplProcessLocalWeaverResult()",
                    returning = "localWeaverInfo")
    public void logResultOfProcessLocalWeaverResult(JoinPoint joinPoint, String localWeaverInfo) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

        logger.info("The module security info was:\n" + localWeaverInfo);
    }

}
