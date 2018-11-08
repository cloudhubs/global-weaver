package security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

/**
 * This aspect will handle all debug logging for this module.
 */
@Aspect
@Configuration
@Configurable
public class SecurityDebugLoggingAspect {

    /**
     * This logger will be used for all necessary logging.
     */
    private Logger logger = LoggerFactory.getLogger(SecurityInfoLoggingAspect.class);

    /**
     * This will log each method call, along with its parameters.
     *
     * @param joinPoint The join point to the method being handled
     */
    @Before(value = "security.aspect.CommonJoinPointConfig.securityAllMethods()")
    public void logDebugBeforeEachMethod(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.debug("Method call to " + joinPoint.getSignature().getName());
        StringBuilder parameters = new StringBuilder();
        parameters.append("Parameters are ");
        for (Object arg : joinPoint.getArgs()) {
            parameters.append(arg.toString() + ' ');
        }
        logger.debug(parameters.toString());
    }

    /**
     * This will log the completion of each method call, as well as its target.
     *
     * @param joinPoint The join point to the method being handled
     */
    @After(value = "security.aspect.CommonJoinPointConfig.securityAllMethods()")
    public void logDebugAfterEachMethod(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.debug("Targeted " + joinPoint.getTarget().toString());
        logger.debug("Finished call to " + joinPoint.getSignature().getName());
    }

}
