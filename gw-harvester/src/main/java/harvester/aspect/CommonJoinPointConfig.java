package harvester.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* harvester.service.HarvesterService.getData(..))")
    public void harvesterServiceGetData(){}

}
