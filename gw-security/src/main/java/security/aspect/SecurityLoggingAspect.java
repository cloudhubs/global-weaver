package security.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import security.domain.LocalWeaverResult;
import security.service.SecurityService;

@Aspect
@Configuration
@Configurable
public class SecurityLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(SecurityLoggingAspect.class);

    @Around("execution(* security.service.SecurityServiceImpl.processLocalWeaverResult(..))")
    public String processLocalWeaverResultAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
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
            return (String) joinPoint.proceed();
        }
        logger.info("Now processing Module " + result.getModuleId() + " - " + result.getModuleName() + ":\n");
        String ret = (String) joinPoint.proceed();
        logger.info("Done processing Module " + result.getModuleId() + " - " + result.getModuleName() + "!\n\n");
        return ret;
    }

}
