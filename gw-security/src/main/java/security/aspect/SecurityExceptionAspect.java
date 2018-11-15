package security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;

/**
 * This aspect is used to handle exceptions in this module. It has a general purpose handler for all exceptions.
 */
@Aspect
@Configuration
@Configurable
public class SecurityExceptionAspect {

    /**
     * This logger is used for all necessary logging.
     */
    private Logger logger = LoggerFactory.getLogger(SecurityExceptionAspect.class);

    /**
     * This is a general purpose handler that will catch any exception thrown by any method and log an error.
     *
     * @param joinPoint The joinPoint to the method from which the exception was thrown
     * @param ex The exception thrown
     */
    @AfterThrowing(value = "security.aspect.CommonJoinPointConfig.securityAllMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.error("Exception thrown from: " + joinPoint.getSignature().getName());
        logger.error(ex.toString());
    }

}
