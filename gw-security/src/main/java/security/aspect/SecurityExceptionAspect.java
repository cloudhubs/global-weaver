package security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;

@Aspect
@Configuration
@Configurable
public class SecurityExceptionAspect {

    private Logger logger = LoggerFactory.getLogger(SecurityExceptionAspect.class);

    @AfterThrowing(value = "execution(* security.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.error("Exception thrown from: " + joinPoint.getSignature().getName());
        logger.error(ex.toString());
    }

}
