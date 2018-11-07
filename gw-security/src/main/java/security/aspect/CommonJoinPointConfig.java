package security.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* security.service.SecurityService.getSecurityData(..))")
    public void securityServiceGetData() {}

    @Pointcut("execution(* security.controller.SecurityController.*(..))")
    public void securityControllerMethods() {}

}
