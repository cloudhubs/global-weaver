package harvester.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
//    @Pointcut("@annotation(cz.cvut.fel.iotframework.annotation.Manage)")
//    public void manageAnnotation(){}

    @Pointcut("execution(* harvester.service.HarvesterService.getSecurityData(..))")
    public void iotFrameworkAnnotation(){}

}
