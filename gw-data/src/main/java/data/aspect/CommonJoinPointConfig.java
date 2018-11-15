package data.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* data.controller.DataController.findInconsistencies(..))")
    public void controllerInconsistencies(){}

    @Pointcut("execution(* data.controller.DataController.findValidationPoints(..))")
    public void controllerValidation(){}

    @Pointcut("execution(* data.service.DataService.getModelData(..))")
    public void serviceGetModelData(){}

    @Pointcut("execution(* data.service.DataService.getBytecodeData(..))")
    public void serviceGetBytecodeData(){}

    @Pointcut("execution(* data.service.DataService.aggregateModelData(..))")
    public void serviceAggregateModelData(){}
}
