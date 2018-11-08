package data.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* data.service.DataService.*(..))")
    public void dataService(){}

}
