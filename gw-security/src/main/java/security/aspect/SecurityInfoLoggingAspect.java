package security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import security.domain.LocalWeaverResult;

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

    /**
     * This will log whenever the security service begins to process module data from a local weaver.
     *
     * @param joinPoint The join point to the method being handled
     */
    @Before(value = "security.aspect.CommonJoinPointConfig.securityServiceImplProcessLocalWeaverResult()")
    public void logBeforeProcessLocalWeaverResult(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        Object[] args = joinPoint.getArgs();
        LocalWeaverResult result = null;
        for (Object arg : args) {
            if (arg.getClass().equals(LocalWeaverResult.class)) {
                result = (LocalWeaverResult) arg;
                break;
            }
        }
        if (result == null) {
            return;
        }
        logger.info("Now processing Module " + result.getModuleId() + " - " + result.getModuleName() + ":\n");
    }

    /**
     * This will log whenever the security service finishes processing module data from the local weaver.
     *
     * @param joinPoint The join point to the method being handled
     */
    @After(value = "security.aspect.CommonJoinPointConfig.securityServiceImplProcessLocalWeaverResult()")
    public void logAfterProcessLocalWeaverResult(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        Object[] args = joinPoint.getArgs();
        LocalWeaverResult result = null;
        for (Object arg : args) {
            if (arg.getClass().equals(LocalWeaverResult.class)) {
                result = (LocalWeaverResult) arg;
                break;
            }
        }
        if (result == null) {
            return;
        }
        logger.info("Done processing Module " + result.getModuleId() + " - " + result.getModuleName() + "!\n\n");
    }

}
