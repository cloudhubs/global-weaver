package security.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * This class is the common join point config for the Spring AOP used in this module. It defines the point cuts to
 * be used later in the application.
 */
public class CommonJoinPointConfig {
    /**
     * This point cut is used to provide special logging around calls to processLocalWeaverResult(..).
     */
    @Pointcut("execution(* security.service.SecurityServiceImpl.processLocalWeaverResult(..))")
    public void securityServiceImplProcessLocalWeaverResult() {}

    /**
     * This point cut is a general purpose point cut for any aspect that needs to apply to all method calls.
     */
    @Pointcut("execution(* security.*.*.*(..))")
    public void securityAllMethods() {}

}
