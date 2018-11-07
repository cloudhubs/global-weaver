package data.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* data.service.DataService.processModelDataInconsistencies(..))")
    public void dataServiceGetData(){}

    //@Pointcut("execution(* data.service.DataService.processModelDataInconsistencies(..))")
    //public void dataServiceInconsistencies(){}

    //@Pointcut("execution(* data.service.DataService.processModelDataValidation(..))")
    //public void dataServiceValidation(){}

    //@Pointcut("execution(* data.controller.DataController.*(..))")
    //public void dataControllerMethods(){}

}
