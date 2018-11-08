package data.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* data.service.DataService.*(..))")
    public void dataService(){}

    @Pointcut("execution(* com.fasterxml.jackson.databind.ObjectMapper.readValue(..))")
    public void parse(){}
}
